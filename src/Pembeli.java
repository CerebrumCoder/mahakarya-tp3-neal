import user.User;

public class Pembeli extends User {
    private Cart keranjang; // 1-to-1 relationship, satu pembeli memiliki satu cart

    public Pembeli(String username, String password) {
        super(username, password, "Pembeli");
        this.keranjang = new Cart(); // Inisialisasi keranjang baru untuk pembeli
    }

    public Cart getCart() {
        return keranjang;
    }
}
