# Laporan Praktikum Minggu 9
Topik: Exception Handling, Custom Exception, dan Penerapan Design Pattern

## Identitas
- Nama  : [Muhammad Firly Ramadhan]
- NIM   : [240202872]
- Kelas : [3IKRB]

---

## Tujuan
1. Menjelaskan perbedaan antara error dan exception.
2. Mengimplementasikan try–catch–finally dengan tepat.
3. Membuat custom exception sesuai kebutuhan program.
4. Mengintegrasikan exception handling ke dalam aplikasi sederhana (kasus keranjang belanja).
(Opsional) Menerapkan design pattern sederhana (Singleton/MVC) dan unit testing dasar.
---

## Dasar Teori
### 1. Error vs Exception

- Error → kondisi fatal, tidak dapat ditangani (contoh: OutOfMemoryError).
- Exception → kondisi tidak normal yang dapat ditangani oleh program.

### 2. Struktur try–catch–finally

```java
try {
} catch (Exception e) {
} finally {
}
```

### 3. Membuat Custom Exception

```java
package com.upb.agripos;

public class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String message) {
        super(message);
    }
}
```

---

## Langkah Praktikum

### 1. Custom Exception

```java
package main.java.com.upb.agripos;


public class InvalidQuantityException extends Exception {
public InvalidQuantityException(String message) {
super(message);
}
}
```

```java
package main.java.com.upb.agripos;


public class ProductNotFoundException extends Exception {
public ProductNotFoundException(String message) {
super(message);
}
}
```

```java
package main.java.com.upb.agripos;


public class InsufficientStockException extends Exception {
public InsufficientStockException(String message) {
super(message);
}
}
```
### 2. Product

```java
package main.java.com.upb.agripos;


public class Product {
private String name;
private int stock;


public Product(String code, String name, double price, int stock) {
this.name = name;
this.stock = stock;
}


public String getName() { return name; }
public int getStock() { return stock; }


public void reduceStock(int qty) {
this.stock -= qty;
}
}
```
### 3. ShoppingCart dengan Exception Handling

```java
package main.java.com.upb.agripos;


import java.util.HashMap;
import java.util.Map;


public class ShoppingCart {
private Map<Product, Integer> items = new HashMap<>();


public void addProduct(Product product, int qty) throws InvalidQuantityException {
if (qty <= 0) {
throw new InvalidQuantityException("Jumlah harus lebih dari 0");
}
items.put(product, items.getOrDefault(product, 0) + qty);
}


public void removeProduct(Product product) throws ProductNotFoundException {
if (!items.containsKey(product)) {
throw new ProductNotFoundException("Produk tidak ditemukan di keranjang");
}
items.remove(product);
}


public void checkout() throws InsufficientStockException {
for (Map.Entry<Product, Integer> entry : items.entrySet()) {
if (entry.getKey().getStock() < entry.getValue()) {
throw new InsufficientStockException(
"Stok tidak cukup untuk " + entry.getKey().getName()
);
}
}
for (Map.Entry<Product, Integer> entry : items.entrySet()) {
entry.getKey().reduceStock(entry.getValue());
}
}
}
```
### 4. MainProgram

```java
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
```
---

## Design Pattern Sederhana
```java
package main.java.com.upb.agripos;

public class ProductService {
    private static ProductService instance;

    private ProductService() {}

    public static ProductService getInstance() {
        if (instance == null) {
            instance = new ProductService();
        }
        return instance;
    }

    public void showServiceInfo() {
        System.out.println("ProductService singleton aktif");
    }
}
```
---

## Hasil Eksekusi  
(![Screenshot hasil](screenshots/hasil.png))
---

## Analisis
Penerapan exception handling dengan custom exception pada sistem POS memungkinkan program menangani kesalahan bisnis secara terkontrol, meningkatkan kejelasan pesan kesalahan, serta menjaga stabilitas aplikasi. Dengan pemisahan peran melalui konsep MVC dan penggunaan Singleton pada service, struktur program menjadi lebih terorganisir, mudah dipelihara, dan sesuai dengan praktik pemrograman berorientasi objek.
---

## Kesimpulan
Exception handling dengan custom exception pada aplikasi POS membantu menangani kesalahan bisnis secara tepat, membuat program lebih stabil, terstruktur, dan mudah dikembangkan sesuai prinsip pemrograman berorientasi objek.
---

## Quiz
1. Jelaskan perbedaan error dan exception.  
   **Jawaban:Error adalah kesalahan fatal yang tidak bisa ditangani program, sedangkan exception adalah kesalahan yang masih bisa ditangani menggunakan try–catch.**  

2. Apa fungsi finally dalam blok try–catch–finally?  
   **Jawaban:Finally digunakan untuk mengeksekusi kode yang selalu dijalankan, baik terjadi exception maupun tidak.**  

3. Mengapa custom exception diperlukan?  
   **Jawaban:Custom exception digunakan agar kesalahan dapat ditangani secara spesifik sesuai kebutuhan bisnis dan pesan error menjadi lebih jelas.** 

3. Berikan contoh kasus bisnis dalam POS yang membutuhkan custom exception.  
   **Jawaban:Contohnya adalah jumlah pembelian tidak valid, produk tidak ada di keranjang, dan stok tidak mencukupi saat checkout.** 
