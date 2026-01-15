package com.upb.agripos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.upb.agripos.dao.JdbcProductDAO;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.service.CartService;
import com.upb.agripos.controller.PosController;
import com.upb.agripos.view.PosView;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * AppJavaFX - Main Application Class
 * Entry point untuk aplikasi Agri-POS Week 14
 */
public class AppJavaFX extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Print identitas
        System.out.println("Hello World, I am 240202872");
        
        // Setup database connection
        Connection conn = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/agripos", 
            "postgres", 
            "admin321"
        );
        
        // Initialize MVC
        JdbcProductDAO dao = new JdbcProductDAO(conn);
        ProductService productService = new ProductService(dao);
        CartService cartService = new CartService();
        PosController controller = new PosController(productService, cartService);
        PosView view = new PosView(controller);

        // Setup JavaFX stage
        primaryStage.setTitle("Agri-POS - Week 14 Integrasi Individu");
        primaryStage.setScene(new Scene(view.asParent(), 900, 700));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
