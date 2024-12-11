package model;

import enums.ItemStatus;
import lib.db.Connect;

public class Item {
    private String itemId;
    private String itemName;
    private String itemSize;
    private Integer itemPrice;
    private String itemCategory;
    private ItemStatus itemStatus;

    public Item(String itemId, String itemName, String itemSize, Integer itemPrice, String itemCategory, ItemStatus itemStatus) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemSize = itemSize;
        this.itemPrice = itemPrice;
        this.itemCategory = itemCategory;
        this.itemStatus = itemStatus;
    }

    public static boolean create(String itemName, String itemSize, Integer itemPrice, String itemCategory, ItemStatus itemStatus) {
        String query = "INSERT INTO items(item_name, item_size, item_price, item_category, item_status) VALUES(?, ?, ?, ?, ?)";
        return Connect.getConnection().executePreparedUpdate(query, itemName, itemSize, itemPrice, itemCategory, itemStatus);
    }

}
