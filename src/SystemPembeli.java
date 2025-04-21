import java.util.Scanner;

public class SystemPembeli implements SystemMenu {
    private Pembeli activePembeli;
    private Scanner input;
    private Burhanpedia mainRepository;

    public SystemPembeli(Burhanpedia mainRepository) {
        this.mainRepository = mainRepository;
        this.input = new Scanner(System.in);
    }

    @Override
    public String showMenu() {
        return """
                === Menu Pembeli ===
                1. Cel Saldo
                2. Topup Saldo
                3. Cek Daftar Barang
                4. Tambah Barang ke Keranjang
                5. Checkout Keranjang
                6. Lacak Barang
                7. Lihat Laporan Pengeluaran
                8. Lihat Riwayat Transaksi
                9. Logout
                """;
    }

    @Override
    public void handleMenu() {
        while (true) {
            System.out.println(showMenu());
            System.out.print("Pilih menu: ");
            int choice = input.nextInt();

            switch (choice) {
                case 1 -> handleCekSaldo();
                case 2 -> handleTopupSaldo();
                case 3 -> handleCekDaftarBarang();
                case 4 -> handleTambahKeKeranjang();
                case 5 -> handleCheckout();
                case 6 -> handleLacakBarang();
                case 7 -> handleLaporanPengeluaran();
                case 8 -> handleRiwayatTransaksi();
                case 9 -> {
                    System.out.println("Logout berhasil.");
                    return;
                }
            
                default -> System.out.println("Pilihan tidak valid.");
            }
        }
    }

    public void handleCekSaldo() {
        System.out.println("Cek Saldo dipilih. Implementasi di sini.");
        // Implementasi untuk cek saldo pembeli
    }

    public void handleTopupSaldo() {
        System.out.println("Topup Saldo dipilih. Implementasi di sini.");
        // Implementasi untuk topup saldo pembeli
    }

    public void handleCekDaftarBarang() {
        System.out.println("Cek Daftar Barang dipilih. Implementasi di sini.");
        // Implementasi untuk cek daftar barang
    }

    public void handleTambahKeKeranjang() {
        System.out.println("Tambah Barang ke Keranjang dipilih. Implementasi di sini.");
        // Implementasi untuk menambah barang ke keranjang
    }

    public void handleCheckout() {
        System.out.println("Checkout Keranjang dipilih. Implementasi di sini.");
        // Implementasi untuk checkout keranjang
    }

    public void handleLacakBarang() {
        System.out.println("Lacak Barang dipilih. Implementasi di sini.");
        // Implementasi untuk melacak barang
    }

    public void handleLaporanPengeluaran() {
        System.out.println("Lihat Laporan Pengeluaran dipilih. Implementasi di sini.");
        // Implementasi untuk melihat laporan pengeluaran
    }

    public void handleRiwayatTransaksi() {
        System.out.println("Lihat Riwayat Transaksi dipilih. Implementasi di sini.");
        // Implementasi untuk melihat riwayat transaksi
    }
}
