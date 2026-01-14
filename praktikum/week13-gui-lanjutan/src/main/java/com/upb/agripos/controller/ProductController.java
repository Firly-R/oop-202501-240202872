package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import java.util.ArrayList;
import java.util.List;

public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // Menangani error 'Unhandled exception type Exception'
    public List<Product> getAllProducts() {
        try {
            return service.getAllProducts();
        } catch (Exception e) {
            System.err.println("Gagal mengambil data: " + e.getMessage());
            return new ArrayList<>(); // Kembalikan list kosong agar UI tidak crash
        }
    }

    public void addProduct(String code, String name, String price, String stock) {
        try {
            // Konversi input dari View (String) ke tipe data Model
            double pPrice = Double.parseDouble(price);
            int pStock = Integer.parseInt(stock);
            
            Product p = new Product(code, name, pPrice, pStock);
            service.addProduct(p);
        } catch (NumberFormatException e) {
            System.err.println("Format angka salah: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method Hapus menggunakan String Code agar sinkron dengan View
    public void deleteProduct(String code) {
        try {
            service.deleteProduct(code);
        } catch (Exception e) {
            System.err.println("Gagal menghapus produk: " + e.getMessage());
            e.printStackTrace();
        }
    }
}