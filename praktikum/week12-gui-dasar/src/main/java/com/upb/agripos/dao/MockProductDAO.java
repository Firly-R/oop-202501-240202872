package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.ArrayList;
import java.util.List;

public class MockProductDAO implements ProductDAO {
    private List<Product> products = new ArrayList<>();

    @Override
    public void save(Product product) {
        products.add(product);
        System.out.println("Produk ditambahkan: " + product.getName());
    }

    @Override
    public Product findById(int id) {
        return products.size() > id ? products.get(id) : null;
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    @Override
    public void update(Product product) {
        // Simple update
    }

    @Override
    public void delete(int id) {
        if (id >= 0 && id < products.size()) {
            products.remove(id);
        }
    }
}