package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;
import java.util.List;

public class ProductService {
    private ProductDAO dao;

    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public void addProduct(Product product) {
        dao.save(product);
    }

    public Product getProduct(int id) {
        return dao.findById(id);
    }

    public List<Product> getAllProducts() {
        return dao.findAll();
    }

    public void updateProduct(Product product) {
        dao.update(product);
    }

    public void deleteProduct(int id) {
        dao.delete(id);
    }
}
