package com.upb.agripos.controller;

import com.upb.agripos.service.ProductService;
import com.upb.agripos.model.Product;
import java.util.List;

public class ProductController {
    private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    public void addProduct(Product product) {
        service.addProduct(product);
    }

    public Product getProduct(int id) {
        return service.getProduct(id);
    }

    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    public void updateProduct(Product product) {
        service.updateProduct(product);
    }

    public void deleteProduct(int id) {
        service.deleteProduct(id);
    }
}
