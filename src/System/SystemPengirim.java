package System;

import java.util.Scanner;
import java.util.List;

import Models.Pengirim;
import Models.Penjual;
import Models.Transaksi;
import Models.User;
import System.TransactionStatus;
import Main.Burhanpedia;

public class SystemPengirim implements SystemMenu {
    private Pengirim activePengirim; // Pengirim yang sedang login
    private Scanner input; // Scanner untuk input
    private Burhanpedia mainRepository; // Akses ke database program

    public SystemPengirim(Burhanpedia mainRepository) {
        this.mainRepository = mainRepository;
        this.input = new Scanner(System.in);
    }

    @Override
    public String showMenu() {
        return """
                ===== MENU PENGIRIM =====
                1. Find Job
                2. Take Job
                3. Confirm Job
                4. Lihat Riwayat Transaksi
                5. Kembali ke menu utama
                """;
    }

    @Override
    public void handleMenu() {
        while (true) {
            System.out.println(showMenu());
            System.out.print("Pilih menu: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> handleFindJob();
                case 2 -> handleTakeJob();
                case 3 -> handleConfirmJob();
                case 4 -> handleRiwayatTransaksi();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    /**
     * Ini berfungsi untuk set activePenjual di file SystemPengirim.java terdefinisi.
     * Caranya kita panggil method di bawah ini di MainMenuSystem.java ketika mau add User Pengirim.
     * Lalu datanya di pass lewat parameter terus di definisikan di this.activePengirim */
    public void setActivePengirim(String username) {
        // Ambil penjual dari repository berdasarkan username.
        // Menggunakan instance of supaya melakukan casting class dengan mudah dan tidak terjadi error
        User user = mainRepository.getUserRepo().getUserByNameAndRole(username, "Pengirim");

        // Debugging untuk memastikan tipe objek
        // System.out.println(user != null ? user.getClass().getSimpleName() : "User tidak ditemukan");

        // Periksa apakah User adalah instance dari Penjual
        if (user instanceof Pengirim pengirim) {
            this.activePengirim = pengirim;
        } else {
            System.out.println("Pengirim dengan username " + username + " tidak ditemukan atau bukan pengirim");
        }
    }

    public void handleFindJob() {
        // Menampilkan semua transaksi yang belum diambil oleh pengirim
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        boolean adaPesanan = false;

        System.out.println("=================================");
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePengirim() == null) { // Pesanan belum diambil
                adaPesanan = true;
                System.out.println("Pesanan tersedia:");
                System.out.printf("ID: %s%n", transaksi.getId());
                System.out.printf("Pembeli: %s%n", transaksi.getNamePembeli());
                System.out.printf("Penjual: %s%n", transaksi.getNamePenjual());
                System.out.println("---------------------------------");
            }
        }

        if (!adaPesanan) {
            System.out.println("Tidak ada pesanan yang tersedia.");
        }
        System.out.println("=================================\n");
    }

    public void handleTakeJob() {
        System.out.print("Masukkan ID transaksi yang ingin diambil: ");
        input.nextLine(); // Bersihkan buffer input
        String idTransaksi = input.nextLine();

        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getId().equals(idTransaksi)) {
                if (transaksi.getNamePengirim() != null) {
                    System.out.println("Pesanan ini sudah diambil oleh pengirim lain.");
                    return;
                }

                // Set pengirim untuk transaksi
                transaksi.setNamePengirim(activePengirim.getUsername());
                transaksi.addStatus(new TransactionStatus(TransactionStatus.SEDANG_DIKIRIM));
                System.out.printf("Pesanan dengan ID %s berhasil diambil oleh %s.%n", transaksi.getId(), activePengirim.getUsername());
                return;
            }
        }

        System.out.println("Transaksi dengan ID tersebut tidak ditemukan.");
    }

    public void handleConfirmJob() {
        // Mengonfirmasi bahwa barang sedang dikirim
        System.out.print("Masukkan ID transaksi yang ingin dikonfirmasi: ");
        String idTransaksi = input.next();

        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getId().equals(idTransaksi) && transaksi.getNamePengirim().equals(activePengirim.getUsername())) {
                transaksi.addStatus(new TransactionStatus(TransactionStatus.SEDANG_DIKIRIM));
                System.out.println("Pesanan berhasil dikonfirmasi sedang dikirim.");
                return;
            }
        }

        System.out.println("Transaksi dengan ID tersebut tidak ditemukan atau bukan milik Anda.");
    }

    public void handleRiwayatTransaksi() {
        System.out.println("Lihat Riwayat Transaksi dipilih. Implementasi di sini.");
        // Implementasi untuk melihat riwayat transaksi pengiriman
    }
}