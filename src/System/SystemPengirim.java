package System;

import java.util.Scanner;

import Models.Pengirim;
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
                ===== Menu Pengirim =====
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
                    System.out.println("Logout berhasil.");
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public void handleFindJob() {
        System.out.println("Find Job dipilih. Implementasi di sini.");
        // Implementasi untuk menampilkan semua transaksi yang tersedia untuk pengiriman
    }

    public void handleTakeJob() {
        System.out.println("Take Job dipilih. Implementasi di sini.");
        // Implementasi untuk mengambil transaksi pengiriman berdasarkan ID
    }

    public void handleConfirmJob() {
        System.out.println("Confirm Job dipilih. Implementasi di sini.");
        // Implementasi untuk mengonfirmasi bahwa barang sedang dikirim
    }

    public void handleRiwayatTransaksi() {
        System.out.println("Lihat Riwayat Transaksi dipilih. Implementasi di sini.");
        // Implementasi untuk melihat riwayat transaksi pengiriman
    }
}