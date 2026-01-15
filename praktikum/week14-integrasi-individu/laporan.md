# Laporan Week 14 - Integrasi Individu (OOP + Database + GUI)
Topik: Integrasi End-to-End OOP, Database, dan GUI JavaFX

## Identitas
- Nama  : 240202872
- NIM   : 240202872
- Kelas : OOP 2025

---

## 1. Ringkasan Aplikasi

Aplikasi **Agri-POS Week 14** adalah sistem point-of-sale (POS) kasir sederhana untuk toko pertanian yang mengintegrasikan:

- **Manajemen Produk**: CRUD (Create, Read, Update, Delete) produk dari database PostgreSQL
- **Keranjang Belanja**: Sistem keranjang berbasis Collections dengan fitur add/remove item
- **Antarmuka GUI**: JavaFX dengan layout intuitif untuk kemudahan penggunaan
- **Validasi & Exception Handling**: Custom exception untuk error handling yang proper
- **Design Pattern**: Penerapan Singleton Pattern untuk Cart management
- **Unit Testing**: JUnit test untuk validasi logika CartService

---

## 2. Keterangan Integrasi Bab 1-13

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

## 3. Traceability Table (Bab 6 → Implementasi)

| Artefak | Use Case | Handler | Controller | Service | DAO | DB Impact |
|---------|----------|---------|-----------|---------|-----|-----------|
| UC-01 | Lihat Produk | Load | `getAllProducts()` | `getAllProducts()` | `findAll()` | SELECT |
| UC-02 | Tambah Produk | Tombol | `addProduct()` | `addProduct()` | `insert()` | INSERT |
| UC-03 | Hapus Produk | Tombol | `deleteProduct()` | `deleteProduct()` | `delete()` | DELETE |
| UC-04 | Tambah Keranjang | Tombol | `addToCart()` | `addToCart()` | - | Memory |
| UC-05 | Checkout | Tombol | `getCartTotal()` | `getCartTotal()` | - | Memory |

---

## 4. Design Pattern & Testing

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

---

## 5. Exception Handling

**Custom Exception**: `ProductException` untuk JDBC errors dan validasi input.

**Validasi**:
- Kode produk tidak boleh kosong
- Harga tidak boleh negatif
- Stok tidak boleh negatif
- Jumlah item keranjang > 0
- Stock cukup untuk add to cart

---

## 6. Database Schema

```sql
CREATE TABLE products (
    code VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL
);
```

---

## 7. Cara Menjalankan

```bash
cd praktikum/week14-integrasi-individu
mvn clean compile
mvn test
mvn javafx:run
```

---

## 8. Kendala & Solusi

### Kendala 1: Singleton Reset di Test
**Solusi**: Memanggil `clearCart()` di `setUp()` untuk reset state.

### Kendala 2: Nested Object Property di TableColumn
**Solusi**: Menggunakan lambda expression dengan `SimpleStringProperty`.

---

## 9. Checklist Keberhasilan

- [x] Aplikasi JavaFX berjalan
- [x] CRUD dengan JDBC & PostgreSQL
- [x] Keranjang menggunakan Collections
- [x] Custom exception `ProductException`
- [x] Singleton Pattern pada Cart
- [x] 9 JUnit test pass
- [x] MVC architecture (DIP)
- [x] Laporan & UML
- [x] Console output identitas

**Status**: ✅ COMPLETE

**Date**: January 15, 2026

## Tujuan
(Tuliskan tujuan praktikum minggu ini.  
Contoh: *Mahasiswa memahami konsep class dan object serta dapat membuat class Produk dengan enkapsulasi.*)

---

## Dasar Teori
(Tuliskan ringkasan teori singkat (3–5 poin) yang mendasari praktikum.  
Contoh:  
1. Class adalah blueprint dari objek.  
2. Object adalah instansiasi dari class.  
3. Enkapsulasi digunakan untuk menyembunyikan data.)

---

## Langkah Praktikum
(Tuliskan Langkah-langkah dalam prakrikum, contoh:
1. Langkah-langkah yang dilakukan (setup, coding, run).  
2. File/kode yang dibuat.  
3. Commit message yang digunakan.)

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
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/hasil.png)
)
---

## Analisis
(
- Jelaskan bagaimana kode berjalan.  
- Apa perbedaan pendekatan minggu ini dibanding minggu sebelumnya.  
- Kendala yang dihadapi dan cara mengatasinya.  
)
---

## Kesimpulan
(Tuliskan kesimpulan dari praktikum minggu ini.  
Contoh: *Dengan menggunakan class dan object, program menjadi lebih terstruktur dan mudah dikembangkan.*)

---

## Quiz
(1. [Tuliskan kembali pertanyaan 1 dari panduan]  
   **Jawaban:** …  

2. [Tuliskan kembali pertanyaan 2 dari panduan]  
   **Jawaban:** …  

3. [Tuliskan kembali pertanyaan 3 dari panduan]  
   **Jawaban:** …  )
