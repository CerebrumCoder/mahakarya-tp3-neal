package Models;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cart {
    private List<CartProduct> keranjangList; // Menyimpan seluruh CartProduct yang sedang dimiliki User

    public Cart() {
        this.keranjangList = new ArrayList<>();
    }

    // Method untuk menambahkan isi cart
    public void addToCart(UUID productId, int quantity) {
        for (CartProduct product : keranjangList) {
            if (product.getProductId().equals(productId)) {
                product.setAmount(product.getProductAmount() + quantity);
                return;
            }
        }
        keranjangList.add(new CartProduct(productId, quantity));
    }

    // Method untuk menghapus suatu Product di dalam Cart
    // dan mengembalikan String yang berupa pesan sukses atau error
    public String deleteFromCart(UUID productId) {
        for (CartProduct product : keranjangList) {
            if (product.getProductId().equals(productId)) {
                keranjangList.remove(product);
                return "Produk berhasil dihapus dari keranjang.";
            }
        }
        return "Produk tidak ditemukan di keranjang.";
    }

    // Method untuk mendapatkan isi konten suatu Cart
    public List<CartProduct> getCartContent() {
        // Mengembalikan salinan daftar keranjang
        return new ArrayList<>(keranjangList);
        // Awal ini:
        // return keranjangList.toArray(new CartProduct[0]);
    }
}