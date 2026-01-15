package com.upb.agripos;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.service.ProductService;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.MockProductDAO;
import com.upb.agripos.view.ProductFormView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Gunakan Mock DAO tanpa database dulu
            ProductDAO dao = new MockProductDAO();
            ProductService service = new ProductService(dao);
            ProductController controller = new ProductController(service);
            ProductFormView view = new ProductFormView(controller);

            Scene scene = new Scene(view.asParent(), 500, 700);
            primaryStage.setTitle("Agri-POS - Kelola Produk");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}