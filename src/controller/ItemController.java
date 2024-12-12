package controller;

import enums.ItemStatus;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;

import java.util.List;

public final class ItemController {

    private static ItemController instance;

    public static ItemController getInstance() {
        return instance = (instance == null) ? new ItemController() : instance;
    }

    private ItemController() {
    }

    public Response<Item> uploadItem(String itemName, String itemSize, String itemPrice, String itemCategory) {
        String message = checkItemValidation(itemName, itemSize, itemPrice, itemCategory);

        if (!message.isEmpty()) {
            return Response.Failed(message);
        }

        boolean isSuccess = Item.create(itemName, itemSize, itemPrice, itemCategory, ItemStatus.PENDING, SessionManager.getCurrentUser().getUserId());

        if (!isSuccess) {
            return Response.Failed("Failed uploading item.");
        }

        return Response.Success(null);
    }

    public Response<List<Item>> viewItems() {
        List<Item> items = Item.getAll();

        if (items == null) {
            return Response.Failed("There is no items.");
        }

        return Response.Success(items);
    }

    public Response<Item> deleteItem(String itemId) {
        boolean isSuccess = Item.delete(itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to delete item.");
        }

        return Response.Success(null);
    }

    public Response<Item> editItem(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory) {
        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item does not exists.");
        }

        boolean isSuccess = Item.update(itemId, itemName, itemSize, itemPrice, itemCategory, item.getItemStatus(), item.getSellerId());

        if (!isSuccess) {
            return Response.Failed("Failed to update item.");
        }

        return Response.Success(null);
    }

    private String checkItemValidation(String itemName, String itemSize, String itemPrice, String itemCategory) {
        if (itemName.isBlank()) {
            return "Item name can't be empty.";
        }

        if (itemName.length() < 3) {
            return "Item name must be at least 3 characters.";
        }

        if (itemSize.isBlank()) {
            return "Item size can't be empty.";
        }

        if (itemPrice.isBlank()) {
            return "Price cannot be empty.";
        }

        try {
            int price = Integer.parseInt(itemPrice);
            if (price == 0) return "Price cannot be 0.";
        } catch (NumberFormatException e) {
            return "Price must be a number.";
        }

        if (itemCategory.isBlank()) {
            return "Item category can't be empty.";
        }

        if (itemCategory.length() < 3) {
            return "Item category must be at least 3 characters.";
        }

        return "";
    }

}
