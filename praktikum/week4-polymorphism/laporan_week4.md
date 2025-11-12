# Laporan Praktikum Minggu 4
Topik: Polymorphism

## Identitas
- Nama  : [Muhammad Firly Ramadhan]
- NIM   : [240202872]
- Kelas : [3IKRB]

---

## Tujuan
1. Menjelaskan konsep polymorphism dalam pemrograman berorientasi objek (OOP) menggunakan bahasa Java.
2. Menunjukkan kemampuan mahasiswa dalam membedakan method overloading dan method overriding.
3. Mengimplementasikan polymorphism melalui penerapan method getInfo() pada berbagai subclass (Benih, Pupuk, AlatPertanian, ObatHama) dalam sistem Agri-POS.
4. Menganalisis penerapan dynamic binding, yaitu bagaimana Java memanggil method sesuai dengan tipe objek aktual pada saat runtime.
5. Menghasilkan program yang menerapkan prinsip inheritance, overloading, overriding, dan dynamic binding untuk menampilkan informasi produk pertanian secara dinamis.
6. Menyusun laporan hasil praktikum yang mencakup penjelasan teori, implementasi program, hasil eksekusi, dan analisis.
---

## Dasar Teori
1. Polymorphism adalah konsep dalam OOP yang memungkinkan satu method memiliki banyak bentuk perilaku, tergantung pada objek yang memanggilnya.
2. Dalam Java, polymorphism terbagi menjadi dua jenis: overloading (compile-time) dan overriding (runtime). Overloading terjadi saat method memiliki nama sama dengan parameter berbeda, sedangkan overriding terjadi saat subclass mengganti implementasi method superclass.
3. Pada sistem Agri-POS, polymorphism diterapkan melalui method getInfo() pada kelas Produk yang dioverride oleh Benih, Pupuk, ObatHama, dan AlatPertanian, sehingga tiap objek menampilkan informasi sesuai jenis produknya.

---

## Langkah Praktikum
1. Membuat Kelas Superclass (Produk)
Buat kelas Produk yang memiliki atribut kode, nama, harga, dan stok.
Tambahkan method tambahStok() dengan dua versi (int dan double) untuk menunjukkan overloading, serta method getInfo() sebagai dasar untuk dioverride oleh subclass.

2. Membuat Kelas Subclass (Benih, Pupuk, AlatPertanian, ObatHama)
Setiap subclass mewarisi Produk dan menambahkan atribut khusus seperti varietas, jenis, atau bahan.
Lakukan method overriding pada getInfo() agar tiap kelas menampilkan informasi produk secara berbeda.

3. Menguji Polymorphism (Dynamic Binding)
Buat kelas utama MainPolymorphism yang berisi array Produk[] dengan elemen Benih, Pupuk, ObatHama, dan AlatPertanian.
Gunakan perulangan for untuk memanggil getInfo() dari tiap objek, lalu amati bagaimana Java menampilkan hasil sesuai jenis objek sebenarnya.

4. Menambahkan Identitas dan Menjalankan Program
Tambahkan pemanggilan CreditBy.print("<NIM>", "<Nama>") di akhir program.
Jalankan program dan pastikan hasil menampilkan informasi produk dengan format berbeda sesuai kelasnya.
Ambil screenshot hasil eksekusi untuk dokumentasi laporan.

---

