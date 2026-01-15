package com.upb.agripos.model;

import java.util.*;

/**
 * Cart model class
 * Merepresentasikan keranjang belanja menggunakan Collections
 * Menggunakan Singleton Pattern untuk akses global
 */
public class Cart {
    private static Cart instance;
    private List<CartItem> items;

    private Cart() {
        this.items = new ArrayList<>();
    }

    // Singleton pattern
    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    // Add item to cart
    public void addItem(Product product, int quantity) {
        // Check if product already exists in cart
        for (CartItem item : items) {
            if (item.getProduct().getCode().equals(product.getCode())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // If not found, add new item
        items.add(new CartItem(product, quantity));
    }

    // Remove item from cart
    public void removeItem(String productCode) {
        items.removeIf(item -> item.getProduct().getCode().equals(productCode));
    }

    // Get all items
    public List<CartItem> getItems() {
        return new ArrayList<>(items);
    }

    // Get total price
    public double getTotalPrice() {
        return items.stream().mapToDouble(CartItem::getSubtotal).sum();
    }

    // Clear cart
    public void clear() {
        items.clear();
    }

    // Get number of items
    public int getItemCount() {
        return items.size();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
