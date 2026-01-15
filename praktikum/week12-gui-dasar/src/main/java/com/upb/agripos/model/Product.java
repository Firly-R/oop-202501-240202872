package com.upb.agripos.model;

public class Product {
    private String name;
    private String code;
    private double price;
    private int stock;

    public Product(String name, String code, double price, int stock) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.stock = stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return name + " - Rp " + price + " (Stok: " + stock + ")";
    }
}