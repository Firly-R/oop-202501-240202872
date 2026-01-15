package com.upb.agripos.service;

import com.upb.agripos.model.Product;
import com.upb.agripos.model.CartItem;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * CartServiceTest - Unit Test untuk CartService
 * Menguji logika bisnis keranjang tanpa dependency UI
 */
public class CartServiceTest {
    private CartService cartService;
    private Product testProduct1;
    private Product testProduct2;

    @Before
    public void setUp() {
        // Reset Cart singleton for each test
        cartService = new CartService();
        cartService.clearCart();
        
        testProduct1 = new Product("P001", "Beras", 50000, 100);
        testProduct2 = new Product("P002", "Gula", 12000, 50);
    }

    @Test
    public void testAddToCart() {
        cartService.addToCart(testProduct1, 2);
        assertEquals(1, cartService.getCartItemCount());
        assertEquals(100000, cartService.getCartTotal(), 0.01);
    }

    @Test
    public void testAddMultipleItems() {
        cartService.addToCart(testProduct1, 1);
        cartService.addToCart(testProduct2, 3);
        assertEquals(2, cartService.getCartItemCount());
        assertEquals(50000 + 36000, cartService.getCartTotal(), 0.01);
    }

    @Test
    public void testAddSameProductIncreaseQuantity() {
        cartService.addToCart(testProduct1, 2);
        cartService.addToCart(testProduct1, 3);
        
        assertEquals(1, cartService.getCartItemCount());
        CartItem item = cartService.getCartItems().get(0);
        assertEquals(5, item.getQuantity());
        assertEquals(250000, cartService.getCartTotal(), 0.01);
    }

    @Test
    public void testRemoveFromCart() {
        cartService.addToCart(testProduct1, 2);
        cartService.addToCart(testProduct2, 1);
        
        cartService.removeFromCart("P001");
        assertEquals(1, cartService.getCartItemCount());
        assertEquals(12000, cartService.getCartTotal(), 0.01);
    }

    @Test
    public void testClearCart() {
        cartService.addToCart(testProduct1, 2);
        cartService.addToCart(testProduct2, 3);
        
        cartService.clearCart();
        assertEquals(0, cartService.getCartItemCount());
        assertEquals(0, cartService.getCartTotal(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToCartWithZeroQuantity() {
        cartService.addToCart(testProduct1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToCartWithNegativeQuantity() {
        cartService.addToCart(testProduct1, -5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToCartExceedsStock() {
        cartService.addToCart(testProduct1, 150);
    }

    @Test
    public void testGetCartItems() {
        cartService.addToCart(testProduct1, 2);
        cartService.addToCart(testProduct2, 1);
        
        assertEquals(2, cartService.getCartItems().size());
    }

    @Test
    public void testCartItemSubtotal() {
        cartService.addToCart(testProduct1, 3);
        CartItem item = cartService.getCartItems().get(0);
        assertEquals(150000, item.getSubtotal(), 0.01); // 3 * 50000
    }

    @Test
    public void testMultipleCheckoutScenarios() {
        // Simulasi multiple checkout scenarios
        cartService.addToCart(testProduct1, 5);
        cartService.addToCart(testProduct2, 10);
        
        double expectedTotal = (5 * 50000) + (10 * 12000);
        assertEquals(expectedTotal, cartService.getCartTotal(), 0.01);
        
        // Clear dan test lagi
        cartService.clearCart();
        cartService.addToCart(testProduct1, 1);
        assertEquals(1, cartService.getCartItemCount());
        assertEquals(50000, cartService.getCartTotal(), 0.01);
    }
}
