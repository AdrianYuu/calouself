package viewmodel;

import model.Item;
import model.Wishlist;

public final class WishlistViewModel {
    private Wishlist wishlist;
    private Item item;

    public WishlistViewModel(Wishlist wishlist, Item item) {
        this.wishlist = wishlist;
        this.item = item;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public Item getItem() {
        return item;
    }

}
