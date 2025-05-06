package System;

import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

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
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        boolean adaPesanan = false;
        int transaksiCount = 0;

        System.out.println("=================================");
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePengirim() == null) { // Pesanan belum diambil
                adaPesanan = true;
                // Tambahkan garus pemisah jika ini bukan transaksi pertama
                if (transaksiCount > 0) {
                    System.out.println("---------------------------------");
                }

                System.out.println("Pesanan tersedia:");
                System.out.printf("ID: %s%n", transaksi.getId());
                System.out.printf("Pembeli: %s%n", transaksi.getNamePembeli());
                System.out.printf("Penjual: %s%n", transaksi.getNamaToko());

                // Garis "---" akan nambah seiringnya bertambah transaksi
                transaksiCount++;
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
                if (!transaksi.getCurrentStatus().equals(TransactionStatus.MENUNGGU_PENGIRIM)) {
                    System.out.println("Pesanan tidak dapat diambil. Status saat ini: " + transaksi.getCurrentStatus() + "\n");
                    return;
                }
                // Validasi apakah pesanan sudah melewati tanggal pengiriman
                if (transaksi.getCurrentStatus().equals(TransactionStatus.DIKEMBALIKAN)) {
                    System.out.println("Pesanan sudah melewati tanggal pengiriman!");
                    return;
                }
                
                // Set pengirim untuk transaksi
                transaksi.setNamePengirim(activePengirim.getUsername());
                transaksi.addStatus(new TransactionStatus(TransactionStatus.SEDANG_DIKIRIM));
                System.out.printf("Pesanan berhasil diambil oleh %s. Status diubah menjadi 'Sedang Dikirim'.%n", activePengirim.getUsername() + "\n");
                return;
            }
        }

        System.out.println("Tidak ada pesanan untuk ID tersebut.\n");
    }

    public void handleConfirmJob() {
        System.out.print("Masukkan ID transaksi yang ingin dikonfirmasi: ");
        String idTransaksi = input.next();

        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getId().equals(idTransaksi) && transaksi.getNamePengirim().equals(activePengirim.getUsername())) {
                if (!transaksi.getCurrentStatus().equals(TransactionStatus.SEDANG_DIKIRIM)) {
                    System.out.println("Pesanan tidak dapat dikonfirmasi. Status saat ini: " + transaksi.getCurrentStatus());
                    return;
                }

                // Ubah status menjadi "Pesanan Selesai"
                transaksi.addStatus(new TransactionStatus(TransactionStatus.PESANAN_SELESAI));
                System.out.println("Pesanan berhasil diselesaikan oleh " + activePengirim.getUsername() + ". Status diubah menjadi 'Pesanan Selesai'.");
                return;
            }
        }

        System.out.println("Tidak ada pesanan untuk ID tersebut.\n");
    }

    public void handleRiwayatTransaksi() {
        List<Transaksi> transaksiList = mainRepository.getTransaksiRepo().getList();
        boolean adaTransaksi = false;
        int transaksiCount = 0;

        System.out.println("=================================");
        for (Transaksi transaksi : transaksiList) {
            if (transaksi.getNamePengirim() != null && transaksi.getNamePengirim().equals(activePengirim.getUsername())) {
                adaTransaksi = true;

                if (transaksiCount > 0) {
                    System.out.println("---------------------------------");
                }
                // Supaya tanggalnya sesuai format Indonesia
                String tanggal;
                if (!transaksi.getHistoryStatus().isEmpty()) {
                    SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM yyyy", new Locale("id","ID"));
                    tanggal = formatter.format(transaksi.getHistoryStatus().get(0).getTimestamp());                    
                } else {
                    tanggal = "Tanggal tidak tersedia";                    
                }
                
                System.out.printf("ID Transaksi    %s%n", transaksi.getId());
                System.out.printf("Tanggal         %s%n", tanggal);
                System.out.printf("Pendapatan      %d%n", transaksi.getBiayaOngkir());

                // Transaksi bertambah, maka tanda "---" juga ngikutin banyaknya transaksi
                transaksiCount++;
            }
        }

        if (!adaTransaksi) {
            System.out.println("Tidak ada riwayat transaksi.");
        }
        System.out.println("=================================\n");
    }
}