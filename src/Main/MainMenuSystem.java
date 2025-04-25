package Main;
import java.util.Scanner;

import Models.Penjual;
import System.SystemAdmin;
import System.SystemMenu;
import System.SystemPembeli;
import System.SystemPengirim;
import System.SystemPenjual;

public class MainMenuSystem implements SystemMenu {
    private Scanner input;
    private SystemPembeli systemPembeli; // Menu untuk role Pembeli
    private SystemPenjual systemPenjual; // Menu untuk role Penjual
    private SystemPengirim systemPengirim; // Menu untuk role Pengirim
    private SystemAdmin systemAdmin; // Menu untuk role Admin
    private static Burhanpedia mainRepository; // Akses ke database program

    public MainMenuSystem(Burhanpedia mainRepository) {
        this.mainRepository = mainRepository;
        this.systemPembeli = new SystemPembeli(mainRepository);
        this.systemPenjual = new SystemPenjual(mainRepository);
        this.systemPengirim = new SystemPengirim(mainRepository);
        this.systemAdmin = new SystemAdmin(mainRepository);
    }

    @Override
    public String showMenu() {

        return """
                Pilih menu
                1. Login
                2. Register
                3. Hari Selanjutnya
                4. Keluar
                """;
    }

    @Override
    public void handleMenu() {
        while (true) {
            System.out.print("\n");
            System.out.println(showMenu());
            System.out.print("Pilih menu: ");
            input = new java.util.Scanner(System.in);
            int choice = input.nextInt();
            switch (choice) {
                case 1 -> handleLogin();
                case 2 -> handleRegister();
                case 3 -> handleNextDay();
                case 4 -> {
                    return;
                }
                default -> System.out.println("Pilihan tidak valid.");
            }
        }

    }

    public void handleLogin() {
        // Implementasi mekanisme login dengan overlapping role
        System.out.println("Login dipilih. Implementasi login di sini.");

    }

    public void handleRegister() {
        // Implementasi mekanisme register dengan overlapping role
        System.out.println("\n===== REGISTRASI =====");

        String username;
        System.out.print("Masukkan username: ");
        username = input.next();
        if (mainRepository.getUserRepo().getUserByName(username) != null) {
            System.out.println("Username sudah ada! Silahkan konfirmasi password untuk menambahkan role lain.");
        }

        System.out.print("Masukkan password: ");
        String password = input.next();

        System.out.println("\nPilih role:\n1. Penjual\n2. Pembeli\n3. Pengirim\n4. Batalkan register\n");
        System.out.print("Perintah : ");
        int roleChoice = input.nextInt();

        // Penjual
        if (roleChoice == 1) {
            System.out.print("Masukkan nama toko: ");
            String namaToko = input.next();
            // Buat penjualBaru lalu ditambah addUser lewat mainRepository tambah kelasnya
            // sesuai dia itu Pembeli/Penjual/Pengirim

            if (!mainRepository.getUserRepo().getUserRoles(username).equals(username)) {
                Penjual penjualBaru = new Penjual(username, password, namaToko);
                mainRepository.getUserRepo().addUser(penjualBaru);

                System.out.println("Registrasi akun penjual berhasil!");
            }
            System.out.println("Role sudah ada. Silahkan pilih role lain!");

        } else {
            System.out.println("Pilihan tidak valid!");
        }

    }

    public void handleNextDay() {
        // Implementasi mekanisme untuk hari berikutnya
        System.out.println("Hari berikutnya dipilih. Implementasi di sini.");
    }

    public void handleCekSaldoAntarAkun(String command) {
        // Implementasi mekanisme cek saldo antar akun
        System.out.println(command + " dipilih. Implementasi di sini.");
    }

    public static void main(String[] args) {
        System.out.println("=============================================================");
        System.out.println("\n  ____             _                 _____         _ _       \r\n"
                + " |  _ \\           | |               |  __ \\       | (_)      \r\n"
                + " | |_) |_   _ _ __| |__   __ _ _ __ | |__) |__  __| |_  __ _ \r\n"
                + " |  _ <| | | | '__| '_ \\ / _` | '_ \\|  ___/ _ \\/ _` | |/ _` |\r\n"
                + " | |_) | |_| | |  | | | | (_| | | | | |  |  __/ (_| | | (_| |\r\n"
                + " |____/ \\__,_|_|  |_| |_|\\__,_|_| |_|_|   \\___|\\__,_|_|\\__,_|\r\n"
                + "                                                             \r\n"
                + "                                                             ");
        System.out.println("=============================================================");
        System.out.println("============== Selamat datang di Burhanpedia! ===============");
        System.out.println("=============================================================");
        // Initialize the mainRepository before passing it to MainMenuSystem
        mainRepository = new Burhanpedia(); // Assuming Burhanpedia has a default constructor
        MainMenuSystem systemMenu = new MainMenuSystem(mainRepository);
        systemMenu.handleMenu();

    }
}