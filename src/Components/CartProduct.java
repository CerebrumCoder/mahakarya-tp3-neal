package Components;
import java.util.UUID;

public class CartProduct {
    private UUID productId; // Identifier unik untuk produk
    private int amount; // Jumlah produk yang ingin dibeli

    public CartProduct(UUID productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getProductAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
