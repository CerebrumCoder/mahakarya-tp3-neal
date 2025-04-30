package System;
import java.util.Random;
import java.util.Scanner;

import Models.Admin;
import Main.Burhanpedia;

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
        System.out.println("Voucher berlaku hingga : ");

        System.out.println("Voucher berhasil dibuat : ");
    }

    public void handleGeneratePromo() {
        System.out.println("Generate Promo dipilih. Implementasi di sini.");
        // Implementasi untuk generate promo
    }

    public void handleLihatVoucher() {
        // Implementasi untuk melihat semua voucher
        System.out.println("=================================");
        System.out.println("Belum ada voucher yang dibuat!");
        System.out.println("=================================\n");

    }

    public void handleLihatPromo() {
        // Implementasi untuk melihat semua promo
        System.out.println("=================================");
        System.out.println("Belum ada promo yang dibuat!");
        System.out.println("=================================\n");
    }

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

    // Method untuk convert to numeric version using the formula
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