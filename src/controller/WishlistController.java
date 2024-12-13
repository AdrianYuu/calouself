package controller;

import lib.response.Response;
import model.Wishlist;

import java.util.List;

public final class WishlistController {

    private static WishlistController instance;

    public static WishlistController getInstance() {
        return instance = (instance == null) ? new WishlistController() : instance;
    }

    private WishlistController() {
    }

    public Response<Wishlist> addWishlist(String userId, String itemId) {
        boolean isSuccess = Wishlist.create(userId, itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to create wishlist.");
        }

        return Response.Success("Successfully create wishlist.", null);
    }

    public Response<List<Wishlist>> viewWishlist(String userId) {
        List<Wishlist> wishlists = Wishlist.getByUserId(userId);

        if(wishlists.isEmpty()){
            return Response.Failed("There is no wishlists.");
        }

        return Response.Success("Successfully get wishlists.", wishlists);
    }

    public Response<Wishlist> removeWishlist(String wishlistId){
        boolean isSuccess = Wishlist.delete(wishlistId);

        if(!isSuccess){
            return Response.Failed("Failed to remove wishlist.");
        }

        return Response.Success("Successfully removed wishlist.", null);
    }

}
