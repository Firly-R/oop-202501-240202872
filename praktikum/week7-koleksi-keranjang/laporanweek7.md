# Laporan Praktikum Minggu 7
Topik: 

## Identitas
- Nama  : [Muhammad Firly Ramadhan]
- NIM   : [240202872]
- Kelas : [3IKRB]

---

## Tujuan
1. Menjelaskan konsep collection dalam Java (List, Map, Set).
2. Menggunakan ArrayList untuk menyimpan dan mengelola objek.
3. Mengimplementasikan Map atau Set sesuai kebutuhan pengelolaan data.
4. Melakukan operasi dasar pada collection: tambah, hapus, dan hitung total.
5. Menganalisis efisiensi penggunaan collection dalam konteks sistem Agri-POS.

---

## Dasar Teori
1. Collections Framework
Java Collections Framework menyediakan struktur data untuk mengelola objek secara dinamis dan efisien.

Struktur utama:
-List (implementasi: ArrayList) — Terurut, dapat menyimpan elemen duplikat.
-Map (implementasi: HashMap) — Menyimpan pasangan key–value, akses cepat berdasarkan key.
-Set (implementasi: HashSet) — Tidak menerima duplikat dan tidak mempertahankan urutan.

2. Studi Kasus: Keranjang Belanja Agri-POS
Keranjang belanja harus dapat:
-Menambahkan produk
-Menghapus produk
-Menampilkan isi keranjang
-Menghitung total nilai transaksi
-Menangani jumlah (quantity) menggunakan Map

---

## Langkah Praktikum

### 1. Membuat Class Product

```java
package com.upb.agripos;

public class Product {
    private final String code;
    private final String name;
    private final double price;

    public Product(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public String getCode() { return code; }
    public String getName() { return name; }
    public double getPrice() { return price; }
}
```

## 2. Implementasi keranjang dengan Arraylist

```java
package com.upb.agripos;

import java.util.ArrayList;
import java.text.DecimalFormat;

public class ShoppingCart {

    private final ArrayList<Product> items = new ArrayList<>();
    private final DecimalFormat df = new DecimalFormat("#");

    public void addProduct(Product p) {
        items.add(p);
    }

    public void removeProduct(Product p) {
        if (items.remove(p)) {
            System.out.println("\nProduk dihapus: " + p.getCode() + " " + p.getName());
        }
    }

    public double getTotal() {
        double total = 0;
        for (Product p : items) {
            total += p.getPrice();
        }
        return total;
    }

    public void printCart() {
        System.out.println("Isi Keranjang:");
        for (Product p : items) {
            System.out.println("- " + p.getCode() + " " + p.getName()
                    + " = " + df.format(p.getPrice()));
        }
        System.out.println("Total: " + df.format(getTotal()));
    }
}
```

## 3. Main Program

```java
package com.upb.agripos;

public class MainCart {
    public static void main(String[] args) {

        System.out.println("Hallo, saya Muhammad Firly Ramadhan-240202872 (Week7)");

        Product p1 = new Product("P01", "Beras", 50000);
        Product p2 = new Product("P02", "Pupuk", 30000);

        ShoppingCart cart = new ShoppingCart();
        cart.addProduct(p1);
        cart.addProduct(p2);
        cart.printCart();

        cart.removeProduct(p1);   
        System.out.println();
        cart.printCart();
    }
}
```

## 4. Implementasi Alternatif menggunakan Map

```java
package com.upb.agripos;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCartMap {

    private final Map<Product, Integer> items = new HashMap<>();

    // tambahProduk
    public void addProduct(Product p) {
        int qty = items.getOrDefault(p, 0) + 1;
        items.put(p, qty);
        System.out.println("tambahProduk: " + p.getName() + " (qty: " + qty + ")");
    }

    // hapusProduk
    public void removeProduct(Product p) {
        if (!items.containsKey(p)) {
            System.out.println("hapusProduk: " + p.getName() + " (tidak ada)");
            return;
        }

        int qty = items.get(p);
        if (qty > 1) {
            items.put(p, qty - 1);
            System.out.println("hapusProduk: " + p.getName()
                    + " (sisa: " + (qty - 1) + ")");
        } else {
            items.remove(p);
            System.out.println("hapusProduk: " + p.getName()
                    + " (dihapus total)");
        }
    }

    // hitungTotal
    public double getTotal() {
        double total = 0;
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        System.out.println("hitungTotal: " + total);
        return total;
    }

    public void printCart() {
        System.out.println("Isi Keranjang (Map):");
        for (Map.Entry<Product, Integer> e : items.entrySet()) {
            Product p = e.getKey();
            int qty = e.getValue();
            System.out.println("- " + p.getCode() + " " + p.getName()
                    + " x" + qty + " = " + (p.getPrice() * qty));
        }
        getTotal();
        System.out.println();
    }
}
```

