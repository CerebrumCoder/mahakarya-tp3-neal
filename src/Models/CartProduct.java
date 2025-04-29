package Models;
import java.util.UUID;

public class CartProduct {
    private UUID productId; // Identifier unik untuk produk
    private int amount; // Jumlah produk yang ingin dibeli

    public CartProduct(UUID productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public int getProductAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}