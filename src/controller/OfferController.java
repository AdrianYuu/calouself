package controller;

import enums.OfferStatus;
import lib.response.Response;
import model.Item;
import model.Offer;
import model.Transaction;
import viewmodel.OfferViewModel;

import java.util.ArrayList;
import java.util.List;

public final class OfferController {

    /**
     * Retrieves all pending offers for a specific seller.
     *
     * @param sellerId the ID of the seller whose pending offers are to be retrieved
     * @return a response containing a list of OfferViewModel objects or an error message if no offers are found
     */
    public Response<List<OfferViewModel>> viewPendingOffers(String sellerId) {
        List<Offer> offers = Offer.getAll();

        if (offers.isEmpty()) {
            return Response.Failed("There is no offers.");
        }

        List<OfferViewModel> offersVM = new ArrayList<>();

        for (Offer offer : offers) {
            if (offer.getOfferStatus() != OfferStatus.PENDING) {
                continue;
            }

            Item item = Item.getById(offer.getItemId());

            if (item == null) {
                continue;
            }

            if (!item.getSellerId().equals(sellerId)) {
                continue;
            }

            offersVM.add(new OfferViewModel(offer, item));
        }

        if (offersVM.isEmpty()) {
            return Response.Failed("There is no offers.");
        }

        return Response.Success(offersVM);
    }

    /**
     * Gets the highest pending offer for a specific item.
     *
     * @param itemId the ID of the item for which the highest offer is to be retrieved
     * @return a response containing the highest offer or null if no offers are found
     */
    public Response<Offer> getItemHighestOffer(String itemId) {
        List<Offer> offers = Offer.getByItemId(itemId);

        Offer highestOffer = null;
        for (Offer offer : offers) {
            if (offer.getOfferStatus() != OfferStatus.PENDING) {
                continue;
            }

            if (highestOffer == null) {
                highestOffer = offer;
            } else if (offer.getOfferPrice() > highestOffer.getOfferPrice()) {
                highestOffer = offer;
            }
        }

        return Response.Success(highestOffer);
    }

    /**
     * Accepts an offer and creates a transaction for the item associated with the offer.
     *
     * @param offerId the ID of the offer to be accepted
     * @return a response indicating whether the offer was successfully accepted or if an error occurred
     */
    public Response<Offer> acceptOffer(String offerId) {
        Offer offer = Offer.getById(offerId);

        if (offer == null) {
            return Response.Failed("Offer not found.");
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
            return Response.Failed("Failed to update offer.");
        }

        boolean isCreated = Transaction.create(offer.getBuyerId(), offer.getItemId());

        if (!isCreated) {
            return Response.Failed("Failed to create transaction.");
        }

        return Response.Success("Offer accepted successfully.");
    }

    /**
     * Creates a new offer for an item by a specific buyer.
     *
     * @param itemId     the ID of the item for which the offer is being made
     * @param buyerId    the ID of the buyer making the offer
     * @param offerPrice the price offered by the buyer for the item
     * @return a response indicating whether the offer was successfully created or if an error occurred
     */
    public Response<Offer> createOffer(String itemId, String buyerId, String offerPrice) {
        if (offerPrice.isBlank()) {
            return Response.Failed("Offer price can't be empty.");
        }

        try {
            int price = Integer.parseInt(offerPrice);
            if (price <= 0) {
                return Response.Failed("Offer price must be a positive number.");
            }
        } catch (NumberFormatException e) {
            return Response.Failed("Offer price must be a number");
        }

        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item does not exists.");
        }

        if (Integer.parseInt(offerPrice) >= item.getItemPrice()) {
            return Response.Failed("Offer price must be less than item price.");
        }

        Response<Offer> response = this.getItemHighestOffer(itemId);

        if (!response.isSuccess()) {
            return Response.Failed("Cannot get highest offer, please try again later.");
        }

        Offer highestOffer = response.getData();

        if (highestOffer != null && Integer.parseInt(offerPrice) <= highestOffer.getOfferPrice()) {
            return Response.Failed(String.format("Offer price must be higher than %d.", highestOffer.getOfferPrice()));
        }

        boolean isSuccess = Offer.create(itemId, buyerId, Integer.parseInt(offerPrice), OfferStatus.PENDING);

        if (!isSuccess) {
            return Response.Failed("Failed to create offer.");
        }

        return Response.Success("Offer created successfully");
    }

    /**
     * Declines an offer with a specified reason.
     *
     * @param offerId the ID of the offer to be declined
     * @param reason  the reason for declining the offer
     * @return a response indicating whether the offer was successfully declined or if an error occurred
     */
    public Response<Offer> declineOffer(String offerId, String reason) {
        if (reason.isBlank()) {
            return Response.Failed("Reason can't be empty.");
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
            return Response.Failed("Failed to update offer.");
        }

        return Response.Success("Offer declined successfully.");
    }

    private static OfferController instance;

    /**
     * Singleton implementation to get an instance of OfferController.
     *
     * @return the single instance of OfferController
     */
    public static OfferController getInstance() {
        return instance = (instance == null) ? new OfferController() : instance;
    }

    private OfferController() {
    }

}
