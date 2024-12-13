package controller;

import lib.response.Response;
import model.Transaction;

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

    public Response<List<Transaction>> viewHistory(String userId) {
        List<Transaction> transactions = Transaction.getByUserId(userId);

        if(transactions.isEmpty()){
            return Response.Failed("There is no history.");
        }

        return Response.Success("Successfully get histories.", transactions);
    }

}
