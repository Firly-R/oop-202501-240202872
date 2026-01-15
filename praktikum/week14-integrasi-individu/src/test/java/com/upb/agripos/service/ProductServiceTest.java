package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductException;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.model.Product;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.sql.*;

/**
 * ProductServiceTest - Unit Test untuk ProductService
 * Menguji logika bisnis produk termasuk stock reduction
 */
public class ProductServiceTest {
    private ProductService productService;
    private Product testProduct;

    @Before
    public void setUp() {
        // Create a mock DAO for testing (simplified without real DB)
        MockProductDAO mockDAO = new MockProductDAO();
        productService = new ProductService(mockDAO);
        testProduct = new Product("TEST001", "Test Product", 10000, 100);
    }

    @Test
    public void testReduceStock() throws ProductException {
        // Setup: Insert test product
        productService.addProduct(testProduct);
        
        // Reduce stock dari 100 menjadi 90
        productService.reduceStock("TEST001", 10);
        
        // Verify
        Product product = productService.getProductByCode("TEST001");
        assertEquals(90, product.getStock());
    }

    @Test
    public void testReduceStockMultipleTimes() throws ProductException {
        // Setup
        productService.addProduct(testProduct);
        
        // Multiple transactions
        productService.reduceStock("TEST001", 10); // 100 - 10 = 90
        productService.reduceStock("TEST001", 20); // 90 - 20 = 70
        productService.reduceStock("TEST001", 15); // 70 - 15 = 55
        
        Product product = productService.getProductByCode("TEST001");
        assertEquals(55, product.getStock());
    }

    @Test(expected = ProductException.class)
    public void testReduceStockExceedsAvailable() throws ProductException {
        // Setup
        productService.addProduct(testProduct);
        
        // Try to reduce stock lebih dari yang tersedia
        productService.reduceStock("TEST001", 150);
    }

    /**
     * Mock DAO untuk testing tanpa database
     */
    private static class MockProductDAO implements ProductDAO {
        private java.util.Map<String, Product> storage = new java.util.HashMap<>();

        @Override
        public void insert(Product product) throws ProductException {
            storage.put(product.getCode(), product);
        }

        @Override
        public Product findByCode(String code) throws ProductException {
            return storage.get(code);
        }

        @Override
        public java.util.List<Product> findAll() throws ProductException {
            return new java.util.ArrayList<>(storage.values());
        }

        @Override
        public void update(Product product) throws ProductException {
            storage.put(product.getCode(), product);
        }

        @Override
        public void delete(String code) throws ProductException {
            storage.remove(code);
        }
    }
}
