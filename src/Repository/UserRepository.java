package Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import Models.User;

public class UserRepository {
    private List<User> userList; // Menyimpan seluruh objek User

    public UserRepository() {
        this.userList = new ArrayList<>();
    }

    // Method untuk mendapatkan role-role yang dimiliki user tertentu
    public String[] getUserRoles(String username) {
        List<String> roles = new ArrayList<>();
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                roles.add(user.getRole());
            }
        }
        return roles.toArray(new String[0]);
    }

    // Method untuk mendapatkan user berdasarkan UUID
    public User getUserById(UUID id) {
        for (User user : userList) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null; // Return null jika user tidak ditemukan
    }

    // Method untuk mendapatkan user berdasarkan username
    public User getUserByName(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null; // Return null jika user dengan username dan role tidak ditemukan
    }

    // Method untuk mendapatkan user berdasarkan username dan role.
    // Biar class casting lebih gampang ketika perpindahan dari User Pembeli ke Penjual atau sebaliknya atau ke Pengirim
    public User getUserByNameAndRole(String username, String role) {
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.getRole().equals(role)) {
                return user;
            }
        }
        return null; // Return null jika user dengan username dan role tidak ditemukan
    }

    // Method untuk mendapatkan semua user
    public List<User> getAll() {
        return new ArrayList<>(userList);
        // Awalnya ini:
        // userList.toArray(new User[0])
    }

    // Method untuk menambahkan user ke repository
    public void addUser(User user) {
        userList.add(user);
    }
}