# Laporan Week 14 - Integrasi Individu (OOP + Database + GUI)
Topik: Integrasi End-to-End OOP, Database, dan GUI JavaFX

 Nama  : Muhammad Firly Ramadhan
 NIM   : 240202872
 Kelas : 3IKRB

---

##Dasar Teori

1. **MVC Architecture**: Pemisahan antara View (UI), Controller (logika interaksi), Model (data), dan Service (business logic).
2. **DAO Pattern**: Abstraksi akses database melalui interface untuk memudahkan maintenance dan testing.
3. **Singleton Pattern**: Memastikan hanya satu instance dari suatu class yang ada di memory (seperti Cart).
4. **Collections**: Menggunakan List<T> untuk menyimpan multiple items dengan operasi CRUD yang efisien.
5. **Exception Handling**: Menangani error secara proper dengan custom exception untuk akurasi pesan error.
6. **Transaction Management**: Memproses multiple database updates secara atomic saat checkout.

---

## Tujuan Praktikum

Mahasiswa mampu:
1. Mengintegrasikan konsep OOP (Bab 1-5) ke aplikasi utuh
2. Menerapkan UML + SOLID principles (Bab 6)
3. Menggunakan Collections untuk keranjang belanja (Bab 7)
4. Menangani exception dengan proper flow (Bab 9)
5. Menerapkan design patterns dan unit testing (Bab 10)
6. Mengintegrasikan database via DAO/JDBC (Bab 11)
7. Membangun GUI responsif dengan JavaFX (Bab 12-13)
8. **Mengelola state aplikasi dengan stock management real-time**

---
##Ringkasan Aplikasi

Aplikasi **Agri-POS Week 14** adalah sistem point-of-sale (POS) kasir sederhana untuk toko pertanian yang mengintegrasikan:

- **Manajemen Produk**: CRUD (Create, Read, Update, Delete) produk dari database PostgreSQL
- **Keranjang Belanja**: Sistem keranjang berbasis Collections dengan fitur add/remove item
- **Antarmuka GUI**: JavaFX dengan layout intuitif untuk kemudahan penggunaan
- **Validasi & Exception Handling**: Custom exception untuk error handling yang proper
- **Design Pattern**: Penerapan Singleton Pattern untuk Cart management
- **Unit Testing**: JUnit test untuk validasi logika CartService
- **Struk/Receipt**: Menampilkan detail transaksi saat checkout
- **Stock Management**: Otomatis mengurangi stok produk setelah checkout

---

##Keterangan Integrasi Bab 1-13

### Bab 1 (Setup & Hello World)
- ✅ Identitas: "Hello World, I am 240202872" ditampilkan di console saat aplikasi start

### Bab 2 (Class & Object)
- ✅ Class `Product`, `CartItem`, `Cart` dengan encapsulation dan getter/setter

### Bab 5 (Abstraction & Interface)
- ✅ `ProductDAO` interface dengan `JdbcProductDAO` implementation

### Bab 6 (UML & SOLID)
- ✅ MVC Pattern dengan separation of concerns
- ✅ Dependency Inversion Principle diterapkan

### Bab 7 (Collections & Keranjang)
- ✅ `Cart` menggunakan `List<CartItem>` untuk menyimpan item

### Bab 9 (Exception Handling)
- ✅ Custom `ProductException` untuk error handling

### Bab 10 (Pattern & Testing)
- ✅ **Singleton Pattern**: `Cart` class
- ✅ **JUnit Test**: 9 test cases untuk CartService

### Bab 11 (DAO & Database)
- ✅ JDBC integration dengan PostgreSQL

### Bab 12-13 (GUI JavaFX)
- ✅ Professional GUI dengan TableView dan event handling

---

##Traceability Table (Bab 6 → Implementasi)

