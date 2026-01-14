package com.upb.agripos;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.upb.agripos.model.Product;
import com.upb.agripos.config.DatabaseConnection;

public class ProductTest {
    @Test
    void test1() { // Cek Nama
        Product p = new Product("P01", "Pupuk");
        assertEquals("Pupuk", p.getName());
    }

    @Test
    void test2() { // Cek Kode
        Product p = new Product("P01", "Pupuk");
        assertEquals("P01", p.getCode());
    }

    @Test
    void test3() { // Cek Singleton
        assertSame(DatabaseConnection.getInstance(), DatabaseConnection.getInstance());
    }

    @Test
    void test4() { // Cek Object Not Null
        assertNotNull(new Product("P01", "Pupuk"));
    }

    @Test
    void test5() { // Cek Koneksi Database
        assertNotNull(DatabaseConnection.getInstance());
    }
}