package com.upb.agripos.service;

import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductException;
import com.upb.agripos.model.Product;
import java.util.List;

/**
 * ProductService class
 * Business logic untuk operasi produk
 * Menerapkan layering architecture (Service layer)
 */
public class ProductService {
    private ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() throws ProductException {
        return productDAO.findAll();
    }

    public void addProduct(Product p) throws ProductException {
        productDAO.insert(p);
    }

    public void deleteProduct(String code) throws ProductException {
        productDAO.delete(code);
    }

    public void updateProduct(Product p) throws ProductException {
        productDAO.update(p);
    }

    public Product getProductByCode(String code) throws ProductException {
        return productDAO.findByCode(code);
    }

    public void reduceStock(String code, int quantity) throws ProductException {
        Product product = getProductByCode(code);
        if (product != null) {
            int newStock = product.getStock() - quantity;
            if (newStock < 0) {
                throw new ProductException("Stok tidak cukup untuk kode produk: " + code);
            }
            product.setStock(newStock);
            updateProduct(product);
        }
    }
}
