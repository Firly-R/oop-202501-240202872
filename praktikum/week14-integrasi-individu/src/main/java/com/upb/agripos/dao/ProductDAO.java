package com.upb.agripos.dao;

import java.util.List;
import com.upb.agripos.model.Product;

/**
 * ProductDAO interface
 * Mendefinisikan contract untuk operasi database
 * Menerapkan Dependency Inversion Principle (DIP)
 */
public interface ProductDAO {
    void insert(Product product) throws ProductException;
    Product findByCode(String code) throws ProductException;
    List<Product> findAll() throws ProductException;
    void update(Product product) throws ProductException;
    void delete(String code) throws ProductException;
}
