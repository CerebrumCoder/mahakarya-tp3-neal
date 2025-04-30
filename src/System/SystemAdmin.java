package System;
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
        System.out.println("Generate Voucher dipilih. Implementasi di sini.");
        // Implementasi untuk generate voucher
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
}