package controller;

import lib.response.Response;
import model.Item;
import model.Transaction;
import viewmodel.PurchaseHistoryViewModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public final class TransactionController {

    private static TransactionController instance;

    public static TransactionController getInstance() {
        return instance = (instance == null) ? new TransactionController() : instance;
    }

    private TransactionController() {
    }

    public Response<Transaction> purchaseItem(String userId, String itemId) {
        boolean isSuccess = Transaction.create(userId, itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to purchase item.");
        }

        return Response.Success("Successfully purchase item.", null);
    }

    public Response<List<PurchaseHistoryViewModel>> viewHistory(String userId) {
        List<Transaction> transactions = Transaction.getByUserId(userId);
        ItemController itemController = ItemController.getInstance();
        List<PurchaseHistoryViewModel> purchaseHistoriesVM = new ArrayList<>();

        if(transactions.isEmpty()){
            return Response.Failed("There is no history.");
        }

        for(Transaction transaction : transactions){
            Response<Item> response = itemController.getById(transaction.getItemId());
            if(!response.isSuccess()) continue;
            Item item = response.getData();
            purchaseHistoriesVM.add(new PurchaseHistoryViewModel(transaction, item));
        }

        return Response.Success("Successfully get histories.", purchaseHistoriesVM);
    }

}
