package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import enums.UserRole;
import lib.db.Connect;

public final class User {

    private String userId;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    private UserRole role;

    public User(String userId, String username, String password, String phoneNumber, String address, UserRole role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.role = role;
    }

    public static User getById(String userId){
        String query = "SELECT * FROM users WHERE user_id = ? LIMIT 1";

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query, userId);

            if (rs.next()) {
                return new User(
                        String.valueOf(rs.getInt("user_id")),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        UserRole.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User getByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ? LIMIT 1";

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query, username);

            if (rs.next()) {
                return new User(
                        String.valueOf(rs.getInt("user_id")),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("phone_number"),
                        rs.getString("address"),
                        UserRole.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean create(String username, String password, String phoneNumber, String address, UserRole role) {
        String query = "INSERT INTO users (username, password, phone_number, address, role) VALUES (?, ?, ?, ?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, username, password, phoneNumber, address, role.name());
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public UserRole getRole() {
        return role;
    }

}
