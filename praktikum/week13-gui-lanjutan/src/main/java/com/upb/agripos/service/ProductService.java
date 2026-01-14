package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.model.Product;
import java.util.List;

public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() throws Exception {
        return productDAO.findAll();
    }

    public void addProduct(Product p) throws Exception {
        productDAO.insert(p);
    }

    public void deleteProduct(String code) throws Exception {
        ProductDAO.delete(code); // Memanggil DAO untuk eksekusi SQL
    }
}