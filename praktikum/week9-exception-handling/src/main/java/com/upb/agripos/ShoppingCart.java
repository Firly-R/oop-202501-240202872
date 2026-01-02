package main.java.com.upb.agripos;


import java.util.HashMap;
import java.util.Map;


public class ShoppingCart {
private Map<Product, Integer> items = new HashMap<>();


public void addProduct(Product product, int qty) throws InvalidQuantityException {
if (qty <= 0) {
throw new InvalidQuantityException("Jumlah harus lebih dari 0");
}
items.put(product, items.getOrDefault(product, 0) + qty);
}


public void removeProduct(Product product) throws ProductNotFoundException {
if (!items.containsKey(product)) {
throw new ProductNotFoundException("Produk tidak ditemukan di keranjang");
}
items.remove(product);
}


public void checkout() throws InsufficientStockException {
for (Map.Entry<Product, Integer> entry : items.entrySet()) {
if (entry.getKey().getStock() < entry.getValue()) {
throw new InsufficientStockException(
"Stok tidak cukup untuk " + entry.getKey().getName()
);
}
}
for (Map.Entry<Product, Integer> entry : items.entrySet()) {
entry.getKey().reduceStock(entry.getValue());
}
}
}