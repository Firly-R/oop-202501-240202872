package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProductFormView {
    private ProductController controller;
    private TextField nameField;
    private TextField priceField;
    private TextField stockField;
    private TextField codeField;
    private ListView<Product> productList;
    private VBox root;

    public ProductFormView(ProductController controller) {
        this.controller = controller;
        this.root = createUI();
    }

    private VBox createUI() {
        VBox mainBox = new VBox();
        mainBox.setPadding(new Insets(15));
        mainBox.setSpacing(10);

        Label title = new Label("Agri-POS - Kelola Produk");
        title.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

        VBox formBox = createFormBox();
        HBox buttonBox = createButtonBox();

        productList = new ListView<>();
        productList.setPrefHeight(250);

        mainBox.getChildren().addAll(
            title,
            new Separator(),
            formBox,
            buttonBox,
            new Label("Daftar Produk:"),
            productList
        );

        return mainBox;
    }

    private VBox createFormBox() {
        VBox formBox = new VBox();
        formBox.setSpacing(10);
        formBox.setStyle("-fx-border-color: #cccccc; -fx-padding: 10;");

        nameField = new TextField();
        priceField = new TextField();
        stockField = new TextField();
        codeField = new TextField();

        formBox.getChildren().addAll(
            new Label("Nama Produk:"), nameField,
            new Label("Harga:"), priceField,
            new Label("Stok:"), stockField,
            new Label("Kode Produk:"), codeField
        );

        return formBox;
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);

        Button addButton = new Button("Tambah");
        addButton.setOnAction(e -> handleAddProduct());

        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> handleUpdateProduct());

        Button deleteButton = new Button("Hapus");
        deleteButton.setOnAction(e -> handleDeleteProduct());

        Button clearButton = new Button("Bersihkan");
        clearButton.setOnAction(e -> clearForm());

        buttonBox.getChildren().addAll(addButton, updateButton, deleteButton, clearButton);

        return buttonBox;
    }

    private void handleAddProduct() {
        try {
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(stockField.getText());
            String code = codeField.getText();

            if (name.isEmpty()) {
                showAlert("Error", "Nama produk tidak boleh kosong!");
                return;
            }

            Product product = new Product(name, code, price, stock);
            controller.addProduct(product);
            showAlert("Success", "Produk berhasil ditambahkan!");
            clearForm();
            loadProducts();
        } catch (NumberFormatException e) {
            showAlert("Error", "Harga dan stok harus berupa angka!");
        }
    }

    private void handleUpdateProduct() {
        showAlert("Info", "Fitur update belum diimplementasikan");
    }

    private void handleDeleteProduct() {
        showAlert("Info", "Fitur delete belum diimplementasikan");
    }

    private void clearForm() {
        nameField.clear();
        priceField.clear();
        stockField.clear();
        codeField.clear();
    }

    private void loadProducts() {
        productList.getItems().clear();
        productList.getItems().addAll(controller.getAllProducts());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox asParent() {
        return root;
    }
}