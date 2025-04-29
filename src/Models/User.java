package Models;
import java.util.UUID;


public abstract class User {
    private UUID id; // Identifier unik dari User
    private String username; // Username pengguna
    private String password; // Password pengguna
    private String role; // Role pengguna (Pembeli, Penjual, Pengirim)
    private long balance; // Saldo pengguna, bernilai 0 saat inisiasi

    public User(String username, String password, String role) {
        this.id = UUID.randomUUID(); // Generate ID unik
        this.username = username;
        this.password = password;
        this.role = role;
        this.balance = 0; // Inisialisasi saldo ke 0
    }

    public UUID getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return this.role;
    }

    public String getBalance() {
        return String.valueOf(balance);
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public abstract void getRiwayatTransaksi(Transaksi[] transaksi);
}