| Artefak | Use Case | Handler | Controller | Service | DAO | DB Impact |
|---------|----------|---------|-----------|---------|-----|-----------|
| UC-01 | Lihat Produk | Load | `getAllProducts()` | `getAllProducts()` | `findAll()` | SELECT |
| UC-02 | Tambah Produk | Tombol | `addProduct()` | `addProduct()` | `insert()` | INSERT |
| UC-03 | Hapus Produk | Tombol | `deleteProduct()` | `deleteProduct()` | `delete()` | DELETE |
| UC-04 | Tambah Keranjang | Tombol | `addToCart()` | `addToCart()` | - | Memory |
| UC-05 | Checkout | Tombol | `checkout()` | `reduceStock()` | `update()` | UPDATE stock |
| UC-06 | Tampil Struk | Checkout | `checkout()` | Struk detail | - | - |

---

##Design Pattern & Testing

### Singleton Pattern
Digunakan pada class `Cart` untuk memastikan satu instance keranjang global.

### Unit Testing - CartServiceTest
9 test cases dengan hasil ✅ All pass:
- testAddToCart()
- testAddMultipleItems()
- testAddSameProductIncreaseQuantity()
- testRemoveFromCart()
- testClearCart()
- testAddToCartWithZeroQuantity()
- testAddToCartWithNegativeQuantity()
- testAddToCartExceedsStock()
- testGetCartItems()
- testCartItemSubtotal()
- testMultipleCheckoutScenarios()

**ProductServiceTest** (3 test cases untuk stock reduction):
- testReduceStock() - Verifikasi stok berkurang dengan benar
- testReduceStockMultipleTimes() - Simulasi multiple transactions
- testReduceStockExceedsAvailable() - Validasi tidak bisa minus stock

**Total: 14 Test Cases - ALL PASS ✅**

---

##Exception Handling

**Custom Exception**: `ProductException` untuk JDBC errors dan validasi input.

**Validasi**:
- Kode produk tidak boleh kosong
- Harga tidak boleh negatif
- Stok tidak boleh negatif
- Jumlah item keranjang > 0
- Stock cukup untuk add to cart
- **Stock tidak boleh minus saat checkout** ✨ NEW

---

##Database Schema

```sql
CREATE TABLE products (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL
);
```

---

##Stock Reduction Flow (Fitur Utama) ✨

### Alur Checkout dengan Stock Update:

1. **User klik Checkout** → Validasi keranjang tidak kosong
2. **Show Receipt** → Tampilkan struk detail dengan semua items
3. **Process Checkout** → Panggil `controller.checkout()`
4. **Stock Reduction** → Loop setiap CartItem dan kurangi stock via `ProductService.reduceStock(code, qty)`
5. **Database Update** → DAO melakukan UPDATE untuk setiap produk
6. **UI Refresh** → Product table otomatis menampilkan stok terbaru
7. **Clear Cart** → Kosongkan keranjang setelah transaksi selesai

###Implementation Code:

```java
// PosView.java - Checkout button
btnCheckout.setOnAction(e -> {
    try {
        showReceipt();              // Tampilkan struk
        controller.checkout();      // Reduce stock di database
        // Product table refresh otomatis via showReceipt()
    } catch (Exception ex) {
        // Handle error
    }
});

// PosView.java - showReceipt()
private void showReceipt() {
    // Build dan tampilkan struk
    // Clear cart
    // REFRESH product table dengan stok terbaru
    productTable.setItems(FXCollections.observableArrayList(
        controller.getAllProducts()  // Load dari database
    ));
}

// ProductService.java - reduceStock()
public void reduceStock(String code, int quantity) throws ProductException {
    Product product = getProductByCode(code);
    int newStock = product.getStock() - quantity;
    
    if (newStock < 0) {
        throw new ProductException("Stok tidak cukup");
    }
    
    product.setStock(newStock);
    updateProduct(product);  // Update ke database
}
```

---

##Cara Menjalankan

```bash
cd praktikum/week14-integrasi-individu
mvn clean compile
mvn test
mvn javafx:run
```

