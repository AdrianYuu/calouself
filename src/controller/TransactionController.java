package controller;

import lib.response.Response;
import model.Item;
import model.Transaction;
import model.User;
import model.Wishlist;
import viewmodel.PurchaseHistoryViewModel;

import java.util.ArrayList;
import java.util.List;

public final class TransactionController {

    /**
     * Purchases an item for a user by creating a transaction. The item is removed
     * from the user's wishlist if present.
     *
     * @param userId the ID of the user making the purchase
     * @param itemId the ID of the item to be purchased
     * @return a response indicating success or failure of the purchase process
     */
    public Response<Transaction> purchaseItem(String userId, String itemId) {
        User user = User.getById(userId);

        if (user == null) {
            return Response.Failed("User not found.");
        }

        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item not found.");
        }

        Wishlist.deleteByItemId(itemId);

        boolean isSuccess = Transaction.create(userId, itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to purchase item.");
        }

        return Response.Success("Successfully purchase item.");
    }

    /**
     * Retrieves the purchase history of a user. For each transaction, the associated
     * item's details are included in the response.
     *
     * @param userId the ID of the user whose purchase history is to be retrieved
     * @return a response containing a list of PurchaseHistoryViewModel or an error message
     */
    public Response<List<PurchaseHistoryViewModel>> viewHistory(String userId) {
        List<Transaction> transactions = Transaction.getByUserId(userId);

        if (transactions.isEmpty()) {
            return Response.Failed("There is no history.");
        }

        List<PurchaseHistoryViewModel> purchaseHistoriesVM = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Item item = Item.getById(transaction.getItemId());

            if (item == null) {
                continue;
            }

            purchaseHistoriesVM.add(new PurchaseHistoryViewModel(transaction, item));
        }

        if (purchaseHistoriesVM.isEmpty()) {
            return Response.Failed("There is no history.");
        }

        return Response.Success(purchaseHistoriesVM);
    }

    private static TransactionController instance;

    /**
     * Singleton implementation to get an instance of TransactionController.
     *
     * @return the single instance of TransactionController
     */
    public static TransactionController getInstance() {
        return instance = (instance == null) ? new TransactionController() : instance;
    }

    private TransactionController() {
    }

}
