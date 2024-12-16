package controller;

import enums.ItemStatus;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class ItemController {

    /**
     * Uploads a new item with the provided details.
     *
     * @param itemName     the name of the item
     * @param itemSize     the size of the item
     * @param itemPrice    the price of the item (as a string)
     * @param itemCategory the category of the item
     * @return a response indicating success or failure of the item upload
     */
    public Response<Item> uploadItem(String itemName, String itemSize, String itemPrice, String itemCategory) {
        String message = checkItemValidation(itemName, itemSize, itemPrice, itemCategory);

        if (!message.isEmpty()) {
            return Response.Failed(message);
        }

        boolean isSuccess = Item.create(itemName, itemSize, Integer.parseInt(itemPrice), itemCategory, ItemStatus.PENDING, SessionManager.getCurrentUser().getUserId());

        if (!isSuccess) {
            return Response.Failed("Failed to upload item.");
        }

        return Response.Success("Item uploaded successfully.");
    }

    /**
     * Updates an existing item with the provided details.
     *
     * @param itemId       the ID of the item to update
     * @param itemName     the new name of the item
     * @param itemSize     the new size of the item
     * @param itemPrice    the new price of the item (as a string)
     * @param itemCategory the new category of the item
     * @return a response indicating success or failure of the item update
     */
    public Response<Item> editItem(String itemId, String itemName, String itemSize, String itemPrice, String itemCategory) {
        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item does not exist.");
        }

        String message = checkItemValidation(itemName, itemSize, itemPrice, itemCategory);

        if (!message.isEmpty()) {
            return Response.Failed(message);
        }

        boolean isSuccess = Item.update(itemId, itemName, itemSize, Integer.parseInt(itemPrice), itemCategory, item.getItemStatus(), null, item.getSellerId());

        if (!isSuccess) {
            return Response.Failed("Failed to update item.");
        }

        return Response.Success("Item updated successfully.");
    }

    /**
     * Deletes an existing item.
     *
     * @param itemId the ID of the item to delete
     * @return a response indicating success or failure of the item deletion
     */
    public Response<Item> deleteItem(String itemId) {
        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item does not exist.");
        }

        boolean isSuccess = Item.delete(itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to delete item.");
        }

        return Response.Success("Item deleted successfully.");
    }

    /**
     * Searches for items that match the given query.
     *
     * @param searchQuery the query to search items by name
     * @return a response containing a list of matching items or an error message
     */
    public Response<List<Item>> browseItem(String searchQuery) {
        List<Item> items = Item.getAll().stream().filter(item -> item.getItemStatus() == ItemStatus.APPROVED).collect(Collectors.toList());

        if (items.isEmpty()) {
            return Response.Failed("There are no items.");
        }

        List<Item> searchedItems = new ArrayList<>();

        for (Item item : items) {
            if (item.getItemName().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchedItems.add(item);
            }
        }

        if (searchedItems.isEmpty()) {
            return Response.Failed("There are no items that match the search query.");
        }

        return Response.Success(searchedItems);
    }

    /**
     * Retrieves all approved items.
     *
     * @return a response containing a list of approved items or an error message
     */
    public Response<List<Item>> viewItems() {
        List<Item> items = Item.getAll().stream().filter(item -> item.getItemStatus() == ItemStatus.APPROVED).collect(Collectors.toList());

        if (items.isEmpty()) {
            return Response.Failed("There are no items.");
        }

        return Response.Success(items);
    }

    /**
     * Retrieves all requested (pending approval) items.
     *
     * @return a response containing a list of requested items or an error message
     */
    public Response<List<Item>> viewRequestedItems() {
        List<Item> items = Item.getAll().stream().filter(item -> item.getItemStatus() == ItemStatus.PENDING).collect(Collectors.toList());

        if (items.isEmpty()) {
            return Response.Failed("There are no requested items.");
        }

        return Response.Success(items);
    }

    /**
     * Approves a pending item.
     *
     * @param itemId the ID of the item to approve
     * @return a response indicating success or failure of the approval process
     */
    public Response<Item> approveItem(String itemId) {
        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item not found.");
        }

        boolean isSuccess = Item.update(itemId, item.getItemName(), item.getItemSize(), item.getItemPrice(), item.getItemCategory(), ItemStatus.APPROVED, null, item.getSellerId());

        if (!isSuccess) {
            return Response.Failed("Failed to update item.");
        }

        return Response.Success("Item approved successfully.");
    }

    /**
     * Declines a pending item with a given reason.
     *
     * @param itemId the ID of the item to decline
     * @param reason the reason for declining the item
     * @return a response indicating success or failure of the decline process
     */
    public Response<Item> declineItem(String itemId, String reason) {
        if (reason.isBlank()) {
            return Response.Failed("Reason can't be empty.");
        }

        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item not found.");
        }

        boolean isSuccess = Item.update(itemId, item.getItemName(), item.getItemSize(), item.getItemPrice(), item.getItemCategory(), ItemStatus.DECLINED, reason, item.getSellerId());

        if (!isSuccess) {
            return Response.Failed("Failed to update item.");
        }

        return Response.Success("Item declined successfully.");
    }

    /**
     * Validates the details of an item.
     *
     * @param itemName     the name of the item
     * @param itemSize     the size of the item
     * @param itemPrice    the price of the item (as a string)
     * @param itemCategory the category of the item
     * @return a validation message if there are errors; an empty string if validation passes
     */
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

    /**
     * Retrieves the singleton instance of the ItemController.
     *
     * @return the singleton instance of the ItemController
     */
    public static ItemController getInstance() {
        return instance = (instance == null) ? new ItemController() : instance;
    }

    private ItemController() {
    }

}
