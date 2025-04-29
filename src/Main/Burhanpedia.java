package Main;
import Repository.*;

public class Burhanpedia {
    private UserRepository userRepo;
    private AdminRepository adminRepo;
    private VoucherRepository voucherRepo;
    private PromoRepository promoRepo;
    private TransaksiRepository transaksiRepo;

    public UserRepository getUserRepo() {
        return userRepo;
    }

    public AdminRepository getAdminRepo() {
        return adminRepo;
    }

    public VoucherRepository getVoucherRepo() {
        return voucherRepo;
    }

    public PromoRepository getPromoRepo() {
        return promoRepo;
    }

    public TransaksiRepository getTransaksiRepo() { return transaksiRepo; }

    public Burhanpedia() {
        this.userRepo = new UserRepository();
        this.adminRepo = new AdminRepository();
        this.voucherRepo = new VoucherRepository();
        this.promoRepo = new PromoRepository();
        this.transaksiRepo = new TransaksiRepository();
    }

    /** Method ini digunakan untuk menghitung keseluruhan total harga produk yang dibeli pada suatu transaksi,
     * setelah penambahan pajak dan pengurangan diskon (jika ada). Method ini akan menerima id transaksi yang
     * berupa String dan mengembalikan long dari total harga pembayaran pada transaksi tersebut.*/
    public long calculateTotalTransaksi(String idTransaksi) {
        long harga = 0;
        return harga;
    }

}