package com.upb.agripos.view;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.Product;
import com.upb.agripos.model.CartItem;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * PosView class
 * GUI untuk aplikasi Agri-POS menggunakan JavaFX
 * Menerapkan MVC pattern dengan pemisahan View dari logika bisnis
 */
public class PosView {
    private VBox root;
    private TableView<Product> productTable;
    private TableView<CartItem> cartTable;
    private TextField txtCode, txtName, txtPrice, txtStock, txtQuantity;
    private Label lblCartTotal, lblCartCount;
    private PosController controller;

    public PosView(PosController controller) {
        this.controller = controller;
        initUI();
    }

    @SuppressWarnings("unchecked")
    private void initUI() {
        root = new VBox(10);
        root.setPadding(new Insets(15));

        // ========== Title ==========
        Label lblTitle = new Label("Agri-POS - Sistem Penjualan Pertanian");
        lblTitle.setStyle("-fx-font-size: 16; -fx-font-weight: bold;");

        // ========== Product Input Section ==========
        VBox productInputSection = createProductInputSection();

        // ========== Product Table Section ==========
        VBox productTableSection = createProductTableSection();

        // ========== Cart Section ==========
        VBox cartSection = createCartSection();

        // ========== Add to Cart Section ==========
        HBox addToCartSection = createAddToCartSection();

        // Add all sections to root
        root.getChildren().addAll(
                lblTitle,
                new Separator(),
                productInputSection,
                productTableSection,
                new Separator(),
                addToCartSection,
                cartSection
        );

        loadData();
    }

    private VBox createProductInputSection() {
        VBox section = new VBox(10);
        section.setStyle("-fx-border-color: #cccccc; -fx-padding: 10;");

        Label lblSection = new Label("Kelola Produk");
        lblSection.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

        HBox inputRow1 = new HBox(10);
        txtCode = new TextField();
        txtCode.setPromptText("Kode Produk");
        txtCode.setPrefWidth(120);

        txtName = new TextField();
        txtName.setPromptText("Nama Produk");
        txtName.setPrefWidth(200);

        inputRow1.getChildren().addAll(
                new Label("Kode:"), txtCode,
                new Label("Nama:"), txtName
        );

        HBox inputRow2 = new HBox(10);
        txtPrice = new TextField();
        txtPrice.setPromptText("Harga");
        txtPrice.setPrefWidth(100);

        txtStock = new TextField();
        txtStock.setPromptText("Stok");
        txtStock.setPrefWidth(100);

        inputRow2.getChildren().addAll(
                new Label("Harga:"), txtPrice,
                new Label("Stok:"), txtStock
        );

        Button btnAdd = new Button("Tambah Produk");
        btnAdd.setPrefWidth(150);
        btnAdd.setStyle("-fx-font-size: 11;");
        btnAdd.setOnAction(e -> {
            controller.addProduct(txtCode.getText(), txtName.getText(), txtPrice.getText(), txtStock.getText());
            clearProductInputs();
            loadData();
        });

        section.getChildren().addAll(lblSection, inputRow1, inputRow2, btnAdd);
        return section;
    }

