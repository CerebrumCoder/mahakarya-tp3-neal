package Models;

import java.util.UUID;

public class Product {
    private UUID productId; // Identifier unik, di-generate otomatis saat inisiasi

    @ProductValidation(message = "Product name cannot be empty")
    private String name; // Nama produk

    @ProductValidation(allowNegative = false, message = "Stock cannot be negative")
    private int stok; // Stok produk

    @ProductValidation(allowNegative = false, message = "Price must be positive")
    private long price; // Harga produk

    public Product(String name, int stok, long price) {
        this.productId = UUID.randomUUID(); // Generate ID unik
        this.name = name;
        this.stok = stok;
        this.price = price;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public String getProductName() {
        return this.name;
    }

    public int getProductStock() {
        return this.stok;
    }

    public long getProductPrice() {
        return this.price;
    }

    public void setProductStock(int stok) {
        this.stok = stok;
    }

    public void setProductPrice(long price) {
        this.price = price;
    }
}
