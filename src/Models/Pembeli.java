package Models;

public class Pembeli extends User {
    private Cart keranjang; // 1-to-1 relationship, satu pembeli memiliki satu cart

    public Pembeli(String username, String password) {
        super(username, password, "Pembeli");
        this.keranjang = new Cart(); // Inisialisasi keranjang baru untuk pembeli
    }

    public Cart getCart() {
        return keranjang;
    }

    @Override
    public void getRiwayatTransaksi(Transaksi[] transaksi) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRiwayatTransaksi'");
    }
}
