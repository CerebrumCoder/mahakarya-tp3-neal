package Models;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {
    private List<CartProduct> keranjang; // Menyimpan seluruh CartProduct yang sedang dimiliki User

    public Cart() {
        this.keranjang = new ArrayList<>();
    }

    // Method untuk menambahkan isi cart
    public String addToCart(UUID productId, int quantity) {
        for (CartProduct product : keranjang) {
            if (product.getProductId().equals(productId)) {
                product.setAmount(product.getProductAmount() + quantity);
                return "Produk berhasil ditambahkan ke keranjang.";
            }
        }
        keranjang.add(new CartProduct(productId, quantity));
        return "Produk baru berhasil ditambahkan ke keranjang.";
    }

    // Method untuk menghapus suatu Product di dalam Cart
    public String deleteFromCart(UUID productId) {
        for (CartProduct product : keranjang) {
            if (product.getProductId().equals(productId)) {
                keranjang.remove(product);
                return "Produk berhasil dihapus dari keranjang.";
            }
        }
        return "Produk tidak ditemukan di keranjang.";
    }

    // Method untuk mendapatkan isi konten suatu Cart
    public CartProduct[] getCartContent() {
        return keranjang.toArray(new CartProduct[0]);
    }
}