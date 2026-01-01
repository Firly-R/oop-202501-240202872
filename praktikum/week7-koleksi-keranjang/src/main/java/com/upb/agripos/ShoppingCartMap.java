package com.upb.agripos;

import java.util.HashMap;
import java.util.Map;
import java.text.DecimalFormat;

public class ShoppingCartMap {

    private final Map<Product, Integer> items = new HashMap<>();
    private final DecimalFormat df = new DecimalFormat("#");

    // tambahProduk
    public void addProduct(Product p) {
        items.put(p, items.getOrDefault(p, 0) + 1);
    }

    // hapusProduk
    public void removeProduct(Product p) {
        if (!items.containsKey(p)) return;

        int qty = items.get(p);
        if (qty > 1) {
            items.put(p, qty - 1);
        } else {
            items.remove(p);
        }

        System.out.println("\nProduk dihapus: " + p.getCode() + " " + p.getName());
    }

    // hitungTotal
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

    // tampilkan keranjang
    public void printCart() {
        System.out.println("Isi Keranjang:");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            Product p = e.getKey();
            int qty = e.getValue();
            System.out.println("- " + p.getCode() + " " + p.getName()
                    + " x" + qty + " = " + df.format(p.getPrice() * qty));
        }
        System.out.println("Total: " + df.format(getTotal()));
    }
}
