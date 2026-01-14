package com.upb.agripos.controller;

import com.upb.agripos.model.Product;
import com.upb.agripos.service.ProductService;
import java.util.List; // Penting: Menghilangkan error 'List cannot be resolved'
import java.util.ArrayList;

public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // Fungsi untuk menambah produk
    public void addProduct(String code, String name, String price, String stock) {
        try {
            double p = Double.parseDouble(price);
            int s = Integer.parseInt(stock);
            Product product = new Product(code, name, p, s);
            service.addProduct(product); // Mengirim ke service
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fungsi untuk mengambil daftar produk agar muncul di ListView
    public List<Product> getAllProducts() {
        try {
            return service.findAll(); // Memanggil service sesuai alur MVC
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}