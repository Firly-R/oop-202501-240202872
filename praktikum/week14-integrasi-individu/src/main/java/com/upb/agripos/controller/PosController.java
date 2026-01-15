package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.model.CartItem;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.dao.ProductException;
import java.util.ArrayList;
import java.util.List;

/**
 * PosController class
 * Controller untuk menangani logika interaksi antara View dan Service
 * Menerapkan separation of concerns (Controller hanya mengkoordinasi)
 */
public class PosController {
    private ProductService productService;
    private CartService cartService;

    public PosController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    // ============ Product Management ============
    public List<Product> getAllProducts() {
        try {
            return productService.getAllProducts();
        } catch (ProductException e) {
            System.err.println("Error fetching products: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    public void addProduct(String code, String name, String price, String stock) {
        try {
            // Validasi input
            if (code == null || code.trim().isEmpty()) {
                throw new IllegalArgumentException("Kode produk tidak boleh kosong");
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("Nama produk tidak boleh kosong");
            }

            double dPrice = Double.parseDouble(price);
            int iStock = Integer.parseInt(stock);

            Product p = new Product(code, name, dPrice, iStock);
            productService.addProduct(p);
        } catch (NumberFormatException e) {
            System.err.println("Format input salah: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Validasi gagal: " + e.getMessage());
        } catch (ProductException e) {
            System.err.println("Error menambah produk: " + e.getMessage());
        }
    }

    public void deleteProduct(String code) {
        try {
            productService.deleteProduct(code);
        } catch (ProductException e) {
            System.err.println("Error menghapus produk: " + e.getMessage());
        }
    }

    public Product getProductByCode(String code) {
        try {
            return productService.getProductByCode(code);
        } catch (ProductException e) {
            System.err.println("Error mencari produk: " + e.getMessage());
            return null;
        }
    }

    // ============ Cart Management ============
    public void addToCart(String productCode, int quantity) {
        try {
            Product product = getProductByCode(productCode);
            if (product == null) {
                throw new IllegalArgumentException("Produk tidak ditemukan");
            }
            cartService.addToCart(product, quantity);
        } catch (IllegalArgumentException e) {
            System.err.println("Error menambah ke keranjang: " + e.getMessage());
        }
    }

    public void removeFromCart(String productCode) {
        cartService.removeFromCart(productCode);
    }

    public List<CartItem> getCartItems() {
        return cartService.getCartItems();
    }

    public double getCartTotal() {
        return cartService.getCartTotal();
    }

    public void clearCart() {
        cartService.clearCart();
    }

    public int getCartItemCount() {
        return cartService.getCartItemCount();
    }

    public void checkout() throws Exception {
        try {
            // Update stock untuk setiap item di keranjang
            for (CartItem item : cartService.getCartItems()) {
                productService.reduceStock(item.getProduct().getCode(), item.getQuantity());
            }
        } catch (Exception e) {
            System.err.println("Error checkout: " + e.getMessage());
            throw e;
        }
    }
}
