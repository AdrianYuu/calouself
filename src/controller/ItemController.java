package controller;

import enums.ItemStatus;
import lib.response.Response;
import model.Item;

public final class ItemController {

    private static ItemController instance;

    public static ItemController getInstance() {
        return instance = (instance == null) ? new ItemController() : instance;
    }

    private ItemController() {
    }

    public Response<Item> uploadItem(String itemName, String itemCategory, String itemSize, String itemPrice) {
        String message = checkItemValidation(itemName, itemCategory, itemSize, itemPrice);

        if (!message.isEmpty()) {
            return Response.Failed(message);
        }

        boolean isSuccess = Item.create(itemName, itemSize, Integer.parseInt(itemPrice), itemCategory, ItemStatus.PENDING);

        if (!isSuccess) {
            return Response.Failed("Failed uploading item.");
        }

        return Response.Success(null);
    }

    private String checkItemValidation(String itemName, String itemCategory, String itemSize, String itemPrice) {
        if (itemName.isBlank()) {
            return "Item name can't be empty.";
        }

        if (itemName.length() < 3) {
            return "Item name must be at least 3 characters.";
        }

        if (itemCategory.isBlank()) {
            return "Item category can't be empty.";
        }

        if (itemCategory.length() < 3) {
            return "Item category must be at least 3 characters.";
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

        return "";
    }
}
