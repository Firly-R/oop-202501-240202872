-- =====================================================
-- SQL Schema untuk Sinkronisasi Produk Kasir & Admin
-- AgriPOS Week 15 - Kolaboratif
-- Date: 2026-01-17
-- =====================================================

-- =====================================================
-- Hapus tabel lama dan buat yang baru dengan kolom sync
-- =====================================================

-- Drop existing table if exists
DROP TABLE IF EXISTS products CASCADE;

-- =====================================================
-- Table: products (dengan dukungan sync real-time)
-- Menyimpan data produk yang dijual dengan status sinkronisasi
-- =====================================================
CREATE TABLE IF NOT EXISTS products (
    product_id SERIAL PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DECIMAL(12, 2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    reorder_level INT DEFAULT 10,
    status VARCHAR(20) DEFAULT 'NORMAL', -- NORMAL, LOW_STOCK, DISCONTINUED
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_modified_by VARCHAR(50),
    is_synced BOOLEAN DEFAULT TRUE
);

-- Create indexes untuk performa query
CREATE INDEX IF NOT EXISTS idx_product_code ON products(code);
CREATE INDEX IF NOT EXISTS idx_product_name ON products(name);
CREATE INDEX IF NOT EXISTS idx_product_category ON products(category);
CREATE INDEX IF NOT EXISTS idx_product_status ON products(status);
CREATE INDEX IF NOT EXISTS idx_product_stock ON products(stock);
CREATE INDEX IF NOT EXISTS idx_product_updated_at ON products(updated_at);

-- =====================================================
-- Table: product_sync_log (untuk tracking sinkronisasi)
-- Mencatat setiap perubahan produk untuk audit dan debug
-- =====================================================
CREATE TABLE IF NOT EXISTS product_sync_log (
    log_id SERIAL PRIMARY KEY,
    product_id INT NOT NULL,
    product_code VARCHAR(20) NOT NULL,
    operation VARCHAR(20) NOT NULL, -- INSERT, UPDATE, DELETE
    changed_by VARCHAR(50) NOT NULL, -- Username user yang melakukan perubahan
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    old_values JSONB,  -- Field yang berubah (old value)
    new_values JSONB,  -- Field yang berubah (new value)
    details TEXT,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE SET NULL
);

CREATE INDEX IF NOT EXISTS idx_sync_log_product_id ON product_sync_log(product_id);
CREATE INDEX IF NOT EXISTS idx_sync_log_changed_at ON product_sync_log(changed_at);
CREATE INDEX IF NOT EXISTS idx_sync_log_operation ON product_sync_log(operation);

-- =====================================================
-- Sample Data untuk Testing Sinkronisasi
-- =====================================================

INSERT INTO products (code, name, category, price, stock, reorder_level, status) VALUES
('P001', 'Benih Padi', 'Biji-bijian', 15000.00, 50, 10, 'NORMAL'),
('P002', 'Pupuk Organik', 'Pupuk', 25000.00, 100, 15, 'NORMAL'),
('P003', 'Pupuk Urea', 'Pupuk', 35000.00, 75, 20, 'NORMAL'),
('P004', 'Pestisida Alami', 'Kimia', 55000.00, 30, 10, 'NORMAL'),
('P005', 'Benih Jagung', 'Biji-bijian', 18000.00, 60, 15, 'NORMAL'),
('P006', 'Bibit Tomat', 'Sayuran', 5000.00, 200, 50, 'NORMAL'),
('P007', 'Kompos Premium', 'Pupuk', 45000.00, 8, 10, 'LOW_STOCK');

-- =====================================================
-- Trigger untuk auto-update updated_at field
-- =====================================================

CREATE OR REPLACE FUNCTION update_product_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_update_product_timestamp ON products;
CREATE TRIGGER trigger_update_product_timestamp
BEFORE UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION update_product_timestamp();

-- =====================================================
-- Trigger untuk auto-update status berdasarkan stock
-- =====================================================

CREATE OR REPLACE FUNCTION update_product_status()
RETURNS TRIGGER AS $$
BEGIN
    -- Update status berdasarkan stock level
    IF NEW.stock <= 0 THEN
        NEW.status := 'DISCONTINUED';
    ELSIF NEW.stock <= NEW.reorder_level THEN
        NEW.status := 'LOW_STOCK';
    ELSE
        NEW.status := 'NORMAL';
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_update_product_status ON products;
CREATE TRIGGER trigger_update_product_status
BEFORE INSERT OR UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION update_product_status();

-- =====================================================
-- Trigger untuk logging perubahan produk (audit trail)
-- =====================================================

CREATE OR REPLACE FUNCTION log_product_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO product_sync_log (product_id, product_code, operation, changed_by, new_values, details)
        VALUES (NEW.product_id, NEW.code, 'INSERT', COALESCE(NEW.last_modified_by, 'system'), 
                row_to_json(NEW), 'Produk baru ditambahkan');
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO product_sync_log (product_id, product_code, operation, changed_by, old_values, new_values, details)
        VALUES (NEW.product_id, NEW.code, 'UPDATE', COALESCE(NEW.last_modified_by, 'system'),
                row_to_json(OLD), row_to_json(NEW), 'Produk diupdate');
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO product_sync_log (product_id, product_code, operation, changed_by, old_values, details)
        VALUES (OLD.product_id, OLD.code, 'DELETE', COALESCE(OLD.last_modified_by, 'system'),
                row_to_json(OLD), 'Produk dihapus');
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_log_product_changes ON products;
CREATE TRIGGER trigger_log_product_changes
AFTER INSERT OR UPDATE OR DELETE ON products
FOR EACH ROW
EXECUTE FUNCTION log_product_changes();

-- =====================================================
-- Views untuk monitoring sinkronisasi
-- =====================================================

-- View: Produk dengan stok rendah
CREATE OR REPLACE VIEW v_low_stock_products AS
SELECT code, name, category, stock, reorder_level, status, updated_at
FROM products
WHERE status IN ('LOW_STOCK', 'DISCONTINUED')
ORDER BY stock ASC;

-- View: Recent product changes (last 24 hours)
CREATE OR REPLACE VIEW v_recent_product_changes AS
SELECT l.log_id, l.product_code, l.operation, l.changed_by, l.changed_at, l.details
FROM product_sync_log l
WHERE l.changed_at >= CURRENT_TIMESTAMP - INTERVAL '24 hours'
ORDER BY l.changed_at DESC;

-- View: Product sync statistics
CREATE OR REPLACE VIEW v_product_sync_stats AS
SELECT 
    COUNT(*) as total_products,
    SUM(stock) as total_stock,
    COUNT(CASE WHEN status = 'NORMAL' THEN 1 END) as normal_count,
    COUNT(CASE WHEN status = 'LOW_STOCK' THEN 1 END) as low_stock_count,
    COUNT(CASE WHEN status = 'DISCONTINUED' THEN 1 END) as discontinued_count,
    MAX(updated_at) as last_update
FROM products;

-- =====================================================
-- Verification Queries
-- =====================================================

-- Lihat semua produk
SELECT * FROM products ORDER BY code;

-- Lihat produk dengan stok rendah
SELECT * FROM v_low_stock_products;

-- Lihat statistik sinkronisasi
SELECT * FROM v_product_sync_stats;

-- Lihat log perubahan produk
SELECT * FROM v_recent_product_changes;

COMMIT;