    private VBox createProductTableSection() {
        VBox section = new VBox(10);

        Label lblSection = new Label("Daftar Produk");
        lblSection.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

        productTable = new TableView<>();

        TableColumn<Product, String> colCode = new TableColumn<>("Kode");
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colCode.setPrefWidth(80);

        TableColumn<Product, String> colName = new TableColumn<>("Nama");
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colName.setPrefWidth(180);

        TableColumn<Product, Double> colPrice = new TableColumn<>("Harga");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colPrice.setPrefWidth(100);

        TableColumn<Product, Integer> colStock = new TableColumn<>("Stok");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colStock.setPrefWidth(80);

        TableColumn<Product, Void> colAction = new TableColumn<>("Aksi");
        colAction.setPrefWidth(100);
        colAction.setCellFactory(param -> new TableCell<Product, Void>() {
            private final Button btnDelete = new Button("Hapus");

            {
                btnDelete.setStyle("-fx-font-size: 10;");
                btnDelete.setOnAction(e -> {
                    Product product = getTableView().getItems().get(getIndex());
                    controller.deleteProduct(product.getCode());
                    loadData();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnDelete);
            }
        });

        productTable.getColumns().addAll(colCode, colName, colPrice, colStock, colAction);
        productTable.setPrefHeight(200);

        section.getChildren().addAll(lblSection, productTable);
        return section;
    }

    private HBox createAddToCartSection() {
        HBox section = new HBox(10);
        section.setStyle("-fx-border-color: #cccccc; -fx-padding: 10; -fx-alignment: center-left;");

        Label lblSection = new Label("Tambah ke Keranjang:");
        lblSection.setStyle("-fx-font-weight: bold;");

        TextField txtProductCode = new TextField();
        txtProductCode.setPromptText("Kode Produk");
        txtProductCode.setPrefWidth(100);

        txtQuantity = new TextField();
        txtQuantity.setPromptText("Qty");
        txtQuantity.setPrefWidth(80);

        Button btnAddToCart = new Button("Tambah");
        btnAddToCart.setStyle("-fx-font-size: 11;");
        btnAddToCart.setOnAction(e -> {
            try {
                int qty = Integer.parseInt(txtQuantity.getText());
                controller.addToCart(txtProductCode.getText(), qty);
                txtProductCode.clear();
                txtQuantity.clear();
                refreshCart();
            } catch (NumberFormatException ex) {
                System.err.println("Jumlah harus angka");
            }
        });

        section.getChildren().addAll(lblSection, txtProductCode, new Label("Qty:"), txtQuantity, btnAddToCart);
        return section;
    }

    private VBox createCartSection() {
        VBox section = new VBox(10);

        HBox headerBox = new HBox(10);
        headerBox.setPrefHeight(40);

        Label lblSection = new Label("Keranjang Belanja");
        lblSection.setStyle("-fx-font-size: 12; -fx-font-weight: bold;");

        HBox cartStatsBox = new HBox(20);
        lblCartCount = new Label("Item: 0");
        lblCartTotal = new Label("Total: Rp 0");
        lblCartTotal.setStyle("-fx-font-weight: bold;");
        cartStatsBox.getChildren().addAll(lblCartCount, lblCartTotal);

        headerBox.getChildren().addAll(lblSection, cartStatsBox);
        headerBox.getChildren().get(0).prefWidth(200);

        cartTable = new TableView<>();

        TableColumn<CartItem, String> colProdCode = new TableColumn<>("Kode");
        colProdCode.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduct().getCode())
        );
        colProdCode.setPrefWidth(80);

