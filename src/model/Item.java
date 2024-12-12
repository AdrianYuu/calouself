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
    private String itemPrice;
    private String itemCategory;
    private ItemStatus itemStatus;
    private String sellerId;

    public Item(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory, ItemStatus itemStatus, String sellerId) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
        this.sellerId = sellerId;
    }

    public static boolean create(String itemName, String itemSize, String itemPrice, String itemCategory, ItemStatus itemStatus, String sellerId) {
        String query = "INSERT INTO items (item_name, item_size, item_price, item_category, item_status, seller_id) VALUES (?, ?, ?, ?, ?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, itemName, itemSize, Integer.parseInt(itemPrice), itemCategory, itemStatus.name(), Integer.parseInt(sellerId));
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
                                rs.getString("item_price"),
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

    public String getItemName() {
        return itemName;
    }

    public String getItemSize() {
        return itemSize;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public String getSellerId() {
        return sellerId;
    }

}
