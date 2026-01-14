package com.upb.agripos.dao;

import java.util.List;
import com.upb.agripos.model.Product;

public interface ProductDAO {
    void insert(Product product) throws Exception;
    Product findByCode(String code) throws Exception;
    List<Product> findAll() throws Exception;
    void update(Product product) throws Exception;
    static void delete(Product product) throws Exception {
	
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}
    static void delete(String product) {
   
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
}