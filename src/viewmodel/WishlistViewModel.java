package viewmodel;

import model.Item;
import model.Wishlist;

public class WishlistViewModel {
    private Wishlist wishlist;
    private Item item;

    public WishlistViewModel(Wishlist wishlist, Item item) {
        this.wishlist = wishlist;
        this.item = item;
    }

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist(Wishlist wishlist) {
        this.wishlist = wishlist;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