## Kode Program
1. Benih.java(overriding)
```benih.java
package main.java.com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    @Override
    public String getInfo() {
        return "Benih  : " + super.getInfo() + ", Varietas: " + varietas;
    }
}
```
2. Produk.java(Overloading & Base Method)
```produk.java
package main.java.com.upb.agripos.model;
public class Produk {
    private String kode;
    private String nama;
    private double harga;
    private int stok;

    public Produk(String kode, String nama, double harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public void tambahStok(int jumlah) {
        this.stok += jumlah;
        System.out.println("Stok " + this.nama + " ditambah " + jumlah + " (int). Stok baru: " + this.stok);
    }

    public void tambahStok(double jumlah) {
        this.stok += (int) jumlah;
        System.out.println("Stok " + this.nama + " ditambah " + (int)jumlah + " (dari double). Stok baru: " + this.stok);
    }

    public String getInfo() {
        return "Produk: " + nama + " (Kode: " + kode + ")";
    }

    public int getStok() {
        return stok;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }
}
```
3. ObatHama.java(latihan mandiri overriding)
```ObatHama.java
package main.java.com.upb.agripos.model;

public class ObatHama extends Produk {
    private String targetHama; 

    public ObatHama(String kode, String nama, double harga, int stok, String targetHama) {
        super(kode, nama, harga, stok);
        this.targetHama = targetHama;
    }

    @Override
    public String getInfo() {
        return "Obat   : " + super.getInfo() + ", Target: " + targetHama;
    }
}
```
4. Pupuk.java(latihan maindiri overriding)
```pupuk.java
package main.java.com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis; 

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    @Override
    public String getInfo() {
        return "Pupuk  : " + super.getInfo() + ", Jenis: " + jenis;
    }
}
```
5. AlatPertanian.java(latihan mandiri overriding)
```alatpertanian.java
package main.java.com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String bahan; 

    public AlatPertanian(String kode, String nama, double harga, int stok, String bahan) {
        super(kode, nama, harga, stok);
        this.bahan = bahan;
    }

    @Override
    public String getInfo() {
        return "Alat   : " + super.getInfo() + ", Bahan: " + bahan;
    }
}
```
6. CreditBy.java
```creditby.java
package main.java.com.upb.agripos.util;

public class CreditBy {
   
     public static void print(String nim, String nama) {
        System.out.println("\n=== Credit By ===");
        System.out.println("Nama: " + nama);
        System.out.println("NIM: " + nim);
        System.out.println("=================\n");
    }
}
```
7. Mainpolymorphism
```mainpolymorphism.java
package main.java.com.upb.agripos;

// Import semua model yang diperlukan
import main.java.com.upb.agripos.model.Produk;
import main.java.com.upb.agripos.model.Benih;
import main.java.com.upb.agripos.model.Pupuk;
import main.java.com.upb.agripos.model.AlatPertanian;
import main.java.com.upb.agripos.model.ObatHama; // Untuk latihan mandiri
import main.java.com.upb.agripos.util.CreditBy;

public class MainPolymorphism {
    public static void main(String[] args) {
        
        // 1. Implementasi Dynamic Binding (Tugas 3)
        // Array dibuat dari tipe Superclass (Produk)
        Produk[] daftarProduk = {
            // Namun diisi oleh objek-objek Subclass
            new Benih("BNH-001", "Benih Padi Mantap", 25000, 100, "IR64"),
            new Pupuk("PPK-101", "Pupuk Urea Subsidi", 350000, 40, "Urea"),
            new AlatPertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new ObatHama("OBH-200", "Insektisida Regent", 75000, 50, "Wereng") // Latihan Mandiri
        };

        System.out.println("===  Daftar Info Produk (Demonstrasi Dynamic Binding) ===");
        
        // Saat p.getInfo() dipanggil, Java secara dinamis (runtime)
        // memilih method getInfo() milik objek aslinya (Benih, Pupuk, dst.)
        for (Produk p : daftarProduk) {
            System.out.println(p.getInfo()); 
        }

        // 2. Demonstrasi Overloading (Tugas 1)
        System.out.println("\n=== Demonstrasi Overloading tambahStok ===");
        Produk produkTes = daftarProduk[0]; // Mengambil "Benih Padi IR64"
        
        System.out.println("Stok awal: " + produkTes.getStok()); 
        
        // Memanggil method tambahStok(int jumlah)
        produkTes.tambahStok(10); 
        
        // Memanggil method tambahStok(double jumlah)
        produkTes.tambahStok(5.5); 

        
        // 3. Panggil CreditBy
        System.out.println("\n----------------------------------------");
        // !! GANTI DENGAN NIM DAN NAMA ANDA !!
        CreditBy.print("240202872", "Muhammad Firly Ramadhan");
    }
}
```
---

## Hasil Eksekusi
(Sertakan screenshot hasil eksekusi program.  
![Screenshot hasil](screenshots/MainPolymorphism.png)
)
---

## Analisis
Pada praktikum ini, konsep polymorphism berhasil diterapkan melalui penggunaan method getInfo() yang dioverride pada setiap subclass (Benih, Pupuk, ObatHama, dan AlatPertanian). Saat objek-objek tersebut disimpan dalam array bertipe Produk[], pemanggilan getInfo() menghasilkan output yang berbeda sesuai tipe objek sebenarnya. Hal ini menunjukkan terjadinya dynamic binding, di mana Java menentukan method yang dipanggil saat runtime, bukan saat compile time.
Selain itu, penerapan method overloading pada tambahStok() menunjukkan bahwa satu nama method dapat digunakan dengan parameter berbeda untuk tujuan yang sama. Implementasi ini memperlihatkan fleksibilitas dan efisiensi dalam penulisan kode.
Secara keseluruhan, hasil eksekusi program membuktikan bahwa polymorphism membuat program lebih modular, mudah dikembangkan, dan mendukung prinsip reusabilitas kode dalam OOP.
---

## Checklist Keberhasilan
- ✅Overloading tambahStok berhasil.
- ✅Overriding getInfo pada subclass berjalan.
- ✅Dynamic binding berjalan melalui array produk.
- ✅Output menampilkan identitas mahasiswa.
- ✅Screenshot & laporan disertakan.

## Kesimpulan
1. Polymorphism memungkinkan satu method memiliki berbagai bentuk perilaku sesuai jenis objek yang memanggilnya.
2. Melalui overloading dan overriding, program dapat dibuat lebih fleksibel, efisien, dan mudah dikembangkan.
3. Hasil praktikum menunjukkan bahwa dynamic binding pada Java bekerja dengan baik, di mana method getInfo() dijalankan sesuai tipe objek aktual (Benih, Pupuk, AlatPertanian).

---

## Quiz
1. Apa perbedaan overloading dan overriding?  
   **Jawaban: Overloading terjadi saat satu nama method digunakan dengan parameter berbeda (compile time), sedangkan overriding terjadi ketika subclass mengganti method superclass (runtime).**

2. Bagaimana Java menentukan method mana yang dipanggil dalam dynamic binding? 
   **Jawaban: Java menentukan method yang dipanggil berdasarkan tipe objek sebenarnya saat program dijalankan (dynamic binding).** 

3. Berikan contoh kasus polymorphism dalam sistem POS selain produk pertanian. 
   **Jawaban: ContohnyaPada sistem toko elektronik (E-POS), terdapat superclass Barang dengan method getInfo(). Subclass-nya seperti Televisi, Laptop, dan Smartphone masing-masing meng-override getInfo() untuk menampilkan spesifikasi sesuai jenis produk. Dengan polymorphism, sistem dapat menampilkan informasi berbagai produk elektronik menggunakan satu method yang sama (getInfo()).**