        TableColumn<CartItem, String> colProdName = new TableColumn<>("Nama");
        colProdName.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProduct().getName())
        );
        colProdName.setPrefWidth(150);

        TableColumn<CartItem, Double> colProdPrice = new TableColumn<>("Harga");
        colProdPrice.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getProduct().getPrice()).asObject()
        );
        colProdPrice.setPrefWidth(100);

        TableColumn<CartItem, Integer> colQty = new TableColumn<>("Qty");
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colQty.setPrefWidth(60);

        TableColumn<CartItem, Double> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getSubtotal()).asObject()
        );
        colSubtotal.setPrefWidth(100);

        TableColumn<CartItem, Void> colRemove = new TableColumn<>("Aksi");
        colRemove.setPrefWidth(80);
        colRemove.setCellFactory(param -> new TableCell<CartItem, Void>() {
            private final Button btnRemove = new Button("Hapus");

            {
                btnRemove.setStyle("-fx-font-size: 10;");
                btnRemove.setOnAction(e -> {
                    CartItem item = getTableView().getItems().get(getIndex());
                    controller.removeFromCart(item.getProduct().getCode());
                    refreshCart();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btnRemove);
            }
        });

        cartTable.getColumns().addAll(colProdCode, colProdName, colProdPrice, colQty, colSubtotal, colRemove);
        cartTable.setPrefHeight(150);

        Button btnClearCart = new Button("Kosongkan Keranjang");
        btnClearCart.setStyle("-fx-font-size: 11;");
        btnClearCart.setOnAction(e -> {
            controller.clearCart();
            refreshCart();
        });

        Button btnCheckout = new Button("Checkout");
        btnCheckout.setStyle("-fx-font-size: 11; -fx-padding: 8 20; -fx-font-weight: bold;");
        btnCheckout.setOnAction(e -> {
            if (controller.getCartItemCount() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Keranjang Kosong");
                alert.setHeaderText("Tidak ada item");
                alert.setContentText("Tambahkan produk ke keranjang terlebih dahulu");
                alert.showAndWait();
                return;
            }
            
            try {
                // Show receipt before processing
                showReceipt();
                
                // Process checkout and reduce stock
                controller.checkout();
                
            } catch (Exception ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Checkout");
                alert.setHeaderText("Gagal memproses transaksi");
                alert.setContentText(ex.getMessage());
                alert.showAndWait();
            }
        });

        HBox actionBox = new HBox(10);
        actionBox.setAlignment(Pos.CENTER);
        actionBox.getChildren().addAll(btnClearCart, btnCheckout);

        section.getChildren().addAll(headerBox, cartTable, actionBox);
        return section;
    }

    private void loadData() {
        productTable.setItems(FXCollections.observableArrayList(controller.getAllProducts()));
        refreshCart();
    }

    private void showReceipt() {
        StringBuilder struk = new StringBuilder();
        struk.append("════════════════════════════════════════\n");
        struk.append("          AGRI-POS RECEIPT\n");
        struk.append("════════════════════════════════════════\n");
        struk.append("Tanggal: ").append(java.time.LocalDateTime.now()).append("\n");
        struk.append("────────────────────────────────────────\n");
        struk.append("ITEM                    QTY   HARGA     SUBTOTAL\n");
        struk.append("────────────────────────────────────────\n");
        
        double grandTotal = 0;
        for (com.upb.agripos.model.CartItem item : controller.getCartItems()) {
            String itemName = item.getProduct().getName();
            if (itemName.length() > 18) {
                itemName = itemName.substring(0, 15) + "...";
            }
            
            double subtotal = item.getSubtotal();
            grandTotal += subtotal;
            
            struk.append(String.format("%-18s %3d Rp%9.0f Rp%10.0f\n",
                itemName,
                item.getQuantity(),
                item.getProduct().getPrice(),
                subtotal
            ));
        }
        
        struk.append("────────────────────────────────────────\n");
        struk.append(String.format("GRAND TOTAL:                    Rp %10.0f\n", grandTotal));
        struk.append("════════════════════════════════════════\n");
        struk.append("Terima Kasih atas pembelian Anda!\n");
        struk.append("════════════════════════════════════════\n");
        
        // Show receipt dialog
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("STRUK PENJUALAN");
        alert.setHeaderText("Transaksi Berhasil");
        
        TextArea textArea = new TextArea(struk.toString());
        textArea.setEditable(false);
        textArea.setWrapText(false);
        textArea.setStyle("-fx-font-family: 'Monospaced'; -fx-font-size: 10;");
        textArea.setPrefRowCount(15);
        textArea.setPrefColumnCount(40);
        
        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
        
        // Clear cart after showing receipt
        controller.clearCart();
        refreshCart();
        
        // Reload product data to show updated stock from database
        productTable.setItems(FXCollections.observableArrayList(controller.getAllProducts()));
    }

    private void refreshCart() {
        cartTable.setItems(FXCollections.observableArrayList(controller.getCartItems()));
        lblCartCount.setText("Item: " + controller.getCartItemCount());
        lblCartTotal.setText("Total: Rp " + String.format("%.2f", controller.getCartTotal()));
    }

    private void clearProductInputs() {
        txtCode.clear();
        txtName.clear();
        txtPrice.clear();
        txtStock.clear();
    }

    public Parent asParent() { return root; }
}
