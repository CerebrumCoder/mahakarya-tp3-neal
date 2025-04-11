public class MainMenuSystem implements SystemMenu {
    private SystemPembeli systemPembeli; // Menu untuk role Pembeli
    private SystemPenjual systemPenjual; // Menu untuk role Penjual
    private SystemPengirim systemPengirim; // Menu untuk role Pengirim
    private SystemAdmin systemAdmin; // Menu untuk role Admin
    private Burhanpedia mainRepository; // Akses ke database program

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
               === Main Menu ===
               1. Login
               2. Register
               3. Hari Berikutnya
               4. Cek Saldo Antar Akun
               5. Exit
               """;
    }

    @Override
    public void handleMenu() {
        System.out.println(showMenu());
        System.out.print("Pilih menu: ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> handleLogin();
            case 2 -> handleRegister();
            case 3 -> handleNextDay();
            case 4 -> handleCekSaldoAntarAkun("Cek Saldo");
            case 5 -> System.out.println("Keluar dari sistem.");
            default -> System.out.println("Pilihan tidak valid.");
        }
    }

    public void handleLogin() {
        // Implementasi mekanisme login dengan overlapping role
        System.out.println("Login dipilih. Implementasi login di sini.");
    }

    public void handleRegister() {
        // Implementasi mekanisme register dengan overlapping role
        System.out.println("Register dipilih. Implementasi register di sini.");
    }

    public void handleNextDay() {
        // Implementasi mekanisme untuk hari berikutnya
        System.out.println("Hari berikutnya dipilih. Implementasi di sini.");
    }

    public void handleCekSaldoAntarAkun(String command) {
        // Implementasi mekanisme cek saldo antar akun
        System.out.println(command + " dipilih. Implementasi di sini.");
    }
}