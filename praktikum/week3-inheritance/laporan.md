# Laporan Praktikum Minggu 1 (sesuaikan minggu ke berapa?)
Topik: [Inheritance]

## Identitas
- Nama  : [Muhammad Firly Ramadhan]
- NIM   : [240202872]
- Kelas : [3IKRB]

---

## Tujuan
1. Mahasiswa mampu menjelaskan konsep inheritance (pewarisan class) dalam OOP.
2. Mahasiswa mampu membuat superclass dan subclass untuk produk pertanian.
3. Mahasiswa mampu mendemonstrasikan hierarki class melalui contoh kode.
4. Mahasiswa mampu menggunakan super untuk memanggil konstruktor dan method parent class.
5. Mahasiswa mampu membuat laporan praktikum yang menjelaskan perbedaan penggunaan inheritance dibanding class tunggal.
---

## Dasar Teori
Inheritance adalah mekanisme dalam OOP yang memungkinkan suatu class mewarisi atribut dan method dari class lain.

Superclass: class induk yang mendefinisikan atribut umum.
Subclass: class turunan yang mewarisi atribut/method superclass, dan dapat menambahkan atribut/method baru.
super digunakan untuk memanggil konstruktor atau method superclass.
Dalam konteks Agri-POS, kita dapat membuat class Produk sebagai superclass, kemudian Benih, Pupuk, dan AlatPertanian sebagai subclass. Hal ini membuat kode lebih reusable dan terstruktur.

---

## Langkah Praktikum
1. Membuat Superclass Produk
Gunakan class Produk dari Bab 2 sebagai superclass.

2. Membuat Subclass
Benih.java → atribut tambahan: varietas.
Pupuk.java → atribut tambahan: jenis pupuk (Urea, NPK, dll).
AlatPertanian.java → atribut tambahan: material (baja, kayu, plastik).

3. Membuat Main Class
Instansiasi minimal satu objek dari tiap subclass.
Tampilkan data produk dengan memanfaatkan inheritance.

4. Menambahkan CreditBy
Panggil class CreditBy untuk menampilkan identitas mahasiswa.

5. Commit dan Push
Commit dengan pesan: week3-inheritance.

---

## Kode Program

1. AlatPertanian.java
```AlatPertanian.java
package com.upb.agripos.model;

public class AlatPertanian extends Produk {
    private String material;

    public AlatPertanian(String kode, String nama, double harga, int stok, String material) {
        // Memanggil konstruktor superclass (Produk)
        super(kode, nama, harga, stok);
        this.material = material;
    }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    // Override method deskripsi untuk menambahkan info material
    @Override
    public String deskripsi() {
        return super.deskripsi() + ", Material: " + this.material;
    }
}

```
2. Benih.java
```Benih.java
package com.upb.agripos.model;

public class Benih extends Produk {
    private String varietas;

    public Benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    public String getVarietas() { return varietas; }
    public void setVarietas(String varietas) { this.varietas = varietas; }

    @Override
    public String deskripsi() {
        return super.deskripsi() + ", Varietas: " + this.varietas;
    }
}
```
3. Produk.java
```Produk.java
package com.upb.agripos.model;
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

    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public double getHarga() { return harga; }
    public int getStok() { return stok; }

    public void setKode(String kode) { this.kode = kode; }
    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(double harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }

    
    public String deskripsi() {
        return "Kode: " + this.kode +
               ", Nama: " + this.nama +
               ", Harga: Rp" + String.format("%,.2f", this.harga) +
               ", Stok: " + this.stok;
    }
}
```
4. Pupuk.java
```package com.upb.agripos.model;

public class Pupuk extends Produk {
    private String jenis;

    public Pupuk(String kode, String nama, double harga, int stok, String jenis) {
        super(kode, nama, harga, stok);
        this.jenis = jenis;
    }

    public String getJenis() { return jenis; }
    public void setJenis(String jenis) { this.jenis = jenis; }

    @Override
    public String deskripsi() {
        return super.deskripsi() + ", Jenis: " + this.jenis;
    }
}
```
5. CreditBy.java
```package com.upb.agripos.Util;

public class CreditBy {
   
     public static void print(String nim, String nama) {
        System.out.println("\n=== Credit By ===");
        System.out.println("Nama: " + nama);
        System.out.println("NIM: " + nim);
        System.out.println("=================\n");
    }
}
```
6. MainInheritance.java
```package com.upb.agripos;

import com.upb.agripos.model.AlatPertanian;
import com.upb.agripos.model.Benih;
import com.upb.agripos.model.Pupuk;

public class MainInheritance {
    public static void main(String[] args) {

        Benih benihPadi = new Benih("BNH-001", "Benih Padi Mantap", 17000, 100, "IR64");
        Pupuk pupukUrea = new Pupuk("PPK-101", "Pupuk Urea Subsidi", 300000, 15, "Urea");
        AlatPertanian cangkul = new AlatPertanian("ALT-501", "Cangkul Baja", 80000, 35, "Baja");

        System.out.println("===== INFORMASI PRODUK AGRI-POS =====");
        
        System.out.println("\n--- Data Dasar Produk ---");
        System.out.println("Benih: " + benihPadi.getNama() + " | Varietas: " + benihPadi.getVarietas());
        System.out.println("Pupuk: " + pupukUrea.getNama() + " | Jenis: " + pupukUrea.getJenis());
        System.out.println("Alat: " + cangkul.getNama() + " | Material: " + cangkul.getMaterial());

        System.out.println("\n---Deskripsi Lengkap Produk---");
        System.out.println("1. Deskripsi Benih : " + benihPadi.deskripsi());
        System.out.println("2. Deskripsi Pupuk : " + pupukUrea.deskripsi());
        System.out.println("3. Deskripsi Alat  : " + cangkul.deskripsi());


        com.upb.agripos.Util.CreditBy.print("240202872", "Muhammad Firly Ramadhan");
    }
    
}
```

