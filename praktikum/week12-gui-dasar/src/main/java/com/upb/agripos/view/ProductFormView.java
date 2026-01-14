package com.upb.agripos.view;

import com.upb.agripos.controller.ProductController;
import com.upb.agripos.model.Product;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ProductFormView {
    private VBox root;
    private TextField txtCode, txtName, txtPrice, txtStock;
    private Button btnAdd;
    private ListView<String> listView; // Ini komponen daftar produknya
    private ProductController controller;

    public ProductFormView(ProductController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        root = new VBox(10); // Susun secara vertikal dengan jarak 10px
        root.setPadding(new Insets(20));

        txtCode = new TextField(); txtCode.setPromptText("Kode Produk");
        txtName = new TextField(); txtName.setPromptText("Nama Produk");
        txtPrice = new TextField(); txtPrice.setPromptText("Harga");
        txtStock = new TextField(); txtStock.setPromptText("Stok");
        btnAdd = new Button("Tambah Produk");
        listView = new ListView<>(); // Inisialisasi kotak daftar

        // Tambahkan semua komponen ke dalam layar
        root.getChildren().addAll(
            new Label("Form Input Produk Agri-POS"),
            new Label("Kode Produk:"), txtCode,
            new Label("Nama Produk:"), txtName,
            new Label("Harga:"), txtPrice,
            new Label("Stok:"), txtStock,
            btnAdd,
            new Label("Daftar Produk:"), listView
        );

        // Ambil data dari database saat aplikasi baru dibuka
        refreshTable();

        // Aksi ketika tombol diklik
        btnAdd.setOnAction(event -> {
            controller.addProduct(txtCode.getText(), txtName.getText(), txtPrice.getText(), txtStock.getText());
            refreshTable(); // Update daftar setelah simpan ke DB
            clearFields();
        });
    }

    // Fungsi untuk mengambil data terbaru dari database
    private void refreshTable() {
        listView.getItems().clear();
        for (Product p : controller.getAllProducts()) {
            listView.getItems().add(p.getCode() + " - " + p.getName() + " (Stok: " + p.getStock() + ")");
        }
    }

    private void clearFields() {
        txtCode.clear(); txtName.clear(); txtPrice.clear(); txtStock.clear();
    }

    public Parent asParent() { return root; }
}