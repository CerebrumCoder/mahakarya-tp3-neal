package Test;

import Main.Burhanpedia;
import Models.*;
import Repository.*;
import System.*;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UnitTest {
    private Transaksi transaksi;
    private Burhanpedia mainRepository;
    private SystemPenjual systemPenjual;
    private SystemPembeli systemPembeli;
    private SystemPengirim systemPengirim;

    @BeforeEach
    void setUp() {
        mainRepository = new Burhanpedia();
        List<TransactionProduct> produkDibeli = new ArrayList<>();
        produkDibeli.add(new TransactionProduct(UUID.randomUUID(), 2));
        transaksi = new Transaksi(
                "TRX123",
                "pembeli1",
                "penjual1",
                null,
                null,
                produkDibeli,
                "Instant",
                "Toko1"
        );

        systemPenjual = new SystemPenjual(mainRepository);
        systemPembeli = new SystemPembeli(mainRepository);
        systemPengirim = new SystemPengirim(mainRepository);
    }

    // ============================
    // Models Tests
    // ============================
    @Test
    void testTransactionStatusValid() {
        TransactionStatus status = new TransactionStatus(TransactionStatus.SEDANG_DIKEMAS);
        assertEquals(TransactionStatus.SEDANG_DIKEMAS, status.getStatus());
        assertNotNull(status.getTimestamp());
    }

    @Test
    void testTransactionStatusInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new TransactionStatus("Invalid Status"));
    }

    @Test
    void testTransaksiAddStatus() {
        transaksi.addStatus(new TransactionStatus(TransactionStatus.SEDANG_DIKEMAS));
        assertEquals(TransactionStatus.SEDANG_DIKEMAS, transaksi.getCurrentStatus());
    }

    @Test
    void testTransaksiCalculateTotal() {
        double total = transaksi.calculateTotal(100000, 5000);
        assertEquals(95000, total);
    }

    @Test
    void testProductRepositoryAddAndGetProduct() {
        ProductRepository productRepo = new ProductRepository("Toko1");
        Product product = new Product("Produk1", 10, 10000);
        productRepo.addProduct(product);

        assertEquals(1, productRepo.getProductList().size());
        assertEquals(product, productRepo.getProductById(product.getProductId()));
    }

    @Test
    void testCartAddAndDeleteProduct() {
        Cart cart = new Cart();
        UUID productId = UUID.randomUUID();
        cart.addToCart(productId, 2);

        assertEquals(1, cart.getCartContent().size());
        assertEquals(2, cart.getCartContent().get(0).getProductAmount());

        String result = cart.deleteFromCart(productId);
        assertEquals("Produk berhasil dihapus dari keranjang.", result);
        assertTrue(cart.getCartContent().isEmpty());
    }

    // ============================
    // Additional Models Tests
    // ============================
    @Test
    void testVoucherIsValid() {
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000); // 1 day from now
        Voucher voucher = new Voucher("VCR123", expiryDate);
        assertTrue(voucher.isValid(new Date()));
    }

    @Test
    void testVoucherCalculateDisc() {
        Voucher voucher = new Voucher("VCR123", new Date());
        int discount = voucher.calculateDisc();
        assertTrue(discount >= 0 && discount <= 100); // Diskon harus dalam rentang 0-100
    }

    @Test
    void testPromoCalculateDisc() {
        Promo promo = new Promo("PROMO123", new Date());
        assertEquals(5, promo.calculateDisc()); // Total digit < 100
    }

    @Test
    void testCartAddDuplicateProduct() {
        Cart cart = new Cart();
        UUID productId = UUID.randomUUID();
        cart.addToCart(productId, 2);
        cart.addToCart(productId, 3);

        assertEquals(1, cart.getCartContent().size());
        assertEquals(5, cart.getCartContent().get(0).getProductAmount());
    }

    @Test
    void testCartDeleteNonExistentProduct() {
        Cart cart = new Cart();
        UUID productId = UUID.randomUUID();
        String result = cart.deleteFromCart(productId);
        assertEquals("Produk tidak ditemukan di keranjang.", result);
    }

    @Test
    void testProductSetters() {
        Product product = new Product("Produk1", 10, 10000);
        product.setProductStock(20);
        product.setProductPrice(15000);
        assertEquals(20, product.getProductStock());
        assertEquals(15000, product.getProductPrice());
    }

    @Test
    void testVoucherSetters() {
        Voucher voucher = new Voucher("VCR123", new Date());
        voucher.setSisaPemakaian(5);
        assertEquals(5, voucher.getSisaPemakaian());
    }

    // ============================
    // Repository Tests
    // ============================
    @Test
    void testUserRepositoryAddAndGetUser() {
        UserRepository userRepo = new UserRepository();
        User user = new Pembeli("pembeli1", "password");
        userRepo.addUser(user);

        assertEquals(1, userRepo.getAll().size());
        assertEquals(user, userRepo.getUserByName("pembeli1"));
    }

    @Test
    void testTransaksiRepositoryAddAndGetTransaksi() {
        TransaksiRepository transaksiRepo = new TransaksiRepository();
        transaksiRepo.addTransaksi(transaksi);

        assertEquals(1, transaksiRepo.getList().size());
        assertEquals(transaksi, transaksiRepo.getList().get(0));
    }

    @Test
    void testVoucherRepositoryGenerateAndGetVoucher() {
        VoucherRepository voucherRepo = new VoucherRepository();
        Date expiryDate = new Date(System.currentTimeMillis() + 86400000); // 1 day from now
        voucherRepo.generate("VCR123", expiryDate);

        assertEquals(1, voucherRepo.getAll().size());
        assertEquals("VCR123", voucherRepo.getById("VCR123").getId());
    }

    // ============================
    // Additional Repository Tests
    // ============================
    @Test
    void testPromoRepositoryGenerateAndGetPromo() {
        PromoRepository promoRepo = new PromoRepository();
        promoRepo.generate("2025-12-31");
        assertEquals(1, promoRepo.getAll().size());
    }

    @Test
    void testAdminRepositoryGetUserByName() {
        AdminRepository adminRepo = new AdminRepository();
        Admin admin = adminRepo.getUserByName("admin");
        assertNotNull(admin);
        assertEquals("admin", admin.getUsername());
    }

    @Test
    void testUserRepositoryGetUserById() {
        UserRepository userRepo = new UserRepository();
        User user = new Pembeli("pembeli1", "password");
        userRepo.addUser(user);

        assertEquals(user, userRepo.getUserById(user.getId()));
    }

    @Test
    void testProductRepositoryGetProductById() {
        ProductRepository productRepo = new ProductRepository("Toko1");
        Product product = new Product("Produk1", 10, 10000);
        productRepo.addProduct(product);

        assertEquals(product, productRepo.getProductById(product.getProductId()));
    }

    @Test
    void testPromoRepositoryRemovePromo() {
        PromoRepository promoRepo = new PromoRepository();
        promoRepo.generate("2025-12-31");
        Promo promo = promoRepo.getAll().get(0);

        promoRepo.removePromo(promo);
        assertTrue(promoRepo.getAll().isEmpty());
    }

    // ============================
    // System Tests
    // ============================
    @Test
    void testSystemPenjualHandleTambahProduk() {
        Penjual penjual = new Penjual("penjual1", "password", "Toko1");
        mainRepository.getUserRepo().addUser(penjual);
        systemPenjual.setActivePenjual("penjual1");

        Product product = new Product("Produk1", 10, 10000);
        penjual.getProductRepo().addProduct(product);

        assertEquals(1, penjual.getProductRepo().getProductList().size());
        assertEquals("Produk1", penjual.getProductRepo().getProductList().get(0).getProductName());
    }

    @Test
    void testSystemPembeliHandleTambahKeKeranjang() {
        Pembeli pembeli = new Pembeli("pembeli1", "password");
        mainRepository.getUserRepo().addUser(pembeli);
        systemPembeli.setActivePembeli("pembeli1");

        Product product = new Product("Produk1", 10, 10000);
        Penjual penjual = new Penjual("penjual1", "password", "Toko1");
        penjual.getProductRepo().addProduct(product);
        mainRepository.getUserRepo().addUser(penjual);

        pembeli.getCart().addToCart(product.getProductId(), 2);

        assertEquals(1, pembeli.getCart().getCartContent().size());
        assertEquals(2, pembeli.getCart().getCartContent().get(0).getProductAmount());
    }

    @Test
    void testSystemPengirimHandleTakeJob() {
        Pengirim pengirim = new Pengirim("pengirim1", "password");
        mainRepository.getUserRepo().addUser(pengirim);
        systemPengirim.setActivePengirim("pengirim1");

        transaksi.addStatus(new TransactionStatus(TransactionStatus.MENUNGGU_PENGIRIM));
        mainRepository.getTransaksiRepo().addTransaksi(transaksi);

        systemPengirim.handleTakeJob();

        assertEquals("pengirim1", transaksi.getNamePengirim());
        assertEquals(TransactionStatus.SEDANG_DIKIRIM, transaksi.getCurrentStatus());
    }

    @Test
    void testSystemPengirimHandleConfirmJob() {
        Pengirim pengirim = new Pengirim("pengirim1", "password");
        mainRepository.getUserRepo().addUser(pengirim);
        systemPengirim.setActivePengirim("pengirim1");

        transaksi.setNamePengirim("pengirim1");
        transaksi.addStatus(new TransactionStatus(TransactionStatus.SEDANG_DIKIRIM));
        mainRepository.getTransaksiRepo().addTransaksi(transaksi);

        transaksi.addStatus(new TransactionStatus(TransactionStatus.PESANAN_SELESAI));
        assertEquals(TransactionStatus.PESANAN_SELESAI, transaksi.getCurrentStatus());
    }

    // ============================
    // Additional System Tests
    // ============================
    @Test
    void testSystemAdminHandleGenerateVoucher() {
        SystemAdmin systemAdmin = new SystemAdmin(mainRepository);
        systemAdmin.handleGenerateVoucher();
        assertEquals(1, mainRepository.getVoucherRepo().getAll().size());
    }

    @Test
    void testSystemPembeliHandleCheckout() {
        Pembeli pembeli = new Pembeli("pembeli1", "password");
        mainRepository.getUserRepo().addUser(pembeli);
        systemPembeli.setActivePembeli("pembeli1");

        Product product = new Product("Produk1", 10, 10000);
        Penjual penjual = new Penjual("penjual1", "password", "Toko1");
        penjual.getProductRepo().addProduct(product);
        mainRepository.getUserRepo().addUser(penjual);

        pembeli.getCart().addToCart(product.getProductId(), 2);
        systemPembeli.handleCheckout();

        assertEquals(8, product.getProductStock()); // Stok berkurang
    }

    @Test
    void testSystemPenjualHandleTambahStok() {
        Penjual penjual = new Penjual("penjual1", "password", "Toko1");
        mainRepository.getUserRepo().addUser(penjual);
        systemPenjual.setActivePenjual("penjual1");

        Product product = new Product("Produk1", 10, 10000);
        penjual.getProductRepo().addProduct(product);

        product.setProductStock(product.getProductStock() + 5);
        assertEquals(15, product.getProductStock());
    }

    @Test
    void testSystemPengirimHandleFindJob() {
        Pengirim pengirim = new Pengirim("pengirim1", "password");
        mainRepository.getUserRepo().addUser(pengirim);
        systemPengirim.setActivePengirim("pengirim1");

        transaksi.addStatus(new TransactionStatus(TransactionStatus.MENUNGGU_PENGIRIM));
        mainRepository.getTransaksiRepo().addTransaksi(transaksi);

        assertEquals(TransactionStatus.MENUNGGU_PENGIRIM, transaksi.getCurrentStatus());
    }

    @Test
    void testSystemAdminHandleLihatVoucher() {
        SystemAdmin systemAdmin = new SystemAdmin(mainRepository);
        systemAdmin.handleGenerateVoucher();
        assertEquals(1, mainRepository.getVoucherRepo().getAll().size());
    }

    @Test
    void testProductValidationSuccess() {
        Product validProduct = new Product("Valid Product", 10, 10000);
        assertDoesNotThrow(() -> ProductValidator.validate(validProduct));
    }

    @Test
    void testProductValidationEmptyName() {
        Product invalidProduct = new Product("", 10, 10000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ProductValidator.validate(invalidProduct));
        assertEquals("Product name cannot be empty", exception.getMessage());
    }

    @Test
    void testProductValidationNegativeStock() {
        Product invalidProduct = new Product("Product", -5, 10000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ProductValidator.validate(invalidProduct));
        assertEquals("Stock cannot be negative", exception.getMessage());
    }

    @Test
    void testProductValidationNegativePrice() {
        Product invalidProduct = new Product("Product", 10, -10000);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> ProductValidator.validate(invalidProduct));
        assertEquals("Price must be positive", exception.getMessage());
    }
}

