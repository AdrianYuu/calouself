package controller;

import enums.OfferStatus;
import lib.response.Response;
import model.Item;
import model.Offer;
import model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class OfferController {
    public Response<List<Item>> getOfferedItems(String sellerId) {
        List<Offer> offers = Offer.getBySellerId(sellerId);

        if (offers.isEmpty()) {
            return Response.Failed("There is no offer.");
        }

        List<Item> items = new ArrayList<>();

        for (Offer offer : offers) {
            Item item = Item.getById(offer.getItemId());
            if (item != null) {
                items.add(item);
            }
        }

        return Response.Success("Successfully get offered items", items);
    }

    public Response<Offer> acceptOffer(String offerId) {
        TransactionController transactionController = TransactionController.getInstance();

        Offer offer = Offer.getById(offerId);

        if (offer == null) {
            return Response.Failed("Offer not found.");
        }

        Response<Transaction> response = transactionController.purchaseItem(offer.getBuyerId(), offer.getItemId());

        if (!response.isSuccess()) {
            return Response.Failed(response.getMessage());
        }

        boolean isSuccess = Offer.update(
                offer.getOfferId(),
                offer.getItemId(),
                offer.getBuyerId(),
                offer.getOfferPrice(),
                OfferStatus.ACCEPTED,
                null
        );

        if (!isSuccess) {
            return Response.Failed("Error updating offer data.");
        }

        return Response.Success("Offer accepted successfully.", null);
    }

    public Response<Offer> declineOffer(String offerId, String declineReason) {
        Offer offer = Offer.getById(offerId);

        if (offer == null) {
            return Response.Failed("Offer not found.");
        }

        boolean isSuccess = Offer.update(
                offer.getOfferId(),
                offer.getItemId(),
                offer.getBuyerId(),
                offer.getOfferPrice(),
                OfferStatus.DECLINED,
                declineReason
        );

        if (!isSuccess) {
            return Response.Failed("Error updating offer data.");
        }

        return Response.Success("Offer declined successfully.", null);
    }
}
