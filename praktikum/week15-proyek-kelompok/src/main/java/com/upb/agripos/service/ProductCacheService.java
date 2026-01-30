package com.upb.agripos.service;

import com.upb.agripos.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.application.Platform;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * ProductCacheService - Singleton untuk menyimpan dan sinkronisasi data produk
 * Digunakan oleh AdminDashboard dan PosView untuk memastikan data selalu sinkron
 * 
 * Features:
 * - Singleton pattern (single instance di seluruh aplikasi)
 * - ObservableList untuk auto-refresh UI
 * - Observer pattern untuk notifikasi perubahan
 * - Thread-safe dengan CopyOnWriteArrayList
 * - Refresh dari database kapan saja
 */
public class ProductCacheService {
    private static ProductCacheService instance;
    private ObservableList<Product> cachedProducts;
    private ProductService productService;
    private final List<ProductChangeListener> listeners = new CopyOnWriteArrayList<>();
    
    /**
     * Interface untuk listener yang ingin diberitahu tentang perubahan produk
     */
    public interface ProductChangeListener {
        void onProductAdded(Product product);
        void onProductUpdated(Product product);
        void onProductDeleted(String productCode);
        void onProductsRefreshed(List<Product> products);
    }
    
    /**
     * Private constructor untuk Singleton pattern
     */
    private ProductCacheService() {
        this.cachedProducts = FXCollections.observableArrayList();
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized ProductCacheService getInstance() {
        if (instance == null) {
            instance = new ProductCacheService();
        }
        return instance;
    }
    
    /**
     * Initialize dengan ProductService
     */
    public void setProductService(ProductService productService) {
        this.productService = productService;
        if (productService != null) {
            refreshProductsFromDatabase();
        }
    }
    
    /**
     * Refresh semua produk dari database
     * Dipanggil saat login, logout, atau when needed
     */
    public void refreshProductsFromDatabase() {
        if (productService == null) {
            System.err.println("[ProductCache] ProductService belum diinisialisasi!");
            return;
        }
        
        try {
            final List<Product> products = productService.getAllProducts();
            final List<Product> finalProducts = (products == null) ? new ArrayList<>() : products;
            
            // Update cache di UI thread
            Platform.runLater(() -> {
                cachedProducts.clear();
                cachedProducts.addAll(finalProducts);
                System.out.println("[ProductCache] ✓ Refresh dari database: " + finalProducts.size() + " produk");
                notifyProductsRefreshed(finalProducts);
            });
        } catch (Exception e) {
            System.err.println("[ProductCache] ✗ Error refresh produk: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Add produk baru dan notify semua listeners
     */
    public void addProduct(Product product) {
        if (productService == null) return;
        
        try {
            boolean success = productService.addProduct(product);
            if (success) {
                Platform.runLater(() -> {
                    cachedProducts.add(product);
                    System.out.println("[ProductCache] ✓ Produk ditambahkan: " + product.getName());
                    notifyProductAdded(product);
                });
            }
        } catch (Exception e) {
            System.err.println("[ProductCache] ✗ Error add produk: " + e.getMessage());
        }
    }
    
    /**
     * Update produk dan notify semua listeners
     */
    public void updateProduct(Product product) {
        if (productService == null) return;
        
        try {
            boolean success = productService.updateProduct(product);
            if (success) {
                Platform.runLater(() -> {
                    // Find dan replace produk di cache
                    int index = -1;
                    for (int i = 0; i < cachedProducts.size(); i++) {
                        if (cachedProducts.get(i).getCode().equals(product.getCode())) {
                            index = i;
                            break;
                        }
                    }
                    
                    if (index >= 0) {
                        cachedProducts.set(index, product);
                    } else {
                        cachedProducts.add(product);
                    }
                    
                    System.out.println("[ProductCache] ✓ Produk diupdate: " + product.getName());
                    notifyProductUpdated(product);
                });
            }
        } catch (Exception e) {
            System.err.println("[ProductCache] ✗ Error update produk: " + e.getMessage());
        }
    }
    
    /**
     * Delete produk dan notify semua listeners
     */
    public void deleteProduct(String productCode) {
        if (productService == null) return;
        
        try {
            boolean success = productService.deleteProduct(productCode);
            if (success) {
                Platform.runLater(() -> {
                    cachedProducts.removeIf(p -> p.getCode().equals(productCode));
                    System.out.println("[ProductCache] ✓ Produk dihapus: " + productCode);
                    notifyProductDeleted(productCode);
                });
            }
        } catch (Exception e) {
            System.err.println("[ProductCache] ✗ Error delete produk: " + e.getMessage());
        }
    }
    
    /**
     * Get all cached products
     */
    public ObservableList<Product> getProducts() {
        return cachedProducts;
    }
    
    /**
     * Get product by code
     */
    public Product getProductByCode(String code) {
        for (Product p : cachedProducts) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Register listener untuk notifikasi perubahan
     */
    public void addChangeListener(ProductChangeListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
            System.out.println("[ProductCache] Listener ditambahkan: " + listener.getClass().getSimpleName());
        }
    }
    
    /**
     * Unregister listener
     */
    public void removeChangeListener(ProductChangeListener listener) {
        listeners.remove(listener);
        System.out.println("[ProductCache] Listener dihapus: " + listener.getClass().getSimpleName());
    }
    
    /**
     * Notify semua listeners bahwa produk ditambahkan
     */
    private void notifyProductAdded(Product product) {
        for (ProductChangeListener listener : listeners) {
            try {
                listener.onProductAdded(product);
            } catch (Exception e) {
                System.err.println("[ProductCache] Error notifying listener: " + e.getMessage());
            }
        }
    }
    
    /**
     * Notify semua listeners bahwa produk diupdate
     */
    private void notifyProductUpdated(Product product) {
        for (ProductChangeListener listener : listeners) {
            try {
                listener.onProductUpdated(product);
            } catch (Exception e) {
                System.err.println("[ProductCache] Error notifying listener: " + e.getMessage());
            }
        }
    }
    
    /**
     * Notify semua listeners bahwa produk dihapus
     */
    private void notifyProductDeleted(String productCode) {
        for (ProductChangeListener listener : listeners) {
            try {
                listener.onProductDeleted(productCode);
            } catch (Exception e) {
                System.err.println("[ProductCache] Error notifying listener: " + e.getMessage());
            }
        }
    }
    
    /**
     * Notify semua listeners bahwa produk di-refresh dari database
     */
    private void notifyProductsRefreshed(List<Product> products) {
        for (ProductChangeListener listener : listeners) {
            try {
                listener.onProductsRefreshed(products);
            } catch (Exception e) {
                System.err.println("[ProductCache] Error notifying listener: " + e.getMessage());
            }
        }
    }
    
    /**
     * Clear cache (untuk logout)
     */
    public void clearCache() {
        cachedProducts.clear();
        System.out.println("[ProductCache] Cache cleared");
    }
}
