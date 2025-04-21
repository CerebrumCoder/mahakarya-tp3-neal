import user.User;

public class Penjual extends User {
    private ProductRepository productRepo; // Menyimpan seluruh Product yang dimiliki penjual

    public Penjual(String username, String password) {
        super(username, password, "Penjual");
        // Ada namaToko yaa
        this.productRepo = new ProductRepository(""); // Inisialisasi repository produk untuk penjual
    }

    public ProductRepository getRepo() {
        return productRepo;
    }
}
