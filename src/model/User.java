package model;

import lib.db.Connect;
import lib.response.Response;

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

    public static Response<User> create(String username, String password, String phoneNumber, String address, String role) {
        String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";

        boolean isSuccess = Connect.getConnection().executePreparedUpdate(query, username, password, phoneNumber, address, role);

        if (!isSuccess) {
            return new Response<User>(false, "Failed to create user.", null);
        }

        return new Response<User>(true, "Successfully create user.", null);
    }

}