---

## Hasil Eksekusi

### ArrayList
![Screenshot hasil](screenshots/ArrayList.png)

### Map
![Screenshot hasil](screenshots/Map.png)

---

## Analisis
(
1. Penjelasan Cara Kerja Program

Program keranjang belanja bekerja dengan menggunakan Java Collections Framework untuk menyimpan dan mengelola data produk. Data produk direpresentasikan dalam class Product, kemudian dikelola melalui class keranjang belanja menggunakan ArrayList atau Map. Produk ditambahkan ke dalam keranjang, ditampilkan ke console, dihitung total harganya, dan dapat dihapus sehingga isi keranjang serta total belanja akan diperbarui secara otomatis.

2. Perbandingan Pendekatan Minggu Ini dengan Minggu Sebelumnya

Pendekatan pada praktikum minggu ini lebih maju dibandingkan minggu sebelumnya karena memanfaatkan struktur data collection seperti ArrayList dan Map, sehingga pengelolaan data menjadi dinamis. Pada minggu sebelumnya, pengolahan data masih bersifat sederhana dan statis, sedangkan pada minggu ini data dapat ditambah dan dihapus dengan lebih fleksibel serta lebih mendekati implementasi aplikasi nyata seperti sistem keranjang belanja.

3. Kendala yang Dihadapi dan Cara Mengatasinya

Kendala utama yang dihadapi adalah tampilan nilai harga dan total belanja yang masih menampilkan angka desimal .0 karena menggunakan tipe data double. Kendala tersebut diatasi dengan melakukan format tampilan angka menggunakan DecimalFormat agar nilai harga dan total belanja ditampilkan dalam bentuk bilangan bulat sehingga output menjadi lebih rapi dan mudah dibaca.
)

---

## Kesimpulan
(Berdasarkan hasil praktikum yang telah dilakukan, dapat disimpulkan bahwa penggunaan Java Collections Framework, khususnya ArrayList dan Map, mempermudah pengelolaan data produk dalam sistem keranjang belanja. ArrayList cocok digunakan untuk keranjang belanja sederhana tanpa pengelolaan jumlah produk, sedangkan Map lebih efektif untuk mengelola produk beserta quantity-nya. Implementasi fitur tambah produk, hapus produk, dan hitung total belanja dapat berjalan dengan baik dan menghasilkan output yang sesuai dengan tujuan praktikum. Dengan demikian, pemanfaatan collection dalam Java dapat meningkatkan efisiensi dan fleksibilitas dalam pengembangan aplikasi berbasis objek.)

---

## Quiz
(1. [Jelaskan perbedaan mendasar antara List, Map, dan Set.]  
   **Jawaban:List menyimpan data berurutan dan boleh duplikat, Map menyimpan data dalam pasangan key–value, sedangkan Set menyimpan data unik tanpa duplikasi.** …  

2. [Mengapa ArrayList cocok digunakan untuk keranjang belanja sederhana?]  
   **Jawaban:ArrayList cocok untuk keranjang belanja sederhana karena mudah digunakan dan mendukung penambahan serta penghapusan data secara dinamis.** …  

3. [Bagaimana struktur Set mencegah duplikasi data?]  
   **Jawaban:Set mencegah duplikasi dengan hanya menyimpan satu data yang sama dan menolak data duplikat.** …  
4. [Kapan sebaiknya menggunakan Map dibandingkan List? Jelaskan dengan contoh.]  
   **Jawaban:Map digunakan ketika data membutuhkan key dan quantity, misalnya produk sebagai key dan jumlah sebagai value dalam keranjang belanja.** … )
