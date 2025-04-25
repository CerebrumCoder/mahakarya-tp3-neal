package Models;


public class Admin {
    private String username; // Username dari admin
    private String password; // Password dari admin

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
