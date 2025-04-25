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
                === Menu Admin ===
                1. Generate Voucher
                2. Generate Promo
                3. Lihat Voucher
                4. Lihat Promo
                5. Logout
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
                    System.out.println("Logout berhasil.");
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
        System.out.println("Lihat Voucher dipilih. Implementasi di sini.");
        // Implementasi untuk melihat semua voucher
    }

    public void handleLihatPromo() {
        System.out.println("Lihat Promo dipilih. Implementasi di sini.");
        // Implementasi untuk melihat semua promo
    }
}