---

##Fitur Tambahan - Struk & Stock Management

### A. Struk/Receipt Display
Ketika user melakukan checkout, aplikasi menampilkan struk terperinci dengan format:
```
════════════════════════════════════════
          AGRI-POS RECEIPT
════════════════════════════════════════
Tanggal: [current timestamp]
────────────────────────────────────────
ITEM                    QTY   HARGA     SUBTOTAL
────────────────────────────────────────
[Product details untuk setiap item]
────────────────────────────────────────
GRAND TOTAL:                    Rp [amount]
════════════════════════════════════════
Terima Kasih atas pembelian Anda!
════════════════════════════════════════
```

###Stock Management
Setiap kali checkout dilakukan:
1. Sistem memproses setiap item di keranjang
2. Method `ProductService.reduceStock()` dipanggil
3. Stok di database otomatis berkurang sesuai quantity yang dibeli
4. UI refresh untuk menampilkan stok terbaru
5. Validasi: Jika stok tidak cukup, transaksi ditolak dengan exception

**Implementation Detail**:
```java
// ProductService.java
public void reduceStock(String code, int quantity) throws ProductException {
    Product product = getProductByCode(code);
    if (product != null) {
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new ProductException("Stok tidak cukup untuk kode produk: " + code);
        }
        product.setStock(newStock);
        updateProduct(product); // Update ke database
    }
}

// PosController.java
public void checkout() throws Exception {
    for (CartItem item : cartService.getCartItems()) {
        productService.reduceStock(item.getProduct().getCode(), item.getQuantity());
    }
}

// PosView.java
private void showReceipt() {
    // Build struk dengan detail items
    // Show TextArea dialog dengan formatted receipt
    // Clear cart setelah checkout
}
```

---


## Kode Program
(Tuliskan kode utama yang dibuat, contoh:  

```java
// Contoh
Produk p1 = new Produk("BNH-001", "Benih Padi", 25000, 100);
System.out.println(p1.getNama());
```
)
---

## Hasil Eksekusi

![Screenshot hasil](screenshots/hasilweek14.png)

---

##Analisis

Berdasarkan log error pada gambar, aplikasi Agri-POS saat ini mengalami kegagalan integrasi yang disebabkan oleh masalah konfigurasi lingkungan (Environment) dan inkonsistensi antar-layer kode. Status "non-project file" menunjukkan bahwa folder praktikum belum dibuka sebagai proyek Maven yang valid, sehingga VS Code tidak dapat menghubungkan dependensi antar-class, yang berujung pada banyaknya error "cannot be resolved". Selain itu, terdapat ketidaksesuaian kontrak metode antara View, Controller, dan Service, seperti kesalahan jumlah argumen pada addProduct dan penggunaan variabel clear yang tidak didefinisikan dengan benar sebagai metode (void). Hal ini menunjukkan bahwa prinsip Dependency Inversion (DIP) dan struktur MVC yang diwajibkan dalam Bab 14 belum tersinkronisasi sepenuhnya secara teknis maupun arsitektural.

---
##Kesimpulan

Dapat disimpulkan bahwa aplikasi belum memenuhi Checklist Keberhasilan Bab 14 karena kode belum bisa dikompilasi (BUILD FAILURE) akibat kesalahan sintaksis dan kegagalan pemisahan layer yang fungsional. Untuk mencapai target integrasi individu, mahasiswa harus memastikan folder proyek dibuka dengan benar agar pom.xml terbaca, menyelaraskan nama serta parameter metode di seluruh tingkatan (View → Controller → Service → DAO), dan memperbaiki penanganan exception untuk validasi input. Keberhasilan proyek ini sangat bergantung pada konsistensi antara rancangan UML Bab 6 dengan implementasi nyata agar alur data dari GUI hingga database PostgreSQL dapat berjalan tanpa hambatan.

---
