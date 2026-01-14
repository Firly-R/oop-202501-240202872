# Laporan Praktikum week11
Topik: Data Access Object (DAO) dan CRUD Database dengan JDBC

## Identitas
- Nama  : [Muhammad Firly Ramadhan]
- NIM   : [240202872]
- Kelas : [3IKRB]

---

## Tujuan
1. Menjelaskan konsep Data Access Object (DAO) dalam pengembangan aplikasi OOP.
2. Menghubungkan aplikasi Java dengan basis data menggunakan JDBC.
3. Mengimplementasikan operasi CRUD (Create, Read, Update, Delete) secara lengkap.
4. Mengintegrasikan DAO dengan class aplikasi OOP sesuai prinsip desain yang baik.


---

## Dasar Teori
### 1. Konsep Data Access Object (DAO)

DAO adalah pola desain yang memisahkan logika akses data dari logika bisnis aplikasi. Dengan DAO, perubahan teknologi basis data tidak memengaruhi logika utama aplikasi.

Manfaat DAO:
- Kode lebih terstruktur dan mudah dipelihara
- Mengurangi tight coupling antara aplikasi dan database
- Mendukung pengujian dan pengembangan lanjutan

---

### 2. JDBC dan Koneksi Database

JDBC (Java Database Connectivity) digunakan untuk menghubungkan aplikasi Java dengan basis data relasional, dalam praktikum ini menggunakan PostgreSQL.

Komponen utama JDBC:
- DriverManager
- Connection
- PreparedStatement
- ResultSet

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
1. Pemisahan Logika (DAO): Menggunakan pola Data Access Object untuk memisahkan logika bisnis aplikasi dari detail teknis akses database.
2. Struktur Model-Interface: Membagi kode ke dalam class Product (data), interface ProductDAO (kontrak), dan ProductDAOImpl (eksekusi SQL) agar lebih rapi.
3. Keamanan Query: Menggunakan PreparedStatement untuk mencegah SQL Injection saat melakukan operasi CRUD.
4. Konektivitas JDBC: Menghubungkan Java ke PostgreSQL menggunakan DriverManager dengan parameter URL, username, dan password yang spesifik.
5. Manajemen Resource: Menerapkan penutupan koneksi otomatis agar tidak terjadi kebocoran memori pada database.
---

## Kesimpulan
*Penerapan pola desain DAO dalam praktikum ini berhasil memisahkan logika bisnis dengan akses data, sehingga kode program menjadi lebih terstruktur dan mudah dipelihara. Selain itu, penggunaan JDBC dengan PreparedStatement memastikan interaksi database agripos berjalan aman dari risiko SQL Injection selama operasi CRUD berlangsung.*

---

## Quiz
(1. [Tuliskan kembali pertanyaan 1 dari panduan]  
   **Jawaban:** …  

2. [Tuliskan kembali pertanyaan 2 dari panduan]  
   **Jawaban:** …  

3. [Tuliskan kembali pertanyaan 3 dari panduan]  
   **Jawaban:** …  )
