package System;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.text.SimpleDateFormat;

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
            System.out.print("Perintah : ");
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
     * Caranya kita panggil method di bawah ini di MainMenuSystem.java ketika mau
     * add User Pembeli.
     * Lalu datanya di pass lewat parameter terus di definisikan di
     * this.activePembeli
     */
    public void setActivePembeli(String username) {
        // Ambil pembeli dari repository berdasarkan username
        // Menggunakan instance of supaya melakukan casting class dengan mudah dan tidak
        // terjadi error
        User user = mainRepository.getUserRepo().getUserByNameAndRole(username, "Pembeli");

        // Debugging untuk memastikan tipe objek
        // System.out.println(user != null ? user.getClass().getSimpleName() : "User
        // tidak ditemukan");

        // Periksa apakah User adalah instance dari Pembeli
        if (user instanceof Pembeli pembeli) {
            this.activePembeli = pembeli;
        } else {
            System.out.println("Pembeli dengan username " + username + " tidak ditemukan atau bukan pembeli.");
        }
    }

    public void handleCekSaldo() {
        // Implementasi untuk cek saldo pembeli
        System.out.println("=================================");
        System.out.printf("Saldo saat ini: %.2f%n", (double) activePembeli.getBalance());
        System.out.println("=================================\n");
    }

    public void handleTopupSaldo() {
        // Implementasi untuk topup saldo pembeli
        System.out.print("Masukkan jumlah saldo yang ingin ditambah: ");
        long price = input.nextLong();

        // Setelah dapat pricenya lalu disimpan ke dalam kelas User Pembeli. Lalu
        // ditambah terus di set
        long saldoBaru = activePembeli.getBalance() + price;
        activePembeli.setBalance(saldoBaru);
        System.out.printf("Saldo berhasil ditambah! Saldo saat ini: %.2f", (double) activePembeli.getBalance());
        System.out.println("\n");
    }

    public void handleCekDaftarBarang() {
        // Implementasi untuk cek daftar barang
        // Ambil semua user dari UserRepository
        List<User> userList = mainRepository.getUserRepo().getAll();

        System.out.println("=================================");
        boolean adaBarang = false;
        int tokoCount = 0; // Counter untuk jumlah toko

        // Iterasi semua user untuk mencari penjual
        for (User user : userList) {
            if (user instanceof Penjual penjual) {
                // Ambil daftar produk dari ProductRepository.java milik Penjual
                List<Product> productList = penjual.getProductRepo().getProductList();

                // Jika penjual memiliki produk, tampilkan nama toko dan produk
                if (!productList.isEmpty()) {
                    adaBarang = true;

                    // Tambahkan garus pemisah jika ini bukan toko pertama
                    if (tokoCount > 0) {
                        System.out.println("---------------------------------");
                    }
                    tokoCount++;

                    // Tampilkan nama toko
                    System.out.println(penjual.getProductRepo().getNamaToko());

                    // Tampilkan daftar produk
                    for (Product product : productList) {
                        System.out.printf("%-10s %10.2f %5d%n", product.getProductName(),
                                (double) product.getProductPrice(), product.getProductStock());
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
        // Input nama toko
        System.out.print("Masukkan nama toko barang yang ingin dibeli: ");
        input.nextLine(); // Membersihkan buffer sebelum membaca input
        String namaToko = input.nextLine(); // Gunakan nextLine() untuk membaca namaToko

        // Input nama barang
        System.out.print("Masukkan nama barang yang ingin dibeli: ");
        String namaBarang = input.nextLine(); // Gunakan nextLine() untuk membaca namaBarang

        // Input jumlah barang
        System.out.print("Masukkan jumlah barang yang ingin dibeli: ");
        int jumlahBarang = input.nextInt();

        List<User> userList = mainRepository.getUserRepo().getAll();
        Penjual penjualDitemukan = null;

        // Cari penjual berdasarkan nama toko
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
            System.out.println("Barang dengan nama " + namaBarang + " tidak ditemukan pada toko " + namaToko + "!\n");
            return;
        }

        // Cek apakah keranjang sudah memiliki barang dari toko lain
        List<CartProduct> keranjangList = activePembeli.getCart().getCartContent();
        if (!keranjangList.isEmpty()) {
            // Ambil semua toko dari produk di keranjang
            String tokoKeranjang = null;

            for (CartProduct cartProduct : keranjangList) {
                for (User user : userList) {
                    if (user instanceof Penjual penjual) {
                        Product product = penjual.getProductRepo().getProductById(cartProduct.getProductId());
                        if (product != null) {
                            tokoKeranjang = penjual.getProductRepo().getNamaToko();
                            break;
                        }
                    }
                }
                if (tokoKeranjang != null)
                    break;
            }

            // Jika toko berbeda, tanyakan kepada pengguna
            if (tokoKeranjang != null && !tokoKeranjang.equalsIgnoreCase(namaToko)) {
                System.out.println("Anda sudah memiliki barang di keranjang yang berasal dari toko berbeda.");
                System.out.print("Kosongkan keranjang dan masukkan barang yang baru? (Y/N) ");
                String konfirmasi = input.next();

                if (!konfirmasi.equalsIgnoreCase("Y")) {
                    System.out.println("\nPenambahan barang dibatalkan!\n");
                    return;
                }

                // Hapus hanya barang dari toko berbeda
                for (CartProduct cp : new ArrayList<>(keranjangList)) {
                    for (User userX : userList) {
                        if (userX instanceof Penjual penjualX) {
                            Product p = penjualX.getProductRepo().getProductById(cp.getProductId());
                            if (p != null && penjualX.getProductRepo().getNamaToko().equalsIgnoreCase(tokoKeranjang)) {
                                activePembeli.getCart().deleteFromCart(cp.getProductId());
                                break;
                            }
                        }
                    }
                }
                System.out.println("\nBarang berhasil ditambahkan!");
            }
        }

        // Tambahkan barang ke keranjang pembeli
        System.out.println();
        activePembeli.getCart().addToCart(produkDitemukan.getProductId(), jumlahBarang);
    }

    public void handleCheckout() {
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
        List<User> userList = mainRepository.getUserRepo().getAll();
        long subtotal = 0;
        String namePenjual = null; // Variabel untuk menyimpan nama penjual terkait transaksi

        for (CartProduct cartProduct : keranjangList) {
            Product product = null;

            // Cari produk di semua penjual
            for (User user : userList) {
                if (user instanceof Penjual penjual) {
                    product = penjual.getProductRepo().getProductById(cartProduct.getProductId());
                    if (product != null) {
                        namePenjual = penjual.getUsername(); // Simpan nama penjual terkait
                        break;
                    }
                }
            }
            if (product == null) {
                System.out.println("Product dengan ID " + cartProduct.getProductId() + " tidak ditemukan!");
                return; // Jika ada produk yang tidak ditemukan, batalkan checkout
            }

            // Hitung total harga produk
            long totalHarga = product.getProductPrice() * cartProduct.getProductAmount();
            subtotal += totalHarga;

            // Tampilkan informasi produk
            System.out.printf("%-8s %10.2f %5d (%.2f)%n", product.getProductName(), (double) product.getProductPrice(),
                    cartProduct.getProductAmount(), (double) totalHarga);
        }

        // Output subtotal
        System.out.println("---------------------------------");
        System.out.printf("Subtotal %10.2f%n", (double) subtotal);
        System.out.println("=================================");

        // Konfirmasi pembelian
        System.out.print("Apakah Anda yakin dengan produknya? (Y/N): ");
        String konfirmasi = input.next();
        if (!konfirmasi.equalsIgnoreCase("Y")) {
            System.out.println("Checkout dibatalkan.");
            return;
        }

        // Masukkan kode voucher
        System.out.println("Masukkan kode diskon.\nJika tidak ada, ketik 'skip'");
        System.out.println("=================================");
        System.out.print("Kode: ");
        String kodeDiskon = input.next();
        double hargaDiskon = 0;
        double subtotalSetelahDiskon = subtotal;

        // Validasi kode diskon
        while (true) {
            if (!kodeDiskon.equalsIgnoreCase("skip")) {
                Voucher voucher = mainRepository.getVoucherRepo().getById(kodeDiskon);
                Promo promo = mainRepository.getPromoRepo().getById(kodeDiskon);

                if (voucher != null && voucher.isValid(new Date())) {
                    int persenDiskon = voucher.calculateDisc();
                    hargaDiskon = subtotal * persenDiskon / 100.0;
                    subtotalSetelahDiskon -= hargaDiskon;

                    // Kurangi sisa pemakaian voucher
                    voucher.setSisaPemakaian(voucher.getSisaPemakaian() - 1);
                    if (voucher.getSisaPemakaian() <= 0) {
                        mainRepository.getVoucherRepo().getAll().remove(voucher);
                    }

                    System.out.println("Voucher diterapkan! Total harga setelah diskon: " + subtotalSetelahDiskon);
                    break;
                } else if (promo != null && promo.isValid(new Date())) {
                    int persenDiskon = promo.calculateDisc();
                    hargaDiskon = subtotal * persenDiskon / 100.0;
                    subtotalSetelahDiskon -= hargaDiskon;

                    System.out.println("Promo diterapkan! Total harga setelah diskon: " + subtotalSetelahDiskon);
                    break;
                } else {
                    System.out.println("Voucher tidak valid atau sudah kadaluarsa!");
                    return;
                }
            } else {
                break;
            }
        }

        // Pilih opsi pengiriman
        System.out.println("Pilih opsi pengiriman:");
        System.out.println("1. Instant (20000)");
        System.out.println("2. Next Day (15000)");
        System.out.println("3. Regular (10000)");
        System.out.print("Pilihan pengiriman : ");
        int pilihanPengiriman = input.nextInt();
        long biayaPengiriman = switch (pilihanPengiriman) {
            case 1 -> 20000;
            case 2 -> 15000;
            case 3 -> 10000;
            default -> throw new IllegalStateException("Unexpected value: " + pilihanPengiriman);
        };

        // Hitung total akhir
        double pajak = subtotalSetelahDiskon * 0.03;
        double totalAkhir = subtotalSetelahDiskon + pajak + biayaPengiriman;

        // Cek saldo pembeli
        if (activePembeli.getBalance() < totalAkhir) {
            System.out.println("Pembelian gagal. Saldo tidak cukup!\n");
            return;
        }

        // Kurangi saldo pembeli
        activePembeli.setBalance((long) (activePembeli.getBalance() - totalAkhir));
        System.out.printf("Pembelian sukses! Saldo saat ini: %.2f", (double) activePembeli.getBalance());
        System.out.println("\n");

        // Kurangi stok produk sesuai jumlah yang dibeli
        for (CartProduct cartProduct : keranjangList) {
            for (User user : userList) {
                if (user instanceof Penjual penjual) {
                    Product product = penjual.getProductRepo().getProductById(cartProduct.getProductId());
                    if (product != null) {
                        int stokBaru = product.getProductStock() - cartProduct.getProductAmount();
                        product.setProductStock(stokBaru);
                    }
                }
            }
        }

        // Tambahkan transaksi ke repository
        List<TransactionProduct> produkDibeli = new ArrayList<>();
        for (CartProduct cartProduct : keranjangList) {
            produkDibeli.add(new TransactionProduct(cartProduct.getProductId(), cartProduct.getProductAmount()));
        }

        // Pastikan nama penjual ditemukan
        if (namePenjual == null) {
            System.out.println("Nama penjual tidak ditemukan. Transaksi dibatalkan.");
            return;
        }

        // Ini untuk mencari namaToko dulu, makanya pake for each loop
        String namaToko = null;
        for (User user : userList) {
            if (user instanceof Penjual penjual && penjual.getUsername().equals(namePenjual)) {
                namaToko = penjual.getProductRepo().getNamaToko();
                break;
            }
        }
        if (namaToko == null) {
            System.out.println("Nama toko tidak ditemukan. Transaksi dibatalkan.");
            return;
        }

        // Buat transaksi baru
        Transaksi transaksiBaru = new Transaksi(
                "TRX" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
                        + String.format("%04d", mainRepository.getTransaksiRepo().getList().size() + 1),
                activePembeli.getUsername(),
                namePenjual, // Isi namePenjual langsung
                null, // namePengirim tetap null sampai pengirim mengambil job
                kodeDiskon.equalsIgnoreCase("skip") ? null : kodeDiskon,
                produkDibeli,
                pilihanPengiriman == 1 ? "Instant" : pilihanPengiriman == 2 ? "Next Day" : "Regular",
                namaToko);

        // Tambahkan transaksi ke dalam repository
        mainRepository.getTransaksiRepo().addTransaksi(transaksiBaru);
    }

    public void handleLacakBarang() {
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        boolean adaTransaksi = false;
        int transaksiCount = 0;

        System.out.println("=================================");
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePembeli().equals(activePembeli.getUsername())) {
                adaTransaksi = true;

                // Format tanggal menggunakan SimpleDateFormat
                String tanggal;
                if (transaksi.getHistoryStatus().isEmpty()) {
                    tanggal = "Tanggal tidak tersedia";
                } else {
                    SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
                    tanggal = formatter.format(transaksi.getHistoryStatus().get(0).getTimestamp());
                }

                // Tambahkan garis pemisah jika ini bukan transaksi pertama
                if (transaksiCount > 0) {
                    System.out.println("---------------------------------");
                }

                // Perbarui status transaksi jika status sebelumnya adalah "Sedang Dikirim"
                if (transaksi.getCurrentStatus().equals(TransactionStatus.SEDANG_DIKIRIM)) {
                    transaksi.addStatus(new TransactionStatus(TransactionStatus.PESANAN_SELESAI));
                }

                // Tampilkan informasi transaksi
                System.out.printf("ID Transaksi    %s%n", transaksi.getId());
                System.out.printf("Tanggal         %s%n", tanggal);
                System.out.printf("Toko            %s%n", transaksi.getNamaToko());
                System.out.printf("Status          %s%n", transaksi.getCurrentStatus());
                if (transaksi.getNamePengirim() != null) {
                    System.out.printf("Pengirim        %s%n", transaksi.getNamePengirim());
                }

                // Increment jumlah transaksi
                transaksiCount++;
            }
        }

        if (!adaTransaksi) {
            System.out.println("Tidak ada transaksi yang ditemukan.");
        }
        System.out.println("=================================\n");
    }

    public void handleLaporanPengeluaran() {
        // Implementasi untuk melihat laporan pengeluaran
        // Ambil daftar transaksi dari TransaksiRepository
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();

        // Filter transaksi berdasarkan nama pembeli
        boolean adaTransaksi = false;
        double totalPengeluaran = 0;

        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePembeli().equals(activePembeli.getUsername())) {
                adaTransaksi = true;

                // Tampilkan laporan pengeluaran
                System.out.println("===== LAPORAN PENGELUARAN =====");
                System.out.printf("ID Transaksi    %s%n", transaksi.getId());

                // Format tanggal transaksi menggunakan formatter dengan locale Indonesia
                System.out.printf("Tanggal         %s%n", LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", new Locale("id", "ID"))));
                System.out.println("---------------------------------");

                // Tampilkan produk yang dibeli
                double subtotal = 0;
                for (TransactionProduct produkTransaksi : transaksi.getProdukDibeli()) {
                    Product product = null;

                    // Cari produk di semua penjual
                    List<User> userList = mainRepository.getUserRepo().getAll();
                    for (User user : userList) {
                        if (user instanceof Penjual penjual) {
                            product = penjual.getProductRepo().getProductById(produkTransaksi.getProductId());
                            if (product != null) {
                                break;
                            }
                        }
                    }

                    if (product != null) {
                        long totalHarga = product.getProductPrice() * produkTransaksi.getProductAmount();
                        subtotal += totalHarga;
                        System.out.printf("%-10s %10.2f %5d (%10.2f)%n", product.getProductName(),
                                (double) product.getProductPrice(), produkTransaksi.getProductAmount(),
                                (double) totalHarga);
                    }
                }

                // Hitung diskon, pajak, dan total
                double hargaDiskon = 0;
                // double subtotalSetelahDiskon = 0;
                if (transaksi.getIdDiskon() != null) {

                    // Cek apakah diskon berasal dari voucher
                    Voucher voucher = mainRepository.getVoucherRepo().getById(transaksi.getIdDiskon());
                    if (voucher != null) {
                        int persenDiskon = voucher.calculateDisc();
                        hargaDiskon = subtotal * persenDiskon / 100.0;
                        // subtotalSetelahDiskon = subtotal - hargaDiskon;
                    } else {
                        // Jika bukan voucher, cek apakah diskon berasal dari promo
                        Promo promo = mainRepository.getPromoRepo().getById(transaksi.getIdDiskon());
                        if (promo != null) {
                            int persenDiskon = promo.calculateDisc();
                            hargaDiskon = subtotal * persenDiskon / 100.0;
                        }

                    }
                }

                double pajak = subtotal * 0.03; // Pajak 3%
                double total = subtotal - hargaDiskon + pajak + transaksi.getBiayaOngkir();

                // Tambahkan ke total pengeluaran
                totalPengeluaran += total;

                // Tampilkan ringkasan transaksi
                System.out.println("---------------------------------");
                System.out.printf("Subtotal   %10.2f%n", subtotal);
                System.out.printf("Diskon     %10.2f%n", hargaDiskon);
                System.out.printf("Pajak (3%%) %10.2f%n", pajak);
                System.out.printf("Pengiriman %10.2f%n", (double) transaksi.getBiayaOngkir());
                System.out.println("---------------------------------");
                System.out.printf("Total      %10.2f%n", total);
            }
        }

        if (!adaTransaksi) {
            System.out.println("=================================");
            System.out.println("Laporan pengeluaran masih kosong!");
            System.out.println("=================================\n");
        } else {
            System.out.println("=================================");
            System.out.printf("Total Keseluruhan: %.2f%n", totalPengeluaran);
            System.out.println();
        }
    }

    /**
     * Informasi yang akan ditampilkan meliputi Id transaksi, jumlah pendapatan,
     * timestamp, dan
     * keterangan seluruh transaksi-transaksi yang dibuat oleh Pembeli yang logged
     * in saat ini.
     */
    public void handleRiwayatTransaksi() {
        // Ambil daftar transaksi dari TransaksiRepository
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();

        // Filter transaksi berdasarkan nama pembeli
        boolean adaTransaksi = false;
        int transaksiCount = 0;

        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePembeli().equals(activePembeli.getUsername())) {
                adaTransaksi = true;

                if (transaksiCount == 0) {
                    // Tampilkan header jika ada transaksi
                    System.out.println("===================== RIWAYAT TRANSAKSI =====================");
                    System.out.printf("%-15s %-15s %-10s %-20s%n", "ID Transaksi", "Tanggal", "Nominal", "Keterangan");
                    System.out.println("------------------------------------------------------------");
                }

                // Supaya tanggalnya sesuai format Indonesia
                String tanggal = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("dd MMMM yyyy", new Locale("id", "ID")));

                // Hitung total nominal transaksi
                double subtotal = 0;
                for (TransactionProduct produkTransaksi : transaksi.getProdukDibeli()) {
                    Product product = null;

                    // Cari produk di semua penjual
                    List<User> userList = mainRepository.getUserRepo().getAll();
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

                double hargaDiskon = 0;
                if (transaksi.getIdDiskon() != null) {
                    Voucher voucher = mainRepository.getVoucherRepo().getById(transaksi.getIdDiskon());
                    if (voucher != null) {
                        int persenDiskon = voucher.calculateDisc();
                        hargaDiskon = subtotal * persenDiskon / 100.0;
                    } else {
                        Promo promo = mainRepository.getPromoRepo().getById(transaksi.getIdDiskon());
                        if (promo != null) {
                            int persenDiskon = promo.calculateDisc();
                            hargaDiskon = subtotal * persenDiskon / 100.0;
                        }
                    }
                }

                double pajak = subtotal * 0.03;
                double total = subtotal - hargaDiskon + pajak + transaksi.getBiayaOngkir();

                // Tampilkan informasi transaksi
                System.out.printf("%-15s %-15s - %-10.2f %-20s%n", transaksi.getId(), tanggal, total,
                        "Pembelian produk");

                transaksiCount++;
            }
        }

        if (!adaTransaksi) {
            // Jika tidak ada transaksi
            System.out.println("======= RIWAYAT TRANSAKSI =======");
            System.out.println("Riwayat transaksi masih kosong!");
            System.out.println("=================================\n");
        } else {
            // Jika ada transaksi
            System.out.println("============================================================\n");
        }
    }
}