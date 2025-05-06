package System;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Models.*;
import Main.Burhanpedia;

public class SystemPenjual implements SystemMenu {
    private Penjual activePenjual; // Penjual yang sedang login
    private Scanner input; // Scanner untuk input
    private Burhanpedia mainRepository; // Akses ke database program

    public SystemPenjual(Burhanpedia mainRepository) {
        this.mainRepository = mainRepository;
        this.input = new Scanner(System.in);
    }

    @Override
    public String showMenu() {
        return """
                ===== MENU PENJUAL =====
                1. Cek Produk
                2. Tambah Produk
                3. Tambah Stok
                4. Ubah Harga Barang
                5. Kirim Barang
                6. Lihat Laporan Pendapatan
                7. Cek Saldo
                8. Lihat Riwayat Transaksi
                9. Kembali ke Menu Utama
                """;
    }

    @Override
    public void handleMenu() {
        while (true) {
            // try dan catch untuk antisipasi input bukan merupakan integer
            System.out.println(showMenu());
            System.out.print("Perintah : ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> handleCekProduk();
                case 2 -> handleTambahProduk();
                case 3 -> handleTambahStok();
                case 4 -> handleUbahHarga();
                case 5 -> handleKirimBarang();
                case 6 -> handleLaporanPendapatan();
                case 7 -> handleCekSaldo();
                case 8 -> handleRiwayatTransaksi();
                case 9 -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }


        }
    }
    /**
     * Ini berfungsi untuk set activePenjual di file SystemPenjual.java terdefinisi.
     * Caranya kita panggil method di bawah ini di MainMenuSystem.java ketika mau add User Penjual.
     * Lalu datanya di pass lewat parameter terus di definisikan di this.activePenjual */
    public void setActivePenjual(String username) {
        // Ambil penjual dari repository berdasarkan username.
        // Menggunakan instance of supaya melakukan casting class dengan mudah dan tidak terjadi error
        User user = mainRepository.getUserRepo().getUserByNameAndRole(username, "Penjual");

        // Debugging untuk memastikan tipe objek
        // System.out.println(user != null ? user.getClass().getSimpleName() : "User tidak ditemukan");

        // Periksa apakah User adalah instance dari Penjual
        if (user instanceof Penjual penjual) {
            this.activePenjual = penjual;
        } else {
            System.out.println("Penjual dengan username " + username + " tidak ditemukan atau bukan penjual");
        }
    }

    public void handleCekProduk() {
        // Implementasi untuk cekProduk

        // Ambil semua productList di dalam ProductRepository.java
        List<Product> productList = activePenjual.getProductRepo().getProductList();

        // Jika productList kosong
        if (productList.isEmpty()) {
            System.out.println("=================================");
            System.out.println("Toko belum memiliki produk!");
            System.out.println("=================================\n");
        } else {
            System.out.println("=================================");
            for (Product product : productList) {
                System.out.printf("%-10s %10.2f %5d%n", product.getProductName(), (double) product.getProductPrice(), product.getProductStock());
            }
            System.out.println("=================================\n");
        }
    }

    public void handleTambahProduk() {
        // Implementasi try catch dan while loop untuk antisipasi kesalahan input
        while (true) {
            try {
                String namaProduk;
                int stokProduk;
                long price;

                // User memasukkan nama produk, jumlah stok, harga produk.
                System.out.print("Masukkan nama produk: ");

                input.nextLine(); // Bersihkan input buffer setelah next()
                namaProduk = input.nextLine();

                // User memasukkan stok produk
                System.out.print("Masukkan stok produk: ");
                if (!input.hasNextInt()) {
                    throw new InputMismatchException("Stok produk harus berupa angka");
                }
                stokProduk = input.nextInt();

                // User memasukkan harga produk
                System.out.print("Masukkan harga produk: ");
                if (!input.hasNextLong()) {
                    throw new InputMismatchException("Harga produk harus berupa angka");
                }
                price = input.nextLong();

                // Setelah mendapat datanya ditambah sebagai list baru di dalam produkRepo
                Product produkBaru = new Product(namaProduk, stokProduk, price);
                activePenjual.getProductRepo().addProduct(produkBaru);
                System.out.println("Berhasil menambahkan produk baru!\n");

                // Debugging
                // System.out.println(activePenjual.getProductRepo().getProductList());

                // Keluar dari loop
                break;
            } catch (java.util.InputMismatchException e) {
                System.out.println("Input tidak valid. Pastikan memasukkan angka untuk stok dan harga produk.");
                input.nextLine(); // Membersihkan input buffer
            }
        }


    }

