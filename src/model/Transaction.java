package model;

import lib.db.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Transaction {
    private String transactionId;
    private String userId;
    private String itemId;

    public Transaction(String transactionId, String userId, String itemId) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.itemId = itemId;
    }

    public static boolean create(String userId, String itemId) {
        String query = "INSERT INTO transactions (user_id, item_id) VALUES (?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, userId, itemId);
    }

    public static List<Transaction> getByUserId(String userId) {
        String query = "SELECT * FROM transactions WHERE user_id = ?";

        List<Transaction> transactions = new ArrayList<>();

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query, userId);
            while (rs.next()) {
                transactions.add(
                        new Transaction(
                                String.valueOf(rs.getString("transaction_id")),
                                String.valueOf(rs.getString("user_id")),
                                String.valueOf(rs.getString("item_id"))
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

}
