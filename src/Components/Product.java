package Components;
import java.util.UUID;

public class Product {
    private UUID productId; // Identifier unik, di-generate otomatis saat inisiasi
    private String name; // Nama produk
    private int stok; // Stok produk
    private long price; // Harga produk

    public Product(String name, int stok, long price) {
        this.productId = UUID.randomUUID(); // Generate ID unik
        this.name = name;
        this.stok = stok;
        this.price = price;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getProductName() {
        return name;
    }

    public int getProductStock() {
        return stok;
    }

    public long getProductPrice() {
        return price;
    }

    public void setProductStock(int stok) {
        this.stok = stok;
    }

    public void setProductPrice(long price) {
        this.price = price;
    }
}
