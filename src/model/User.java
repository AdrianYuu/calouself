package model;

import lib.db.Connect;

public final class User {

    private String userId;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private String role;

    public User(String userId, String username, String password, String phoneNumber, String address, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public static boolean create(String username, String password, String phoneNumber, String address, String role) {
        String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, username, password, phoneNumber, address, role);
    }

}
