package Repository;

import java.util.ArrayList;
import java.util.List;
import Models.Admin;


public class AdminRepository {
    private List<Admin> adminList;

    public AdminRepository() {
        this.adminList = new ArrayList<>();
        // Hardcode 3 admin sesuai TP sebelumnya

        adminList.add(new Admin("admin", "admin"));
        adminList.add(new Admin("root", "toor"));
        adminList.add(new Admin("dekdepe", "aku_CinTaJaVa"));
    }

    // Method untuk mendapatkan admin berdasarkan username
    public Admin getUserByName(String username) {
        for (Admin admin : adminList) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return admin;
            }
        }

        return null; // Return null jika admin tidak ditemukan
    }

    // Method untuk mendapatkan semua admin
    public Admin[] getAll() {
        return adminList.toArray(new Admin[0]);
    }

    // Method untuk mengecek apakah username sudah ada
    public boolean isUsernameExists(String username) {
        for (Admin admin : adminList) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        // Return false jika username tidak ditemukan
        return false;
    }

    // Method untuk login
    public String login(String username, String password) {
        for (Admin admin : adminList) {
            if (admin.getUsername().equalsIgnoreCase(username)) {
                return admin.getPassword().equals(password) ? "Login berhasil! Selamat datang, " + admin.getUsername() + "!" : "Password salah!";
            }
        }
        return "Akun tidak ditemukan!";
    }
}
