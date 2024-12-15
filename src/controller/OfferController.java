package controller;

import enums.OfferStatus;
import lib.response.Response;
import model.Item;
import model.Offer;
import model.Transaction;
import viewmodel.OfferViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OfferController {
    public Response<List<OfferViewModel>> viewPendingOffers(String sellerId) {
        ItemController itemController = ItemController.getInstance();
        List<OfferViewModel> offerVMList = new ArrayList<>();

        List<Offer> offers = Offer.getAll();

        for (Offer offer : offers) {
            if (offer.getOfferStatus() != OfferStatus.PENDING) {
                continue;
            }

            Response<Item> itemResponse = itemController.getById(offer.getItemId());

            if (!itemResponse.isSuccess()) {
                continue;
            }

            Item item = itemResponse.getData();

            if (!item.getSellerId().equals(sellerId)) {
                continue;
            }

            offerVMList.add(new OfferViewModel(offer, item));
        }

        return Response.Success("Successfully get offers.", offerVMList);
    }

    public Response<Offer> getItemHighestOffer(String itemId) {
        List<Offer> offers = Offer.getByItemId(itemId);

        Offer highestOffer = null;
        for (Offer offer : offers) {
            if (offer.getOfferStatus() != OfferStatus.ACCEPTED) {
                continue;
            }

            if (highestOffer == null) {
                highestOffer = offer;
            }
            else if (offer.getOfferPrice() > highestOffer.getOfferPrice()) {
                highestOffer = offer;
            }
        }

        return Response.Success("Successfully get highest offer", highestOffer);
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

    public Response<Offer> createOffer(String itemId, String buyerId, int offerPrice) {
        if (offerPrice <= 0) {
            return Response.Failed("Offer price must be a positive number.");
        }

        Response<Offer> highestOfferResponse = getItemHighestOffer(itemId);

        if (!highestOfferResponse.isSuccess()) {
            return Response.Failed("Cannot get highest offer, please try again later.");
        }

        Offer highestOffer = highestOfferResponse.getData();

        if (highestOffer != null && offerPrice <= highestOffer.getOfferPrice()) {
            return Response.Failed(String.format("Offer price must be higher than %d.", highestOffer.getOfferPrice()));
        }

        boolean isSuccess = Offer.create(itemId, buyerId, offerPrice, OfferStatus.PENDING);

        if (!isSuccess) {
            return Response.Failed("Failed creating offer.");
        }

        return Response.Success("Offer created successfully", null);
    }

    public Response<Offer> declineOffer(String offerId, String reason) {
        if (reason.isBlank()) {
            return Response.Failed("Reason cannot be empty.");
        }
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
                reason
        );

        if (!isSuccess) {
            return Response.Failed("Error updating offer data.");
        }

        return Response.Success("Offer declined successfully.", null);
    }

    private static OfferController instance;

    public static OfferController getInstance() {
        if (instance == null) {
            instance = new OfferController();
        }

        return instance;
    }
}
