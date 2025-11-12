package main.java.com.upb.agripos;

// Import semua model yang diperlukan
import main.java.com.upb.agripos.model.produk;
import main.java.com.upb.agripos.model.benih;
import main.java.com.upb.agripos.model.pupuk;
import main.java.com.upb.agripos.model.alatpertanian;
import main.java.com.upb.agripos.model.ObatHama; // Untuk latihan mandiri
import main.java.com.upb.agripos.util.creditby;

public class mainpolymorphism {
    public static void main(String[] args) {
        
        // 1. Implementasi Dynamic Binding (Tugas 3)
        // Array dibuat dari tipe Superclass (Produk)
        produk[] daftarProduk = {
            // Namun diisi oleh objek-objek Subclass
            new benih("BNH-001", "Benih Padi Mantap", 25000, 100, "IR64"),
            new pupuk("PPK-101", "Pupuk Urea Subsidi", 350000, 40, "Urea"),
            new alatpertanian("ALT-501", "Cangkul Baja", 90000, 15, "Baja"),
            new ObatHama("OBH-200", "Insektisida Regent", 75000, 50, "Wereng") // Latihan Mandiri
        };

        System.out.println("===  Daftar Info Produk (Demonstrasi Dynamic Binding) ===");
        
        // Saat p.getInfo() dipanggil, Java secara dinamis (runtime)
        // memilih method getInfo() milik objek aslinya (Benih, Pupuk, dst.)
        for (produk p : daftarProduk) {
            System.out.println(p.getInfo()); 
        }

        // 2. Demonstrasi Overloading (Tugas 1)
        System.out.println("\n=== Demonstrasi Overloading tambahStok ===");
        produk produkTes = daftarProduk[0]; // Mengambil "Benih Padi IR64"
        
        System.out.println("Stok awal: " + produkTes.getStok()); 
        
        // Memanggil method tambahStok(int jumlah)
        produkTes.tambahStok(10); 
        
        // Memanggil method tambahStok(double jumlah)
        produkTes.tambahStok(5.5); 

        
        // 3. Panggil CreditBy
        System.out.println("\n----------------------------------------");
        // !! GANTI DENGAN NIM DAN NAMA ANDA !!
        creditby.print("240202872", "Muhammad Firly Ramadhan");
    }
}