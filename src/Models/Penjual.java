package Models;
import Repository.ProductRepository;

public class Penjual extends User {
    private ProductRepository productRepo; // Menyimpan seluruh Product yang dimiliki penjual

    public Penjual(String username, String password, String namaToko) {
        super(username, password, "Penjual");
        // Ada namaToko yaa
        this.productRepo = new ProductRepository(namaToko); // Inisialisasi repository produk untuk penjual
    }

    public ProductRepository getProductRepo() {
        return this.productRepo;
    }

    @Override
    public void getRiwayatTransaksi(Transaksi[] transaksi) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRiwayatTransaksi'");
    }
}