package Main;

import java.util.List;

import Models.Penjual;
import Models.Product;
import Models.Promo;
import Models.Transaksi;
import Models.User;
import Models.Voucher;
import Repository.*;
import System.TransactionProduct;

public class Burhanpedia {
    private UserRepository userRepo;
    private AdminRepository adminRepo;
    private VoucherRepository voucherRepo;
    private PromoRepository promoRepo;
    private TransaksiRepository transaksiRepo;

    public UserRepository getUserRepo() {
        return this.userRepo;
    }

    public AdminRepository getAdminRepo() {
        return this.adminRepo;
    }

    public VoucherRepository getVoucherRepo() {
        return this.voucherRepo;
    }

    public PromoRepository getPromoRepo() {
        return this.promoRepo;
    }

    public TransaksiRepository getTransaksiRepo() {
        return this.transaksiRepo;
    }

    public Burhanpedia() {
        this.userRepo = new UserRepository();
        this.adminRepo = new AdminRepository();
        this.voucherRepo = new VoucherRepository();
        this.promoRepo = new PromoRepository();
        this.transaksiRepo = new TransaksiRepository();
    }

    /**
     * Method ini digunakan untuk menghitung keseluruhan total harga produk yang
     * dibeli pada suatu transaksi,
     * setelah penambahan pajak dan pengurangan diskon (jika ada). Method ini akan
     * menerima id transaksi yang
     * berupa String dan mengembalikan long dari total harga pembayaran pada
     * transaksi tersebut.
     */
    public long calculateTotalTransaksi(String idTransaksi) {
        Transaksi transaksi = this.transaksiRepo.getList().stream()
                .filter(t -> t.getId().equals(idTransaksi))
                .findFirst()
                .orElse(null);

        if (transaksi == null) {
            System.out.println("Transaksi dengan ID " + idTransaksi + " tidak ditemukan.");
            return 0;
        }

        double subtotal = 0;

        // Hitung subtotal berdasarkan produk yang dibeli
        for (TransactionProduct produkTransaksi : transaksi.getProdukDibeli()) {
            Product product = null;

            // Cari produk di semua penjual
            List<User> userList = this.userRepo.getAll();
            for (User user : userList) {
                if (user instanceof Penjual penjual) {
                    product = penjual.getProductRepo().getProductById(produkTransaksi.getProductId());
                    if (product != null) {
                        break;
                    }
                }
            }

            if (product != null) {
                subtotal += product.getProductPrice() * produkTransaksi.getProductAmount();
            }
        }

        // Hitung diskon
        double hargaDiskon = 0;
        if (transaksi.getIdDiskon() != null) {
            Voucher voucher = this.voucherRepo.getById(transaksi.getIdDiskon());
            if (voucher != null) {
                int persenDiskon = voucher.calculateDisc();
                hargaDiskon = subtotal * persenDiskon / 100.0;
            } else {
                Promo promo = this.promoRepo.getById(transaksi.getIdDiskon());
                if (promo != null) {
                    int persenDiskon = promo.calculateDisc();
                    hargaDiskon = subtotal * persenDiskon / 100.0;
                }
            }
        }

        // Hitung total transaksi
        double total = subtotal - hargaDiskon + transaksi.getBiayaOngkir();

        return Math.round(total); // Kembalikan total transaksi dalam bentuk long
    }

}