package controller;

import enums.ItemStatus;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;
import utils.AlertHelper;

import java.util.List;
import java.util.stream.Collectors;

public final class ItemController {


    private ItemController() {
    }

    public Response<Item> uploadItem(String itemName, String itemSize, String itemPrice, String itemCategory) {
        String message = checkItemValidation(itemName, itemSize, itemPrice, itemCategory);

        if (!message.isEmpty()) {
            return Response.Failed(message);
        }

        boolean isSuccess = Item.create(itemName, itemSize, Integer.parseInt(itemPrice), itemCategory, ItemStatus.PENDING, SessionManager.getCurrentUser().getUserId());

        if (!isSuccess) {
            return Response.Failed("Failed to upload item.");
        }

        return Response.Success("Item uploaded successfully.", null);
    }

    public Response<List<Item>> viewItems() {
        List<Item> items = Item.getAll().stream().filter(item -> item.getItemStatus() == ItemStatus.APPROVED).collect(Collectors.toList());

        if (items.isEmpty()) {
            return Response.Failed("There is no accepted items.");
        }

        return Response.Success("Successfully get accepted items.", items);
    }

    public Response<List<Item>> viewRequestedItems() {
        List<Item> items = Item.getAll().stream().filter(item -> item.getItemStatus() == ItemStatus.PENDING).collect(Collectors.toList());

        if (items.isEmpty()) {
            return Response.Failed("There is no requested items.");
        }

        return Response.Success("Successfully get requested items.", items);
    }

    public Response<Item> approveItem(String itemId) {
        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item not found.");
        }

        if (item.getItemStatus() == ItemStatus.APPROVED) {
            return Response.Failed("Item status is already approved.");
        }

        boolean isSuccess = Item.update(itemId,
                item.getItemName(),
                item.getItemSize(),
                item.getItemPrice(),
                item.getItemCategory(),
                ItemStatus.APPROVED,
                null,
                item.getSellerId()
        );

        if (!isSuccess) {
            return Response.Failed("Failed to delete item.");
        }

        return Response.Success("Item approved successfully.", null);
    }

    public Response<Item> declineItem(String itemId, String reason) {
        if (reason.isBlank()) {
            return Response.Failed("Reason cannot be empty.");
        }

        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item not found.");
        }

        if (item.getItemStatus() == ItemStatus.DECLINED) {
            return Response.Failed("Item status is already declined.");
        }

        boolean isSuccess = Item.update(itemId,
                item.getItemName(),
                item.getItemSize(),
                item.getItemPrice(),
                item.getItemCategory(),
                ItemStatus.DECLINED,
                reason,
                item.getSellerId()
        );

        if (!isSuccess) {
            return Response.Failed("Failed to delete item.");
        }

        return Response.Success("Item declined successfully.", null);
    }

    public Response<Item> deleteItem(String itemId) {
        boolean isSuccess = Item.delete(itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to delete item.");
        }

        return Response.Success("Item deleted successfully.", null);
    }

    public Response<Item> editItem(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory) {
        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item does not exists.");
        }

        boolean isSuccess = Item.update(itemId, itemName, itemSize, Integer.parseInt(itemPrice), itemCategory, item.getItemStatus(), null, item.getSellerId());

        if (!isSuccess) {
            return Response.Failed("Failed to update item.");
        }

        return Response.Success("Item updated successfully.", null);
    }

    private String checkItemValidation(String itemName, String itemSize, String itemPrice, String itemCategory) {
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

    private static ItemController instance;

    public static ItemController getInstance() {
        return instance = (instance == null) ? new ItemController() : instance;
    }
}
