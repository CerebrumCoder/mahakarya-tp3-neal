package Models;

import java.util.UUID;

public class Admin {
    private UUID id;
    private String username; // Username dari admin
    private String password; // Password dari admin

    public Admin(String username, String password) {
        this.id = UUID.randomUUID();
        this.username = username;
        this.password = password;
    }

    public UUID getId() {return this.id;}

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
}