package main.java.com.upb.agripos;


public class MainExceptionDemo {
public static void main(String[] args) {
System.out.println("Hallo, Saya Muhammad Firly Ramadhan-240202872 (Week9)");


Product pupuk = new Product("P01", "Pupuk Organik", 25000, 3);
ShoppingCart cart = new ShoppingCart();


try {
cart.addProduct(pupuk, -1);
} catch (InvalidQuantityException e) {
System.out.println("Kesalahan: " + e.getMessage());
}


try {
cart.removeProduct(pupuk);
} catch (ProductNotFoundException e) {
System.out.println("Kesalahan: " + e.getMessage());
}
try {
cart.addProduct(pupuk, 5);
cart.checkout();
} catch (Exception e) {
System.out.println("Kesalahan: " + e.getMessage());
}
}
}
