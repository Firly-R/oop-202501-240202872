package com.upb.agripos.dao;

import com.upb.agripos.model.Product;
import java.util.List;

public interface ProductDAO {
    void save(Product product);
    Product findById(int id);
    List<Product> findAll();
    void update(Product product);
    void delete(int id);
}