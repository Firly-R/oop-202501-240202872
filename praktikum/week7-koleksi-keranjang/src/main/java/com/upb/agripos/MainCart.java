package com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {

        System.out.println("Hallo, saya Muhammad Firly Ramadhan-240202872 (Week7)");

        Product p1 = new Product("P01", "Beras", 50000);
        Product p2 = new Product("P02", "Pupuk", 30000);

        ShoppingCartMap cart = new ShoppingCartMap();

        cart.addProduct(p1);
        cart.addProduct(p1); // qty jadi 2
        cart.addProduct(p2);
        cart.printCart();

        cart.removeProduct(p1);
        System.out.println();
        cart.printCart();
    }
}
