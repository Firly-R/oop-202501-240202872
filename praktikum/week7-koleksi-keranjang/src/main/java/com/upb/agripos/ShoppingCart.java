package com.upb.agripos;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class ShoppingCart {

    private final ArrayList<Product> items = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("#");

    public void addProduct(Product p) {
        items.add(p);
    }

    public void removeProduct(Product p) {
        if (items.remove(p)) {
            System.out.println("\nProduk dihapus: " + p.getCode() + " " + p.getName());
        }
    }

    public double getTotal() {
        double total = 0;
        for (Product p : items) {
            total += p.getPrice();
        }
        return total;
    }

    public void printCart() {
        System.out.println("Isi Keranjang:");
        for (Product p : items) {
            System.out.println("- " + p.getCode() + " " + p.getName()
                    + " = " + df.format(p.getPrice()));
        }
        System.out.println("Total: " + df.format(getTotal()));
    }
}