    public void handleTambahStok() {
        // User memasukkan namaBarang dan tambahStok dalam produk.
        System.out.print("Masukkan nama barang: ");
        String namaBarang = input.next();
        System.out.print("Masukkan jumlah stok yang ingin ditambah: ");
        int tambahStok = input.nextInt();

        // Ambil semua productList di dalam ProductRepository.java
        List<Product> productList = activePenjual.getProductRepo().getProductList();

        // Cari produk berdasarkan nama
        Product getProduct = null;
        for (Product product : productList) {
            if (product.getProductName().equalsIgnoreCase(namaBarang)) {
                getProduct = product;
                break;
            }
        }

        if (getProduct != null) {
            // setProductStock() hanya menset aja. Makanya di dalam parameter ada getProductStock() + tambahStok
            getProduct.setProductStock(getProduct.getProductStock() + tambahStok);
            System.out.println("Stok " + getProduct.getProductName().toLowerCase() + " berhasil ditambah!" + " Stok saat ini: " + getProduct.getProductStock() + "\n");
        } else {
            System.out.println("Stok " + namaBarang + " tidak ditemukan!");
        }

    }

    public void handleUbahHarga() {
        // Implementasi untuk ubah harga barang

        // User memasukkan namaBarang dalam produk.
        System.out.print("Masukkan nama barang: ");
        String namaBarang = input.next();
        System.out.print("Masukkan harga barang yang baru: ");
        long price = input.nextInt();

        // Ambil semua productList di dalam ProductRepository.java
        List<Product> productList = activePenjual.getProductRepo().getProductList();

        // Cari produk berdasarkan nama
        Product getProduct = null;
        for (Product product : productList) {
            if (product.getProductName().equalsIgnoreCase(namaBarang)) {
                getProduct = product;
                break;
            }
        }

        if (getProduct != null) {
            getProduct.setProductPrice(price);
            System.out.printf("Harga %s diperbarui: %.2f", getProduct.getProductName().toLowerCase(), (double) getProduct.getProductPrice());
            System.out.println("\n");
        } else {
            System.out.println("Harga " + namaBarang + " tidak ditemukan!");
        }

    }

    public void handleKirimBarang() {
        // Menampilkan semua transaksi yang menunggu pengiriman
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        boolean adaPesanan = false;

        System.out.println("=================================");
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePenjual().equals(activePenjual.getUsername()) &&
                transaksi.getNamePengirim() == null) { // Pesanan belum diambil oleh pengirim
                adaPesanan = true;
                System.out.printf("ID Transaksi: %s%n", transaksi.getId());
                System.out.printf("Tanggal: %s%n", transaksi.getHistoryStatus().get(0).getTimestamp());
                System.out.printf("Status: %s%n", transaksi.getCurrentStatus());
                System.out.println("---------------------------------");
            }
        }

        if (!adaPesanan) {
            System.out.println("Tidak ada barang yang bisa dikirim!");
        }
        System.out.println("=================================\n");
    }

    public void handleLaporanPendapatan() {
        // Implementasi untuk melihat laporan pendapatan
        // Ambil semua productList di dalam ProductRepository.java
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();

        // Filter transaksi berdasarkan nama penjual
        boolean adaPendapatan = false;
        for (Transaksi transaksi: transaksiList) {
            if (transaksi.getNamePenjual().equals(activePenjual.getUsername())) {
                adaPendapatan = true;
                break;
            }
        }

        // Cek apakah ada pendapatan atau tidak dengan bantuan boolean
        if (!adaPendapatan) {
            System.out.println("=================================");
            System.out.println("Laporan pendapatan masih kosong!");
            System.out.println("=================================\n");
        } else {
            System.out.println("Laporan pendapatan tersedia. Implementasi detail laporan di sini.");
        }
    }

    public void handleCekSaldo() {
        // Implementasi untuk cek saldo penjual
        System.out.println("=================================");
        System.out.printf("Saldo saat ini: %.2f%n", (double) activePenjual.getBalance());
        System.out.println("=================================\n");
    }

    /**Informasi yang akan ditampilkan meliputi Id transaksi, jumlah pendapatan, timestamp, dan
     * keterangan seluruh transaksi-transaksi yang dibuat oleh Penjual yang logged in saat ini.*/
    public void handleRiwayatTransaksi() {
        // Implementasi untuk melihat riwayat transaksi
        // Ambil daftar transaksi dari repository
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();

        // Filter transaksi berdasarkan nama penjual
        boolean adaTransaksi = false;
        for (Transaksi transaksi: transaksiList) {
            if (transaksi.getNamePenjual().equals(activePenjual.getUsername())) {
                adaTransaksi = true;
                break;
            }
        }

        // Cek apakah ada transaksi atau tidak dengan bantuan boolean
        if (!adaTransaksi) {
            System.out.println("=================================");
            System.out.println("Riwayat transaksi masih kosong!");
            System.out.println("=================================\n");
        } else {
            System.out.println("Riwayat transaksi tersedia. Implementasi detail riwayat di sini.");
        }

    }
}