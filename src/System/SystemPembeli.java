package System;

import java.util.Scanner;

import Models.Pembeli;
import Main.Burhanpedia;
import Models.Penjual;

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

        if (getPembeli != null && getPembeli.getRole().equals("Penjual")) {
            this.activePembeli = getPembeli;
        } else {
            System.out.println("Pembeli dengan username " + username + " tidak ditemukan atau bukan penjual.");
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
        System.out.printf("Saldo berhasil ditambah! Saldo saat ini: %.2f%n", (double) activePembeli.getBalance());

    }

    public void handleCekDaftarBarang() {
        System.out.println("Cek Daftar Barang dipilih. Implementasi di sini.");
        // Implementasi untuk cek daftar barang
    }

    public void handleTambahKeKeranjang() {
        System.out.println("Tambah Barang ke Keranjang dipilih. Implementasi di sini.");
        // Implementasi untuk menambah barang ke keranjang
    }

    public void handleCheckout() {
        System.out.println("Checkout Keranjang dipilih. Implementasi di sini.");
        // Implementasi untuk checkout keranjang
    }

    public void handleLacakBarang() {
        System.out.println("Lacak Barang dipilih. Implementasi di sini.");
        // Implementasi untuk melacak barang
    }

    public void handleLaporanPengeluaran() {
        System.out.println("Lihat Laporan Pengeluaran dipilih. Implementasi di sini.");
        // Implementasi untuk melihat laporan pengeluaran
    }

    /**Informasi yang akan ditampilkan meliputi Id transaksi, jumlah pendapatan, timestamp, dan
     * keterangan seluruh transaksi-transaksi yang dibuat oleh Pembeli yang logged in saat ini.*/
    public void handleRiwayatTransaksi() {
        System.out.println("Lihat Riwayat Transaksi dipilih. Implementasi di sini.");
        // Implementasi untuk melihat riwayat transaksi
    }
}