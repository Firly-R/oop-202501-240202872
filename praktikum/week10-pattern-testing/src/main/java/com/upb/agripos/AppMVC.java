package com.upb.agripos;

import com.upb.agripos.config.DatabaseConnection;
import com.upb.agripos.model.Product;
import com.upb.agripos.view.ConsoleView;
import com.upb.agripos.controller.ProductController;

public class AppMVC {
    public static void main(String[] args) {
        // Ganti dengan Nama dan NIM Anda sesuai instruksi
        System.out.println("Hello, I am Muhammad Firly Ramadhan-240202872 (Week10)");

        // Uji coba Singleton
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        db1.executeQuery("SELECT * FROM products");

        // Implementasi MVC
        Product product = new Product("P01", "Pupuk Organik");
        ConsoleView view = new ConsoleView();
        ProductController controller = new ProductController(product, view);
        
        controller.updateView();
    }
}