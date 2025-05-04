package System;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import Models.*;
import Main.Burhanpedia;

public class SystemPembeli implements SystemMenu {
    private Pembeli activePembeli;
    private Scanner input;
    private Burhanpedia mainRepository;

    public SystemPembeli(Burhanpedia mainRepository) {
        this.mainRepository = mainRepository;
        this.input = new Scanner(System.in);
    }

    @Override
    public String showMenu() {
        return """
                ===== MENU PEMBELI =====
                1. Cek Saldo
                2. Top Up Saldo
                3. Cek Daftar Barang
                4. Tambah Barang ke Keranjang
                5. Checkout Keranjang
                6. Lacak Barang
                7. Lihat Laporan Pengeluaran
                8. Lihat Riwayat Transaksi
                9. Kembali ke menu utama
                """;
    }

    @Override
    public void handleMenu() {
        while (true) {
            System.out.println(showMenu());
            System.out.print("Pilih menu: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> handleCekSaldo();
                case 2 -> handleTopupSaldo();
                case 3 -> handleCekDaftarBarang();
                case 4 -> handleTambahKeKeranjang();
                case 5 -> handleCheckout();
                case 6 -> handleLacakBarang();
                case 7 -> handleLaporanPengeluaran();
                case 8 -> handleRiwayatTransaksi();
                case 9 -> {
                    return;
                }

                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    /**
     * Ini berfungsi untuk set activePembeli di file SystemPembeli.java terdefinisi.
     * Caranya kita panggil method di bawah ini di MainMenuSystem.java ketika mau add User Pembeli.
     * Lalu datanya di pass lewat parameter terus di definisikan di this.activePembeli */
    public void setActivePembeli(String username) {
        // Ambil penjual dari repository berdasarkan username
        Pembeli getPembeli = (Pembeli) mainRepository.getUserRepo().getUserByName(username);

        if (getPembeli != null && getPembeli.getRole().equals("Pembeli")) {
            this.activePembeli = getPembeli;
        } else {
            System.out.println("Pembeli dengan username " + username + " tidak ditemukan atau bukan pembeli.");
        }
    }

    public void handleCekSaldo() {
        // Implementasi untuk cek saldo pembeli
        System.out.println("=================================");
        System.out.printf("Stok saat ini: %.2f%n", (double) activePembeli.getBalance());
        System.out.println("=================================\n");
    }

    public void handleTopupSaldo() {
        // Implementasi untuk topup saldo pembeli
        System.out.print("Masukkan jumlah saldo yang ingin ditambah: ");
        long price = input.nextLong();

        // Setelah dapat pricenya lalu disimpan ke dalam kelas User Pembeli
        activePembeli.setBalance(price);
        System.out.printf("Saldo berhasil ditambah! Saldo saat ini: %.2f", (double) activePembeli.getBalance());
        System.out.println("\n");
    }

    public void handleCekDaftarBarang() {
        // Implementasi untuk cek daftar barang
        // Ambil semua user dari UserRepository
        List<User> userList = mainRepository.getUserRepo().getAll();

        System.out.println("=================================");
        boolean adaBarang = false;

        // Iterasi semua user untuk mencari penjual
        for (User user : userList) {
            if (user instanceof Penjual penjual) {
                // Ambil daftar produk dari ProductRepository.java milik Penjual
                List<Product> productList = penjual.getProductRepo().getProductList();

                // Jika penjual memiliki produk, tampilkan nama toko dan produk
                if (!productList.isEmpty()) {
                    adaBarang = true;
                    System.out.println(penjual.getProductRepo().getNamaToko());
                    for (Product product : productList) {
                        System.out.printf("%-8s %10.2f %5d%n", product.getProductName(), (double) product.getProductPrice(), product.getProductStock());
                    }
                }

            }
        }
        if (!adaBarang) {
            System.out.println("Tidak ada barang yang tersedia");
        }
        System.out.println("=================================\n");

    }

    public void handleTambahKeKeranjang() {
        // Implementasi untuk menambah barang ke keranjang
        // Nambah barang ke keranjang

        // Input toko penjual
        System.out.print("Masukkan toko penjual barang yang ingin dibeli: ");
        String namaToko = input.next();

        // Input nama barang
        System.out.print("Masukkan nama barang yang ingin dibeli: ");
        String namaBarang = input.next();

        // Input jumlah barang
        System.out.print("Masukkan jumlah barang yang ingin dibeli: ");
        int jumlahBarang = input.nextInt();

        // Cari penjual berdasarkan nama toko
        List<User> userList = mainRepository.getUserRepo().getAll();
        Penjual penjualDitemukan = null;
        for (User user : userList) {
            if (user instanceof Penjual penjual && penjual.getProductRepo().getNamaToko().equalsIgnoreCase(namaToko)) {
                penjualDitemukan = penjual;
                break;
            }
        }

        if (penjualDitemukan == null) {
            System.out.println("Toko dengan nama " + namaToko + " tidak ditemukan.");
            return;
        }

        // Cari produk berdasarkan nama barang
        Product produkDitemukan = null;
        for (Product product : penjualDitemukan.getProductRepo().getProductList()) {
            if (product.getProductName().equalsIgnoreCase(namaBarang)) {
                produkDitemukan = product;
                break;
            }
        }

        if (produkDitemukan == null) {
            System.out.println("Barang dengan nama " + namaBarang + " tidak ditemukan di toko " + namaToko + "!");
            return;
        }

        // Tambahkan barang ke keranjang pembeli
        activePembeli.getCart().addToCart(produkDitemukan.getProductId(), jumlahBarang);
    }

    public void handleCheckout() {
        // Implementasi untuk checkout keranjang
        // Ambil isi keranjang
        List<CartProduct> keranjangList = activePembeli.getCart().getCartContent();

        if (keranjangList.isEmpty()) {
            System.out.println("=================================");
            System.out.println("Keranjang masih kosong!");
            System.out.println("=================================\n");
            return;
        }

        // Tampilkan isi keranjang
        System.out.println("=================================");
        long subtotal = 0;
        for (CartProduct cartProduct : keranjangList) {
            Product product = null;

            // Cari produk di semua penjual
            List<User> userList = mainRepository.getUserRepo().getAll();
            for (User user : userList) {
                if (user instanceof Penjual penjual) {
                    product = penjual.getProductRepo().getProductById(cartProduct.getProductId());
                    if (product != null) {
                        break;
                    }
                }
            }
            if (product == null) {
                System.out.println("Product dengan ID " + cartProduct.getProductId() + " tidak ditemukan!");
                return; // Jika ada produk yang tidak ditemukan, batalkan checkout
            }

            // Ini mengkalikan harga produk dengan banyak produk yang dibeli sama User. Gunakan jumlah dari CartProduct, bukan stok produk
            long totalHarga = product.getProductPrice() * cartProduct.getProductAmount();
            subtotal += totalHarga;

            // Tampilkan informasi produk
            System.out.printf("%-8s %10.2f %5d (%10.2f)%n", product.getProductName(), (double) product.getProductPrice(), cartProduct.getProductAmount(), (double) totalHarga);
        }

        // Output subtotal
        System.out.println("---------------------------------");
        System.out.printf("Subtotal %10.2f%n", (double) subtotal);
        System.out.println("=================================");

        // Konfirmasi pembelian. Kalo tidak sama dengan Y maka Checkout dibatalkan
        System.out.print("Apakah Anda yakin dengan produknya? (Y/N): ");
        String konfirmasi = input.next();
        if (!konfirmasi.equalsIgnoreCase("Y")) {
            System.out.println("Checkout dibatalkan.");
            return;
        }

        // Masukkan kode voucher
        System.out.println("Masukkan kode voucher.\nJika tidak ada, ketik 'skip'");
        System.out.println("=================================");
        System.out.print("Kode: ");
        String kodeVoucher = input.next();
        double diskon = 0;
        if (!kodeVoucher.equalsIgnoreCase("skip")) {
            Voucher voucher = mainRepository.getVoucherRepo().getById(kodeVoucher);
            if (voucher != null && voucher.isValid(new Date())) {
                diskon = voucher.calculateDisc(subtotal);
            }
        }


    }

    public void handleLacakBarang() {
        System.out.println("Lacak Barang dipilih. Implementasi di sini.");
        // Implementasi untuk melacak barang
    }

    public void handleLaporanPengeluaran() {
        // Implementasi untuk melihat laporan pengeluaran
        System.out.println("=================================");
        System.out.println("Laporan pengeluaran masih kosong!");
        System.out.println("=================================\n");
    }

    /**Informasi yang akan ditampilkan meliputi Id transaksi, jumlah pendapatan, timestamp, dan
     * keterangan seluruh transaksi-transaksi yang dibuat oleh Pembeli yang logged in saat ini.*/
    public void handleRiwayatTransaksi() {
        // Implementasi untuk melihat riwayat transaksi
        System.out.println("======= RIWAYAT TRANSAKSI =======");
        System.out.println("Riwayat transaksi masih kosong!");
        System.out.println("=================================\n");
    }
}