package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;
import java.util.List;

public class ProductService {
    private ProductDAO dao;

    public ProductService(ProductDAO dao) {
        this.dao = dao;
    }

    public void insert(Product p) throws Exception {
        dao.insert(p);
    }

    public List<Product> findAll() throws Exception {
        return dao.findAll();
    }
}