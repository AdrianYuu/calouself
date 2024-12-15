package controller;

import lib.response.Response;
import model.Item;
import model.Wishlist;
import viewmodel.WishlistViewModel;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public final class WishlistController {

    private static WishlistController instance;

    public static WishlistController getInstance() {
        return instance = (instance == null) ? new WishlistController() : instance;
    }

    private WishlistController() {
    }

    public Response<Wishlist> addWishlist(String userId, String itemId) {
        List<Wishlist> wishlists = Wishlist.getByUserId(userId);

        for(Wishlist wishlist : wishlists){
            if(wishlist.getItemId().equals(itemId)){
                return Response.Failed("Already wishlist this item.");
            }
        }

        boolean isSuccess = Wishlist.create(userId, itemId);

        if (!isSuccess) {
            return Response.Failed("Failed to create wishlist.");
        }

        return Response.Success("Successfully create wishlist.", null);
    }

    public Response<List<WishlistViewModel>> viewWishlist(String userId) {
        ItemController itemController = ItemController.getInstance();
        List<Wishlist> wishlists = Wishlist.getByUserId(userId);
        List<WishlistViewModel> wishlistsVM = new ArrayList<>();

        if(wishlists.isEmpty()){
            return Response.Failed("There is no wishlists.");
        }

        for(Wishlist wishlist : wishlists){
            Response<Item> response = itemController.getById(wishlist.getItemId());
            if(!response.isSuccess()) continue;
            Item item = response.getData();
            wishlistsVM.add(new WishlistViewModel(wishlist, item));
        }

        return Response.Success("Successfully get wishlists.", wishlistsVM);
    }

    public Response<Wishlist> removeWishlist(String wishlistId){
        boolean isSuccess = Wishlist.delete(wishlistId);

        if(!isSuccess){
            return Response.Failed("Failed to remove wishlist.");
        }

        return Response.Success("Successfully removed wishlist.", null);
    }

}
