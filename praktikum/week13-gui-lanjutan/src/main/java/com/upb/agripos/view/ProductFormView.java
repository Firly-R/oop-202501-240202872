package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductFormView {
    private VBox root;
    private TableView<Product> table;
    private TextField txtCode, txtName, txtPrice, txtStock;
    private ProductController controller;

    public ProductFormView(ProductController controller) {
        this.controller = controller;
        initUI();
    }

    @SuppressWarnings("unchecked")
    private void initUI() {
        root = new VBox(10);
        root.setPadding(new Insets(20));

        txtCode = new TextField(); txtCode.setPromptText("Kode");
        txtName = new TextField(); txtName.setPromptText("Nama");
        txtPrice = new TextField(); txtPrice.setPromptText("Harga");
        txtStock = new TextField(); txtStock.setPromptText("Stok");

        // Konfigurasi Tabel
        table = new TableView<>();
        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        table.getColumns().addAll(colCode, colName, colPrice, colStock);

        Button btnAdd = new Button("Tambah");
        btnAdd.setOnAction(e -> {
            controller.addProduct(txtCode.getText(), txtName.getText(), txtPrice.getText(), txtStock.getText());
            loadData();
        });

        // Tombol Hapus dengan Lambda
        Button btnDelete = new Button("Hapus Terpilih");
        btnDelete.setOnAction(e -> {
            Product selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                controller.deleteProduct(selected.getCode()); // Kirim String Code
                loadData();
            }
        });

        root.getChildren().addAll(txtCode, txtName, txtPrice, txtStock, btnAdd, btnDelete, table);
        loadData();
    }

    private void loadData() {
        table.setItems(FXCollections.observableArrayList(controller.getAllProducts()));
    }

    public Parent asParent() { return root; }
}