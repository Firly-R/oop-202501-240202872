package com.upb.agripos;

import java.sql.Connection;
import java.sql.DriverManager;
import com.upb.agripos.dao.ProductDAO;
import com.upb.agripos.dao.ProductDAOImpl;
import com.upb.agripos.model.Product;
import java.util.List;

public class MainDAOTest {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/agripos";
        String user = "postgres";
        String password = "admin321"; // Ganti jika password PostgreSQL Anda berbeda!

        try {
            // Memastikan driver PostgreSQL dimuat
            Class.forName("org.postgresql.Driver");
            
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("=== Koneksi Berhasil ===");

                ProductDAO dao = new ProductDAOImpl(conn);

                // 1. CREATE (Insert)
                System.out.println("\n[1] Menambah Produk P01...");
                dao.insert(new Product("P01", "Pupuk Organik", 25000, 10));

                // 2. UPDATE
                System.out.println("[2] Mengubah Produk P01...");
                dao.update(new Product("P01", "Pupuk Organik Premium", 30000, 8));

                // 3. READ (Find By Code)
                Product p = dao.findByCode("P01");
                if (p != null) {
                    System.out.println("[3] Data Ditemukan: " + p.getName() + " | Harga: " + p.getPrice());
                }

                // 4. READ ALL
                System.out.println("[4] Daftar Semua Produk:");
                List<Product> allProducts = dao.findAll();
                for (Product item : allProducts) {
                    System.out.println("    - " + item.getCode() + ": " + item.getName());
                }

                // 5. DELETE
                // Hapus baris di bawah jika ingin data tetap ada di database
                System.out.println("[5] Menghapus Produk P01...");
                dao.delete("P01");
                
                System.out.println("\n=== Tes CRUD Selesai ===");

            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC tidak ditemukan! Tambahkan .jar ke Referenced Libraries.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}