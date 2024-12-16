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

    public Response<Transaction> purchaseItem(String userId, String itemId) {
        User user = User.getById(userId);

        if (user == null) {
            return Response.Failed("User not found.");
        }

        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item not found.");
        }

        boolean isDeleted = Wishlist.deleteByItemId(itemId);

        if (!isDeleted) {
            return Response.Failed("Failed to delete wishlist.");
        }

        boolean isSuccess = Transaction.create(userId, itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to purchase item.");
        }

        return Response.Success("Successfully purchase item.");
    }

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

    public static TransactionController getInstance() {
        return instance = (instance == null) ? new TransactionController() : instance;
    }

    private TransactionController() {
    }

}
