package System;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
        // Ambil pembeli dari repository berdasarkan username
        // Menggunakan instance of supaya melakukan casting class dengan mudah dan tidak terjadi error
        User user = mainRepository.getUserRepo().getUserByNameAndRole(username, "Pembeli");

        // Debugging untuk memastikan tipe objek
        System.out.println(user != null ? user.getClass().getSimpleName() : "User tidak ditemukan");

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
                        System.out.printf("%-10s %10.2f %5d%n", product.getProductName(), (double) product.getProductPrice(), product.getProductStock());
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
        input.nextLine(); // Membersihkan buffer sebelum membaca input
        String namaToko = input.nextLine(); // Gunakan nextLine() untuk membaca namaToko

        // Input nama barang
        System.out.print("Masukkan nama barang yang ingin dibeli: ");
        String namaBarang = input.nextLine(); // Gunakan nextLine() untuk membaca namaBarang

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

        // Cek apakah keranjang sudah memiliki barang dari toko lain
        List<CartProduct> keranjangList = activePembeli.getCart().getCartContent();
        if (!keranjangList.isEmpty()) {
            // Ambil semua toko dari produk di keranjang
            // Awalnya ini pakai HashSet untuk menghindari duplikasi, cuman pakai ArrayList juga bisa tapi perlu
            // dibuat if elsenya dulu untuk menghindari duplikat.
            List<String> tokoKeranjang = new ArrayList<>();

            for (CartProduct cartProduct : keranjangList) {
                for (User user : userList) {
                    if (user instanceof Penjual penjual) {
                        Product product = penjual.getProductRepo().getProductById(cartProduct.getProductId());
                        if (product != null) {
                            if (!tokoKeranjang.contains(penjual.getProductRepo().getNamaToko())) {
                                tokoKeranjang.add(penjual.getProductRepo().getNamaToko());
                            }
                            break;
                        }
                    }
                }
            }


            // Jika toko berbeda, tanyakan kepada pengguna
            if (!tokoKeranjang.isEmpty() && !tokoKeranjang.contains(namaToko)) {
                System.out.println("Anda sudah memiliki barang di keranjang yang berasal dari toko berbeda.");
                System.out.print("Kosongkan keranjang dan masukkan barang yang baru? (Y/N): ");
                input.nextLine(); // Membersihkan buffer dulu
                String konfirmasi = input.nextLine();

                if (!konfirmasi.equalsIgnoreCase("Y")) {
                    System.out.println("Penambahan barang dibatalkan!");
                    return;
                }

                // Kosongkan keranjang
                keranjangList.clear();
            }

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
            System.out.printf("%-8s %10.2f %5d (%.2f)%n", product.getProductName(), (double) product.getProductPrice(), cartProduct.getProductAmount(), (double) totalHarga);
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
        double hargaDiskon = 0;
        double subtotalSetelahDiskon = 0;

        // Antisipasi kalo salah masukin voucher, jadinya dijadiin while loop
        while (true) {
            if (!kodeVoucher.equalsIgnoreCase("skip")) {
                Voucher voucher = mainRepository.getVoucherRepo().getById(kodeVoucher);
                if (voucher != null && voucher.isValid(new Date())) {
                    int persenDiskon = voucher.calculateDisc();
                    hargaDiskon = subtotal * persenDiskon / 100.0;
                    subtotalSetelahDiskon = subtotal - hargaDiskon;

                    // Kurangi sisa pemakaian Voucher (nanti ditambah)


                    System.out.println("Voucher diterapkan! Total harga setelah diskon: " + subtotalSetelahDiskon);
                    break;
                } else {
                    System.out.println("Voucher tidak valid atau sudah kadaluarsa!");
                }
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

            // Implementasi try catch
            default -> throw new IllegalStateException("Unexpected value: " + pilihanPengiriman);
        };

        // Hitung total akhir + pajak
        double pajak = subtotalSetelahDiskon * 0.03;

        // Ini untuk output nanti di "Saldo saat in: "
        double totalAkhirTanpaPengiriman = subtotalSetelahDiskon - pajak;
        double totalAkhir = subtotalSetelahDiskon - pajak + biayaPengiriman;

        // Cek saldo pembeli
        if (activePembeli.getBalance() < totalAkhir) {
            System.out.println("Pembelian gagal. Saldo tidak cukup!");
            return;
        }

        activePembeli.setBalance((long) totalAkhir);
        System.out.printf("Pembelian sukses! Saldo saat ini: %.2f", totalAkhirTanpaPengiriman);
        System.out.println("\n");

        // Menambahkan transaksi ke TransaksiRepository.java
        // Ini Date Formatter untuk di TRX tanggal nanti dalam waktu Indonesia
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", new Locale("id", "ID"));
        String idTransaksi = "TRX" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%04d", mainRepository.getTransaksiRepo().getList().size() + 1);

        // List produkDibeli
        List<TransactionProduct> produkDibeli = new ArrayList<>();
        for (CartProduct cartProduct : keranjangList) {
            produkDibeli.add(new TransactionProduct(cartProduct.getProductId(), cartProduct.getProductAmount()));
        }
        Transaksi transaksiBaru = new Transaksi(
                idTransaksi,
                activePembeli.getUsername(),
                null, //nama penjual,
                null,
                kodeVoucher.equalsIgnoreCase("skip") ? null : kodeVoucher,
                produkDibeli,
                pilihanPengiriman == 1 ? "Instant" : pilihanPengiriman == 2 ? "Next Day" : "Regular"
        );

        // Tambah Transaksi ke dalam list transaksi
        mainRepository.getTransaksiRepo().addTransaksi(transaksiBaru);
    }

    public void handleLacakBarang() {
        System.out.println("Lacak Barang dipilih. Implementasi di sini.");
        // Implementasi untuk melacak barang
    }

    public void handleLaporanPengeluaran() {
        // Implementasi untuk melihat laporan pengeluaran
        // Ambil daftar transaksi dari TransaksiRepository
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();

        // Filter transaksi berdasarkan nama pembeli
        boolean adaTransaksi = false;
        double totalPengeluaran = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", new Locale("id", "ID"));

        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePembeli().equals(activePembeli.getUsername())) {
                adaTransaksi = true;

                // Tampilkan laporan pengeluaran
                System.out.println("\n===== LAPORAN PENGELUARAN =====");
                System.out.printf("ID Transaksi    %s%n", transaksi.getId());

                // Format tanggal transaksi menggunakan formatter dengan locale Indonesia
                String formattedDate = LocalDateTime.now().format(formatter);
                System.out.printf("Tanggal         %s%n", formattedDate);
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
                                (double) product.getProductPrice(), produkTransaksi.getProductAmount(), (double) totalHarga);
                    }
                }

                // Hitung diskon, pajak, dan total
                double hargaDiskon = 0;
                double subtotalSetelahDiskon = 0;
                if (transaksi.getIdDiskon() != null) {
                    Voucher voucher = mainRepository.getVoucherRepo().getById(transaksi.getIdDiskon());
                    if (voucher != null) {
                        int persenDiskon = voucher.calculateDisc();
                        hargaDiskon = subtotal * persenDiskon / 100.0;
                        subtotalSetelahDiskon = subtotal - hargaDiskon;
                    }
                }

                double pajak = subtotal * 0.03; // Pajak 3%
                double total = subtotalSetelahDiskon + pajak + transaksi.getBiayaOngkir();

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
            System.out.println("\n");
        }
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