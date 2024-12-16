package viewmodel;

import model.Item;
import model.Offer;

public final class OfferViewModel {
    private Offer offer;
    private Item item;

    public OfferViewModel(Offer offer, Item item) {
        this.offer = offer;
        this.item = item;
    }

    public Offer getOffer() {
        return offer;
    }

    public Item getItem() {
        return item;
    }

}
