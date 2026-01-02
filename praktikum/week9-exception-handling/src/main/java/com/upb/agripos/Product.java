package main.java.com.upb.agripos;


public class Product {
private String name;
private int stock;


public Product(String code, String name, double price, int stock) {
this.name = name;
this.stock = stock;
}


public String getName() { return name; }
public int getStock() { return stock; }


public void reduceStock(int qty) {
this.stock -= qty;
}
}