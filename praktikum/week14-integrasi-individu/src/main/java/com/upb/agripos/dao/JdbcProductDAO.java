package com.upb.agripos.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.upb.agripos.model.Product;

/**
 * JdbcProductDAO implementation
 * Implementasi konkret dari ProductDAO menggunakan JDBC
 * Menangani semua operasi database untuk Product
 */
public class JdbcProductDAO implements ProductDAO {
    private final Connection connection;

    public JdbcProductDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Product p) throws ProductException {
        if (p.getCode() == null || p.getCode().trim().isEmpty()) {
            throw new ProductException("Kode produk tidak boleh kosong");
        }
        if (p.getPrice() < 0) {
            throw new ProductException("Harga tidak boleh negatif");
        }
        if (p.getStock() < 0) {
            throw new ProductException("Stok tidak boleh negatif");
        }

        String sql = "INSERT INTO products(code, name, price, stock) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getCode());
            ps.setString(2, p.getName());
            ps.setDouble(3, p.getPrice());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ProductException("Gagal menambah produk: " + e.getMessage(), e);
        }
    }

    @Override
    public Product findByCode(String code) throws ProductException {
        String sql = "SELECT * FROM products WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                        rs.getString("code"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                    );
                }
            }
        } catch (SQLException e) {
            throw new ProductException("Gagal mencari produk: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Product> findAll() throws ProductException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getString("code"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            throw new ProductException("Gagal mengambil daftar produk: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void update(Product p) throws ProductException {
        if (p.getCode() == null || p.getCode().trim().isEmpty()) {
            throw new ProductException("Kode produk tidak boleh kosong");
        }
        if (p.getPrice() < 0) {
            throw new ProductException("Harga tidak boleh negatif");
        }
        if (p.getStock() < 0) {
            throw new ProductException("Stok tidak boleh negatif");
        }

        String sql = "UPDATE products SET name=?, price=?, stock=? WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setDouble(2, p.getPrice());
            ps.setInt(3, p.getStock());
            ps.setString(4, p.getCode());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new ProductException("Gagal memperbarui produk: " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(String code) throws ProductException {
        String sql = "DELETE FROM products WHERE code=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, code);
            int result = ps.executeUpdate();
            if (result == 0) {
                throw new ProductException("Produk dengan kode " + code + " tidak ditemukan");
            }
        } catch (SQLException e) {
            throw new ProductException("Gagal menghapus produk: " + e.getMessage(), e);
        }
    }
}
