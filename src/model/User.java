package model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    
    public static User get(String username) {
    	String query = "SELECT * FROM users WHERE username = ?";
    	
    	ResultSet rs = Connect.getConnection().executePreparedQuery(query, username);
    	
    	try {
	    	if (rs.next()) {
					return new User(
								String.valueOf(rs.getInt("user_id")),
								rs.getString("username"),
								rs.getString("password"),
								rs.getString("phone_number"),
								rs.getString("address"),
								rs.getString("role")
								);
	    	}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

	public String getPassword() {
		return password;
	}

}
