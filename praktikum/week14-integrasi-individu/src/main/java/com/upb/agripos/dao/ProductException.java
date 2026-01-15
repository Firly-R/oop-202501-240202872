package com.upb.agripos.dao;

/**
 * Custom Exception untuk validasi dan error handling
 * Digunakan ketika terjadi kesalahan pada proses CRUD
 */
public class ProductException extends Exception {
    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
