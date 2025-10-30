package com.upb.agripos;

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