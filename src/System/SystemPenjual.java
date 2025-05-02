package System;

import java.util.Scanner;

import Models.Pembeli;
import Models.Penjual;
import Main.Burhanpedia;
import Models.Product;

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
            System.out.println(showMenu());
            System.out.print("Pilih menu: ");
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

    public void handleCekProduk() {
        System.out.println("=================================");
        System.out.println("Toko belum memiliki produk!");
        System.out.println("=================================\n");
    }

    public void handleTambahProduk() {
        // User memasukkan nama produk, jumlah stok, harga produk.
        System.out.print("Masukkan nama produk: ");
        String namaProduk = input.next();
        System.out.print("Masukkan stok produk: ");
        int stokProduk = input.nextInt();
        System.out.print("Masukkan harga produk: ");
        long price = input.nextLong();

        // Setelah mendapat datanya ditambah sebagai list baru di dalam produkRepo
        Product produkBaru = new Product(namaProduk, stokProduk, price);
        activePenjual.getProductRepo().addProduct(produkBaru);

        // Debugging
        activePenjual.getProductRepo().getProductList();
    }

    public void handleTambahStok() {
        System.out.println("Tambah Stok dipilih. Implementasi di sini.");
        // Implementasi untuk tambah stok
    }

    public void handleUbahHarga() {
        System.out.println("Ubah Harga Barang dipilih. Implementasi di sini.");
        // Implementasi untuk ubah harga barang
    }

    public void handleKirimBarang() {
        System.out.println("Kirim Barang dipilih. Implementasi di sini.");
        // Implementasi untuk kirim barang
    }

    public void handleLaporanPendapatan() {
        System.out.println("Lihat Laporan Pendapatan dipilih. Implementasi di sini.");
        // Implementasi untuk melihat laporan pendapatan
    }

    public void handleCekSaldo() {
        System.out.println("Cek Saldo dipilih. Implementasi di sini.");
        // Implementasi untuk cek saldo penjual
    }

    /**Informasi yang akan ditampilkan meliputi Id transaksi, jumlah pendapatan, timestamp, dan
     * keterangan seluruh transaksi-transaksi yang dibuat oleh Penjual yang logged in saat ini.*/
    public void handleRiwayatTransaksi() {
        System.out.println("Lihat Riwayat Transaksi dipilih. Implementasi di sini.");
        // Implementasi untuk melihat riwayat transaksi
    }
}