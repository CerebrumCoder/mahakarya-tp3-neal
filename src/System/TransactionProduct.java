package System;
import java.util.UUID;

public class TransactionProduct {
    private UUID productId; // ID unik dari produk yang dibeli
    private int amount; // Jumlah produk yang dibeli

    public TransactionProduct(UUID productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    public UUID getProductId() {
        return productId;
    }

    public int getProductAmount() {
        return amount;
    }
}
