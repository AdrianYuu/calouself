package model;

import lib.db.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class Wishlist {
    private String wishlistId;
    private String userId;
    private String itemId;

    public Wishlist(String wishlistId, String userId, String itemId) {
        this.wishlistId = wishlistId;
        this.userId = userId;
        this.itemId = itemId;
    }

    public static Wishlist getById(String itemId) {
        String query = "SELECT * FROM wishlists WHERE wishlist_id = ? LIMIT 1";

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query, itemId);

            if (rs.next()) {
                return new Wishlist(
                        String.valueOf(rs.getInt("wishlist_id")),
                        String.valueOf(rs.getInt("user_id")),
                        String.valueOf(rs.getInt("item_id"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Wishlist> getByUserId(String userId) {
        String query = "SELECT * FROM wishlists WHERE user_id = ?";

        List<Wishlist> wishlists = new ArrayList<>();

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query, userId);
            while (rs.next()) {
                wishlists.add(
                        new Wishlist(
                                String.valueOf(rs.getInt("wishlist_id")),
                                String.valueOf(rs.getInt("user_id")),
                                String.valueOf(rs.getInt("item_id"))
                        )
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wishlists;
    }

    public static boolean create(String userId, String itemId) {
        String query = "INSERT INTO wishlists (user_id, item_id) VALUES (?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, userId, itemId);
    }

    public static boolean delete(String wishlistId) {
        String query = "DELETE FROM wishlists WHERE wishlist_id = ?";
        return Connect.getConnection().executePreparedUpdate(query, wishlistId);
    }

    public static boolean deleteByItemId(String itemId){
        String query = "DELETE FROM wishlists WHERE item_id = ?";
        return Connect.getConnection().executePreparedUpdate(query, itemId);
    }

    public String getWishlistId() {
        return wishlistId;
    }

    public String getUserId() {
        return userId;
    }

    public String getItemId() {
        return itemId;
    }

}
