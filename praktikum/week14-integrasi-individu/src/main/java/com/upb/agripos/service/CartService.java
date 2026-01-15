package com.upb.agripos.service;

import com.upb.agripos.model.Cart;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.model.Product;
import java.util.List;

/**
 * CartService class
 * Business logic untuk operasi keranjang belanja
 * Menggunakan Singleton Cart dan menyediakan metode bisnis untuk keranjang
 */
public class CartService {
    private Cart cart;

    public CartService() {
        this.cart = Cart.getInstance();
    }

    public void addToCart(Product product, int quantity) throws IllegalArgumentException {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Jumlah item harus lebih dari 0");
        }
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Stok tidak mencukupi");
        }
        cart.addItem(product, quantity);
    }

    public void removeFromCart(String productCode) {
        cart.removeItem(productCode);
    }

    public List<CartItem> getCartItems() {
        return cart.getItems();
    }

    public double getCartTotal() {
        return cart.getTotalPrice();
    }

    public void clearCart() {
        cart.clear();
    }

    public int getCartItemCount() {
        return cart.getItemCount();
    }
}
