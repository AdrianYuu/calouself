package viewmodel;

import model.Item;
import model.Transaction;

public class PurchaseHistoryViewModel {
    private Transaction transaction;
    private Item item;

    public PurchaseHistoryViewModel(Transaction transaction, Item item) {
        this.transaction = transaction;
        this.item = item;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Item getItem() {
        return item;
    }

}