## Hasil Eksekusi
- ✅ Superclass `Produk` digunakan kembali tanpa duplikasi kode.  
- ✅ Subclass `Benih`, `Pupuk`, dan `AlatPertanian` berhasil dibuat dengan atribut tambahan.  
- ✅ Program berjalan menampilkan objek dari setiap subclass.  
- ✅ CreditBy ditampilkan dengan benar.  
- ✅ Commit sesuai instruksi.  
- ✅ Laporan singkat lengkap dengan analisis.  
---

## Analisis
1. Kelas Produk menjadi superclass yang menyimpan atribut umum produk seperti kode, nama, harga, dan stok.
2. Kelas Benih, Pupuk, dan AlatPertanian merupakan subclass yang menambahkan atribut khusus masing-masing dan meng-override method deskripsi().
3. Program menerapkan konsep OOP yaitu enkapsulasi, pewarisan, dan polimorfisme dengan baik.
4. Kelas MainInheritance digunakan untuk membuat objek dari tiap subclass dan menampilkan data serta deskripsinya.
5. Kelas CreditBy menampilkan identitas pembuat program melalui method statis print().
---

## Kesimpulan
Program AgriPOS berhasil menerapkan konsep dasar Object-Oriented Programming (OOP) dengan baik melalui penggunaan inheritance, encapsulation, dan polymorphism, di mana setiap kelas turunan memiliki karakteristik serta perilaku sendiri namun tetap mewarisi atribut dari kelas induk Produk, sehingga kode menjadi lebih terstruktur, mudah dikembangkan, dan efisien.
---

## Quiz
1. Apa keuntungan menggunakan inheritance dibanding membuat class terpisah tanpa hubungan?  
   **Jawaban:** Keuntungan menggunakan inheritance adalah kode menjadi lebih efisien karena subclass dapat mewarisi atribut dan method dari superclass tanpa harus menulis ulang, sehingga memudahkan perawatan dan pengembangan program.

2. Bagaimana cara subclass memanggil konstruktor superclass?  
   **Jawaban:** ubclass memanggil konstruktor superclass dengan menggunakan keyword super() di dalam konstruktor subclass untuk menginisialisasi atribut yang dimiliki kelas induk.

3. Berikan contoh kasus di POS pertanian selain Benih, Pupuk, dan Alat Pertanian yang bisa dijadikan subclass.  
   **Jawaban:** Contoh kasus lain di POS pertanian adalah ObatTanaman, yang bisa menjadi subclass dari Produk dengan atribut tambahan seperti kandungan atau dosis penggunaan.



