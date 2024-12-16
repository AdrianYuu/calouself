package controller;

import lib.response.Response;
import model.Item;
import model.Wishlist;
import viewmodel.WishlistViewModel;

import java.util.ArrayList;
import java.util.List;

public final class WishlistController {

    public Response<Wishlist> addWishlist(String userId, String itemId) {
        List<Wishlist> wishlists = Wishlist.getByUserId(userId);

        for (Wishlist wishlist : wishlists) {
            if (wishlist.getItemId().equals(itemId)) {
                return Response.Failed("Already wishlist this item.");
            }
        }

        Item item = Item.getById(itemId);

        if (item == null) {
            return Response.Failed("Item not found");
        }

        boolean isSuccess = Wishlist.create(userId, itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to create wishlist.");
        }

        return Response.Success("Successfully create wishlist.");
    }

    public Response<List<WishlistViewModel>> viewWishlist(String userId) {
        List<Wishlist> wishlists = Wishlist.getByUserId(userId);

        if (wishlists.isEmpty()) {
            return Response.Failed("There is no wishlists.");
        }

        List<WishlistViewModel> wishlistsVM = new ArrayList<>();

        for (Wishlist wishlist : wishlists) {
            Item item = Item.getById(wishlist.getItemId());

            if (item == null) {
                continue;
            }

            wishlistsVM.add(new WishlistViewModel(wishlist, item));
        }

        return Response.Success(wishlistsVM);
    }

    public Response<Wishlist> removeWishlist(String wishlistId) {
        Wishlist wishlist = Wishlist.getById(wishlistId);

        if (wishlist == null) {
            return Response.Failed("Wishlist does not exists.");
        }

        boolean isSuccess = Wishlist.delete(wishlistId);

        if (!isSuccess) {
            return Response.Failed("Failed to remove wishlist.");
        }

        return Response.Success("Successfully removed wishlist.", null);
    }

    private static WishlistController instance;

    public static WishlistController getInstance() {
        return instance = (instance == null) ? new WishlistController() : instance;
    }

    private WishlistController() {
    }

}
