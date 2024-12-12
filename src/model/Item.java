package model;

import enums.ItemStatus;
import lib.db.Connect;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private String itemId;
    private String itemName;
    private String itemSize;
    private Integer itemPrice;
    private String itemCategory;
    private ItemStatus itemStatus;
    private String sellerId;

    public Item(String itemId, String itemName, String itemSize, Integer itemPrice, String itemCategory, ItemStatus itemStatus, String sellerId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.sellerId = sellerId;
    }

    public static boolean create(String itemName, String itemSize, Integer itemPrice, String itemCategory, ItemStatus itemStatus, String sellerId) {
        String query = "INSERT INTO items (item_name, item_size, item_price, item_category, item_status, seller_id) VALUES (?, ?, ?, ?, ?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, itemName, itemSize, itemPrice, itemCategory, itemStatus.name(), Integer.parseInt(sellerId));
    }

    public static List<Item> getAll() {
        String query = "SELECT * FROM items";

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query);

            List<Item> items = new ArrayList<>();

            while (rs.next()) {
                items.add(
                        new Item(
                                String.valueOf(rs.getInt("item_id")),
                                rs.getString("item_name"),
                                rs.getString("item_size"),
                                rs.getInt("item_price"),
                                rs.getString("item_category"),
                                ItemStatus.valueOf(rs.getString("item_status")),
                                rs.getString("seller_id")
                        )
                );
            }

            return items;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Item getById(String itemId) {
        String query = "SELECT * FROM items WHERE item_id = ? LIMIT 1";

        try {
            ResultSet rs = Connect.getConnection().executePreparedQuery(query, itemId);

            if (rs.next()) {
                return new Item(
                        String.valueOf(rs.getInt("item_id")),
                        rs.getString("item_name"),
                        rs.getString("item_size"),
                        rs.getInt("item_price"),
                        rs.getString("item_category"),
                        ItemStatus.valueOf(rs.getString("item_status")),
                        rs.getString("seller_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean update(String itemId, String itemName, String itemSize, Integer itemPrice, String itemCategory, ItemStatus itemStatus, String sellerId) {
        String query = "UPDATE items " +
                "SET item_name = ?, " +
                "item_size = ?, " +
                "item_price = ?, " +
                "item_category = ?, " +
                "item_status = ?, " +
                "seller_id = ? " +
                "WHERE item_id = ?";
        return Connect.getConnection().executePreparedUpdate(query, itemName, itemSize, itemPrice, itemCategory, itemStatus.name(), sellerId, itemId);
    }

    public static boolean delete(String itemId) {
        String query = "DELETE from items WHERE item_id = ?";
        return Connect.getConnection().executePreparedUpdate(query, itemId);
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getSellerId() {
        return sellerId;
    }

    public String getItemId() {
        return itemId;
    }

    public ItemStatus getItemStatus() {
        return itemStatus;
    }

}
