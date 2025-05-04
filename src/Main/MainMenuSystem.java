package Main;
import java.util.Arrays;
import java.util.Scanner;

import Models.Admin;
import Models.Pembeli;
import Models.Pengirim;
import Models.Penjual;
import Models.User;
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
            // try dan catch untuk antisipasi input bukan merupakan integer
            try {
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
                    default -> System.out.println("Pilihan tidak valid. Input angka dari 1 hingga 4 saja.");
                }
            } catch (Exception e) {
                System.out.println("Input bukan integer. Input angka dari 1 hingga 4 saja.");
                input.nextLine(); // Untuk membersihkan input buffer
            }

        }
    }

    public void handleLogin() {
        // Implementasi mekanisme login dengan overlapping role
        System.out.println("\n===== LOGIN =====");
        String username;
        String password;

        // Input username dan password untuk login
        System.out.print("Masukkan username: ");
        username = input.next();
        System.out.print("Masukkan password: ");
        password = input.next();

        // Cek apakah username ada di repository
        User existingUser = mainRepository.getUserRepo().getUserByName(username);
        Admin existingAdmin = mainRepository.getAdminRepo().getUserByName(username);

        // Switch apakah ini Admin atau User. Dibuat terpisah karena Admin tidak memiliki parent User
        if (existingAdmin != null) {
            // Cek apakah admin atau bukan
            if (existingAdmin.getUsername().equals(username) && existingAdmin.getPassword().equals(password)) {
                System.out.println("Login berhasil! Selamat datang, " + username + "!\n");
                systemAdmin.handleMenu(); //Menu Admin
            }
        } else {
            // Cek jika existingUser null
            if (existingUser == null) {
                System.out.println("Username tidak ditemukan!");
                return;
            }

            // Validasi password
            if (!existingUser.getPassword().equals(password)) {
                System.out.println("Password salah!");
                return;
            }

            // Tampilkan opsi login berdasarkan role yang dimiliki
            String[] roles = mainRepository.getUserRepo().getUserRoles(username);
            // Berjalan loop
            while (true) {
                System.out.println("\nPilih opsi login:\n1. Penjual\n2. Pembeli\n3. Pengirim\n4. Cek Saldo Antar Role\n5. Batal Login\n");

                System.out.print("Perintah: ");
                int roleChoice = input.nextInt();

                // Handle pilihan login
                switch (roleChoice) {
                    case 1 -> {
                        if (Arrays.asList(roles).contains("Penjual")) {
                            User penjualUser = mainRepository.getUserRepo().getUserByNameAndRole(username, "Penjual");
                            if (penjualUser instanceof Penjual penjual) {
                                System.out.println("Login berhasil! Selamat datang, " + username + "!\n");
                                systemPenjual.setActivePenjual(username); // Pass data username ke systemPenjual untuk bisa akses produkRepo
                                systemPenjual.handleMenu(); //Menu Penjual
                                return; // Ketika systemPenjual input angka 9, maka dia akan membawa balik ke menu utama
                            }

                        } else {
                            System.out.println("Username " + username + " tidak memiliki role penjual!");
                        }
                    }
                    case 2 -> {
                        if (Arrays.asList(roles).contains("Pembeli")) {
                            User pembeliUser = mainRepository.getUserRepo().getUserByNameAndRole(username, "Pembeli");
                            if (pembeliUser instanceof Pembeli pembeli) {
                                System.out.println("Login berhasil! Selamat datang, " + username + "!\n");
                                systemPembeli.setActivePembeli(username); // Pass data username ke systemPenjual untuk bisa akses produkRepo
                                systemPembeli.handleMenu(); //Menu Pembeli
                                return; // Ketika systemPembeli input angka 9, maka dia akan membawa balik ke menu utama
                            }
                        } else {
                            System.out.println("Username " + username + " tidak memiliki role pembeli!");
                        }
                    }
                    case 3 -> {
                        if (Arrays.asList(roles).contains("Pengirim")) {
                            User pengirimUser = mainRepository.getUserRepo().getUserByNameAndRole(username, "Pengirim");
                            if (pengirimUser instanceof Pengirim pengirim) {
                                System.out.println("Login berhasil! Selamat datang, " + username + "!\n");
                                systemPengirim.handleMenu(); //Menu Pengirim
                                return; // Ketika systemPengirim input angka 9, maka dia akan membawa balik ke menu utama
                            }

                        } else {
                            System.out.println("Username " + username + " tidak memiliki role pengirim!");
                        }
                    }
                    case 4 -> {
                        handleCekSaldoAntarAkun(username);
                    }
                    case 5 -> {
                        System.out.println("Login dibatalkan, kembali ke menu utama...");
                        return; // Keluar dari opsi logi dan kembali ke menu utama
                    }
                    default -> System.out.println("Pilihan tidak valid. Input bukan angka atau bukan angka dari 1 hingga 5!");

                }
            }
        }
    }

    public void handleRegister() {
        // Implementasi try dan catch, untuk materi DDP 2: Exception dan catch
        try {
            // Implementasi mekanisme register dengan overlapping role
            System.out.println("\n===== REGISTRASI =====");

            String username;
            String password;

            System.out.print("Masukkan username: ");
            username = input.next();

            // Untuk cek apakah class User dengan username sudah ada atau belum di Repository.
            User existingUser = mainRepository.getUserRepo().getUserByName(username);

            if (existingUser != null) {
                // Gunakan getUserRoles untuk mendapatkan semua role yang dimiliki user
                String[] roles = mainRepository.getUserRepo().getUserRoles(username);

                boolean hasPembeli = Arrays.asList(roles).contains("Pembeli");
                boolean hasPenjual = Arrays.asList(roles).contains("Penjual");
                boolean hasPengirim = Arrays.asList(roles).contains("Pengirim");

                // Cek apakah User sudah memiliki semua role
                if (hasPembeli && hasPenjual && hasPengirim) {
                    System.out.println("Username sudah ada! Username " + username + " tidak dapat menambahkan role lagi karena sudah memiliki semua role, registrasi dibatalkan.");
                    return;
                }

                // Konfirmasi password jika ingin menambahkan user baru
                System.out.println("Username sudah ada! Silahkan konfirmasi password untuk menambahkan role lain.");
                System.out.print("Masukkan password: ");
                password = input.next();
                // Kalo input passwordnya salah
                if (!existingUser.getPassword().equals(password)) {
                    System.out.println("Password salah! Registrasi dibatalkan.");
                    return;
                }
            } else {
                // Ini input password kalo User memang baru pertama kali ditambah
                System.out.print("Masukkan password: ");
                password = input.next();
            }

            // Berjalan loop
            boolean aktif = true;
            while (aktif) {
                // try dan catch untuk input yang bukan merupakan integer
                try {
                    System.out.println("\nPilih role:\n1. Penjual\n2. Pembeli\n3. Pengirim\n4. Batalkan register\n");
                    System.out.print("Perintah : ");
                    int roleChoice = input.nextInt();

                    // Penentuan roleChoice berdasarkan pilihan dari pengguna
                    // Penjual baru
                    if (roleChoice == 1) {
                        // Cek apakah existingUser null atau tidak dan juga cek apakah memiliki role "Penjual"
                        if (existingUser != null && Arrays.asList(mainRepository.getUserRepo().getUserRoles(username)).contains("Penjual")) {
                            System.out.println("Role sudah ada. Silahkan pilih role lain!");
                        } else {
                            System.out.print("Masukkan nama toko: ");
                            String namaToko = input.next();

                            // Buat penjualBaru lalu ditambah addUser lewat mainRepository tambah kelasnya
                            // sesuai dia itu Pembeli/Penjual/Pengirim
                            Penjual penjualBaru = new Penjual(username, password, namaToko);
                            mainRepository.getUserRepo().addUser(penjualBaru);

                            // Konfirmasi registrasi akun penjual berhasil
                            System.out.println("Registrasi akun penjual berhasil!");
                            aktif = false;
                        }
                    }
                    // Pembeli baru
                    else if (roleChoice == 2) {
                        // Cek apakah existingUser null atau tidak dan juga cek apakah memiliki role "Pembeli"
                        if (existingUser != null && Arrays.asList(mainRepository.getUserRepo().getUserRoles(username)).contains("Pembeli")) {
                            System.out.println("Role sudah ada. Silahkan pilih role lain!");
                        } else {
                            // Buat pembeliBaru lalu ditambah addUser lewat mainRepository tambah kelasnya
                            // sesuai dia itu Pembeli/Penjual/Pengirim
                            Pembeli pembeliBaru = new Pembeli(username, password);
                            mainRepository.getUserRepo().addUser(pembeliBaru);
                            System.out.println("Registrasi akun pembeli berhasil!");
                            aktif = false;
                        }
                    }
                    // Pengirim baru
                    else if (roleChoice == 3) {
                        // Cek apakah existingUser null atau tidak dan juga cek apakah memiliki role "Pengirim"
                        if (existingUser != null && Arrays.asList(mainRepository.getUserRepo().getUserRoles(username)).contains("Pengirim")) {
                            System.out.println("Role sudah ada. Silahkan pilih role lain!");
                        } else {
                            // Buat pengirimBaru lalu ditambah addUser lewat mainRepository tambah kelasnya
                            Pengirim pengirimBaru = new Pengirim(username, password);
                            mainRepository.getUserRepo().addUser(pengirimBaru);
                            System.out.println("Registrasi akun pengirim berhasil!");
                            aktif = false;
                        }
                    }
                    // Batalkan register
                    else if (roleChoice == 4) {
                        System.out.println("Registrasi dibatalkan, kembali ke menu utama...");
                        aktif = false;
                    } else {
                        System.out.println("Pilihan tidak valid. Masukkan angka antara 1 hingga 4 saja.");
                    }

                    // Untuk debugging
                    System.out.println(mainRepository.getUserRepo().getAll());
                } catch (Exception e) {
                    System.out.println("Input bukan integer. Silahkan coba lagi.");
                    input.nextLine(); // Untuk membersihkan input buffer
                }

            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
        }
    }

    public void handleNextDay() {
        // Implementasi mekanisme untuk hari berikutnya
        System.out.println("Hari berikutnya dipilih. Implementasi di sini.");
    }

    public void handleCekSaldoAntarAkun(String username) {
        // Implementasi mekanisme cek saldo antar akun

        // Header tabel dengan format yang sama seperti data
        System.out.printf("%-10s | %s%n", "Role", "Saldo");
        System.out.println("=======================");

        // Ambil semua role yang dimiliki user dari mainRepository
        String[] roles = mainRepository.getUserRepo().getUserRoles(username);

        // Ambil user berdasarkan username
        User existingUser = mainRepository.getUserRepo().getUserByName(username);

        // Iterasi melalui setiap role dan tampilkan saldo
        for (String role : roles) {
            System.out.printf("%-10s | %.2f%n", role, (double) existingUser.getBalance());
        }
        System.out.println("=======================");
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