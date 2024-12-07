package lib.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public final class Connect {

    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "calouself";
    private final String HOST = "localhost:3306";
    private final String CONNECTION = String.format("jdbc:mysql://%s/%s", HOST, DATABASE);

    private Connection con;
    private Statement st;
    private static Connect connect;

    private Connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
            st = con.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to connect the database, the system is terminated!");
            System.exit(0);
        }
    }

    public static synchronized Connect getConnection() {
        return connect = (connect == null) ? new Connect() : connect;
    }

    public ResultSet executeQuery(String query) {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public boolean executeUpdate(String query) {
        try {
            int rowsAffected = st.executeUpdate(query);
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean executePreparedUpdate(String query, Object... parameters) {
        try {
            PreparedStatement ps = con.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) {
                ps.setObject(i + 1, parameters[i]);
            }
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}