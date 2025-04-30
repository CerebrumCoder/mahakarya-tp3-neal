package System;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import Models.Admin;
import Main.Burhanpedia;
import Models.Voucher;

public class SystemAdmin implements SystemMenu {
    private Admin activeAdmin; // Admin yang sedang login
    private Scanner input; // Scanner untuk input
    private Burhanpedia mainRepository; // Akses ke database program

    public SystemAdmin(Burhanpedia mainRepository) {
        this.mainRepository = mainRepository;
        this.input = new Scanner(System.in);
    }

    @Override
    public String showMenu() {
        return """
                ===== MENU ADMIN =====
                1. Generate Voucher
                2. Generate Promo
                3. Lihat Voucher
                4. Lihat Promo
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
                case 1 -> handleGenerateVoucher();
                case 2 -> handleGeneratePromo();
                case 3 -> handleLihatVoucher();
                case 4 -> handleLihatPromo();
                case 5 -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public void handleGenerateVoucher() {
        // Implementasi untuk generate voucher
        System.out.print("Voucher berlaku hingga : ");
        String expiryDateInput = input.next();
        try {
            // Parse input tanggal menjadi objek Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date expiryDate = dateFormat.parse(expiryDateInput);

            // Generate kode voucher
            String voucherCode = generateCode();
            String numericCode = convertToNumericCode(voucherCode);

            // Tambahkan voucher ke repository
            mainRepository.getVoucherRepo().generate(numericCode, 1, expiryDate);

            // Tampilkan hasil
            System.out.println("\nVoucher berhasil dibuat: " + numericCode + "\n");
        } catch (ParseException e) {
            System.out.println("Format tanggal tidak valid. Gunakan format yyyy-MM-dd");
        }
    }

    public void handleGeneratePromo() {
        // Implementasi untuk generate promo
        System.out.print("Promo berlaku hingga : ");
        String expiryDateInput = input.next();

        // Panggil metode generate di PromoRepository
        mainRepository.getPromoRepo().generate(expiryDateInput);
    }

    public void handleLihatVoucher() {
        // Implementasi untuk melihat semua voucher

        // Ambil semua voucherList di dalam VoucherRepository.java
        List<Voucher> voucherList = mainRepository.getVoucherRepo().getAll();

        // Jika voucherList kosong
        if (voucherList.isEmpty()) {
            System.out.println("=================================");
            System.out.println("Belum ada voucher yang dibuat!");
            System.out.println("=================================\n");
        } else {
            while (true) {
                System.out.println("===== MENU LIHAT VOUCHER =====\n1. Lihat Semua\n2. Lihat Berdasarkan ID\n3. Kembali\n");
                System.out.print("Perintah: ");
                int roleChoice = input.nextInt();

                switch (roleChoice) {
                    case 1 -> {
                        System.out.println("=================================");
                        for (Voucher voucher : voucherList) {
                            // Format tanggal output dd/MM/yyyy
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String formattedDate = dateFormat.format(voucher.getBerlakuHingga());

                            System.out.println(voucher.getId() + " [Dapat digunakan " + voucher.getSisaPemakaian() + " kali]" + " [Sampai dengan " + formattedDate + "]");
                        }
                        System.out.println("=================================\n");
                    }
                    case 2 -> {
                        System.out.print("Masukkan id Voucher: ");
                        String id = input.next();
                        Voucher voucher = mainRepository.getVoucherRepo().getById(id);
                        if (voucher == null) {
                            System.out.println("\n=================================");
                            System.out.println("Tidak ada Voucher dengan id " + id);
                            System.out.println("=================================\n");
                        } else {
                            // Format tanggal output dd/MM/yyyy
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            String formattedDate = dateFormat.format(voucher.getBerlakuHingga());

                            System.out.println("\n=================================");
                            System.out.println(voucher.getId() + " [Dapat digunakan " + voucher.getSisaPemakaian() + " kali]" + " [Sampai dengan " + formattedDate + "]");
                            System.out.println("=================================\n");
                        }
                    }
                    case 3 -> {
                        System.out.print("\n");
                        return;
                    }
                    default -> System.out.println("Pilihan tidak valid. Pastikan pilihan antara angka 1 hingga 3.");
                }
            }
        }
    }

    public void handleLihatPromo() {
        // Implementasi untuk melihat semua promo
        System.out.println("=================================");
        System.out.println("Belum ada promo yang dibuat!");
        System.out.println("=================================\n");
    }

    // ===== Untuk Generate Voucher =====
    // Method untuk generateVoucherCode. Sesuai dengan TP 2. private, karena hanya untuk SystemAdmin.java
    private static String generateCode() {
        Random random = new Random();
        String code = "";

        for (int i = 0; i < 10; i++) {
            char randomChar = (char) ('A' + random.nextInt(26));
            code += randomChar;
        }
        return code;
    }

    // Method untuk convert to numeric version using the formula. Sesuai dengan TP 2. private, karena hanya untuk SystemAdmin.java
    private static String convertToNumericCode(String voucherCode) {
        String numericCode = "";

        for (int i = 0; i < voucherCode.length(); i++) {
            // Ubah jadi Code 93
            char character = voucherCode.charAt(i);
            // Formula: (ASCII - 65)/Code 93 * index mod 10
            int numericValue = ((character - 'A') * i) % 10;
            numericCode += numericValue;
        }

        return numericCode;
    }
}