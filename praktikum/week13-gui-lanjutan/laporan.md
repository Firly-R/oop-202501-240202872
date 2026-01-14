# Laporan Praktikum week13
Topik:GUI Lanjutan JavaFX (TableView dan Lambda Expression)

## Identitas
- Nama  : [Muhammad Firly Ramadhan]
- NIM   : [240202872]
- Kelas : [3IKRB]

---

## Tujuan
1. Menampilkan data menggunakan TableView JavaFX.
2. Mengintegrasikan koleksi objek dengan GUI.
3. Menggunakan lambda expression untuk event handling.
4. Menghubungkan GUI dengan DAO secara penuh.
5. Membangun antarmuka GUI Agri-POS yang lebih interaktif.
---

## Dasar Teori
1. TableView Control: TableView adalah komponen JavaFX yang berfungsi menampilkan data secara terstruktur dalam format baris dan kolom yang lebih profesional dibanding ListView.

2. TableColumn & PropertyValueFactory: Kolom tabel didefinisikan melalui TableColumn dan dihubungkan secara otomatis ke variabel di class Model menggunakan PropertyValueFactory.

3. Lambda Expression: Lambda Expression merupakan fitur Java untuk menulis blok kode penanganan event (seperti klik tombol) secara lebih ringkas dan efisien tanpa perlu mendeklarasikan class anonim yang panjang.

4. Arsitektur MVC Terintegrasi: Pengembangan ini menerapkan pola Model-View-Controller untuk memisahkan logika tampilan tabel, pemrosesan data, dan akses database.

5. Traceability: Prinsip traceability digunakan untuk memastikan setiap elemen antarmuka (View) memiliki keterkaitan yang jelas dengan logika bisnis di Service dan eksekusi Query di DAO.

---

## Langkah Praktikum
1. Persiapan Struktur Folder: Membuat folder baru week13-gui-lanjutan di dalam direktori praktikum dan menyalin seluruh file sumber (src) dari Week 12 sebagai dasar pengembangan.

2. Konfigurasi Environment: Mendaftarkan folder src/main/java sebagai Source Path di VS Code dan menambahkan library JavaFX serta driver PostgreSQL ke dalam Referenced Libraries untuk mengatasi error "NoClassDefFoundError".

3. Modifikasi View (TableView): Mengubah komponen ListView pada ProductFormView.java menjadi TableView serta mendefinisikan TableColumn untuk Kode, Nama, Harga, dan Stok menggunakan PropertyValueFactory.

4. Implementasi Fitur Hapus: Menambahkan tombol "Hapus Terpilih" dan menerapkan Lambda Expression pada setOnAction untuk menangkap baris data yang dipilih dan mengirimkan kodenya ke Controller.

5. Pembaruan Controller dan Service: Menambahkan method deleteProduct(String code) pada ProductController dan ProductService untuk menghubungkan antarmuka pengguna dengan lapisan logika bisnis.

6. Eksekusi Query DAO: Mengimplementasikan method delete pada ProductDAOImpl menggunakan PreparedStatement dengan perintah SQL DELETE FROM produk WHERE kode = ? untuk penghapusan permanen di database PostgreSQL.

7. Penanganan Exception: Menambahkan blok try-catch di tingkat Controller untuk menangani Unhandled exception type Exception guna memastikan aplikasi tidak berhenti secara tiba-tiba jika terjadi kesalahan database.

---

## Kode Program
##

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
