package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductException;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.CartItem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 * CheckoutFlowTest - Integration test untuk stock reduction flow
 * Mensimulasikan skenario checkout lengkap dengan stock management
 */
public class CheckoutFlowTest {
    private ProductService productService;
    private CartService cartService;
    private MockProductDAO mockDAO;

    @Before
    public void setUp() {
        mockDAO = new MockProductDAO();
        productService = new ProductService(mockDAO);
        cartService = new CartService();
        cartService.clearCart();
    }

    @Test
    public void testCompleteCheckoutFlow() throws ProductException {
        // Step 1: Setup produk awal
        Product beras = new Product("P001", "Beras", 50000, 100);
        Product gula = new Product("P002", "Gula", 12000, 50);
        
        productService.addProduct(beras);
        productService.addProduct(gula);
        
        // Verify initial stock
        assertEquals(100, productService.getProductByCode("P001").getStock());
        assertEquals(50, productService.getProductByCode("P002").getStock());
        
        // Step 2: Simulasi pembelian
        cartService.addToCart(beras, 10);  // Beli 10 Beras
        cartService.addToCart(gula, 5);    // Beli 5 Gula
        
        assertEquals(2, cartService.getCartItemCount());
        assertEquals(500000 + 60000, cartService.getCartTotal(), 0.01);
        
        // Step 3: Checkout - Reduce stock untuk setiap item
        for (CartItem item : cartService.getCartItems()) {
            productService.reduceStock(item.getProduct().getCode(), item.getQuantity());
        }
        
        // Step 4: Verify stock berkurang
        assertEquals(90, productService.getProductByCode("P001").getStock());
        assertEquals(45, productService.getProductByCode("P002").getStock());
        
        // Step 5: Clear cart
        cartService.clearCart();
        assertEquals(0, cartService.getCartItemCount());
    }

    @Test
    public void testMultipleCheckoutTransactions() throws ProductException {
        // Setup
        Product produk = new Product("P001", "Test", 10000, 100);
        productService.addProduct(produk);
        
        // Transaction 1: Beli 25
        cartService.addToCart(produk, 25);
        for (CartItem item : cartService.getCartItems()) {
            productService.reduceStock(item.getProduct().getCode(), item.getQuantity());
        }
        assertEquals(75, productService.getProductByCode("P001").getStock());
        cartService.clearCart();
        
        // Transaction 2: Beli 30
        Product updated = productService.getProductByCode("P001");
        cartService.addToCart(updated, 30);
        for (CartItem item : cartService.getCartItems()) {
            productService.reduceStock(item.getProduct().getCode(), item.getQuantity());
        }
        assertEquals(45, productService.getProductByCode("P001").getStock());
        cartService.clearCart();
        
        // Transaction 3: Beli 45 (habis)
        updated = productService.getProductByCode("P001");
        cartService.addToCart(updated, 45);
        for (CartItem item : cartService.getCartItems()) {
            productService.reduceStock(item.getProduct().getCode(), item.getQuantity());
        }
        assertEquals(0, productService.getProductByCode("P001").getStock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckoutStockInsufficient() throws ProductException {
        // Setup
        Product produk = new Product("P001", "Test", 10000, 20);
        productService.addProduct(produk);
        
        // Try to add to cart 25 (lebih dari stok 20)
        // CartService akan throw IllegalArgumentException saat addToCart
        cartService.addToCart(produk, 25);
    }

    @Test
    public void testStockReductionAccuracy() throws ProductException {
        // Verify akurasi pengurangan stok
        Product p = new Product("P001", "Produk", 10000, 1000);
        productService.addProduct(p);
        
        // Reduce in various amounts
        productService.reduceStock("P001", 123);
        assertEquals(877, productService.getProductByCode("P001").getStock());
        
        productService.reduceStock("P001", 456);
        assertEquals(421, productService.getProductByCode("P001").getStock());
        
        productService.reduceStock("P001", 421);
        assertEquals(0, productService.getProductByCode("P001").getStock());
    }

    /**
     * Mock DAO untuk testing tanpa database
     */
    private static class MockProductDAO implements ProductDAO {
        private Map<String, Product> storage = new HashMap<>();

        @Override
        public void insert(Product product) throws ProductException {
            storage.put(product.getCode(), new Product(
                product.getCode(),
                product.getName(),
                product.getPrice(),
                product.getStock()
            ));
        }

        @Override
        public Product findByCode(String code) throws ProductException {
            Product p = storage.get(code);
            if (p != null) {
                // Return copy untuk avoid external modification
                return new Product(p.getCode(), p.getName(), p.getPrice(), p.getStock());
            }
            return null;
        }

        @Override
        public List<Product> findAll() throws ProductException {
            List<Product> list = new ArrayList<>();
            for (Product p : storage.values()) {
                list.add(new Product(p.getCode(), p.getName(), p.getPrice(), p.getStock()));
            }
            return list;
        }

        @Override
        public void update(Product product) throws ProductException {
            if (storage.containsKey(product.getCode())) {
                storage.put(product.getCode(), new Product(
                    product.getCode(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
                ));
            } else {
                throw new ProductException("Produk tidak ditemukan: " + product.getCode());
            }
        }

        @Override
        public void delete(String code) throws ProductException {
            storage.remove(code);
        }
    }
}
