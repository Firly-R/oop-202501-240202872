package main.java.com.upb.agripos.model;

public class alatpertanian extends produk {
    private String bahan; 

    public alatpertanian(String kode, String nama, double harga, int stok, String bahan) {
        super(kode, nama, harga, stok);
        this.bahan = bahan;
    }

    @Override
    public String getInfo() {
        return "Alat   : " + super.getInfo() + ", Bahan: " + bahan;
    }
}
