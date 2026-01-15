-- Schema for Agri-POS Database
-- Database: agripos

CREATE TABLE IF NOT EXISTS products (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL CHECK (price >= 0),
    stock INTEGER NOT NULL CHECK (stock >= 0)
);

-- Sample data
INSERT INTO products (code, name, price, stock) VALUES
('P001', 'Beras Premium 5kg', 50000, 100),
('P002', 'Gula Putih 1kg', 12000, 50),
('P003', 'Minyak Goreng 2L', 25000, 30),
('P004', 'Telur Ayam 1kg', 28000, 40),
('P005', 'Bawang Merah 500g', 15000, 60)
ON CONFLICT (code) DO NOTHING;
