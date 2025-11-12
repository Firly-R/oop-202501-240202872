package main.java.com.upb.agripos.model;

public class benih extends produk {
    private String varietas;

    public benih(String kode, String nama, double harga, int stok, String varietas) {
        super(kode, nama, harga, stok);
        this.varietas = varietas;
    }

    @Override
    public String getInfo() {
        return "Benih  : " + super.getInfo() + ", Varietas: " + varietas;
    }
}
