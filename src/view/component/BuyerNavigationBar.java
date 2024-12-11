package view.component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class BuyerNavigationBar extends NavigationBar {

    private Menu homeMn;
    private MenuItem homeMi;

    private Menu wishlistMn;
    private MenuItem viewWishlistMi;

    private Menu historyMn;
    private MenuItem viewPurchaseHistoryMi;

    @Override
    public void init() {
        homeMn = new Menu("Home");
        wishlistMn = new Menu("Wishlist");
        historyMn = new Menu("History");

        homeMi = new MenuItem("Home");
        viewWishlistMi = new MenuItem("View Wishlist");
        viewPurchaseHistoryMi = new MenuItem("View Purchase History");
    }

    @Override
    public void setLayout() {
        homeMn.getItems().add(homeMi);
        wishlistMn.getItems().add(viewWishlistMi);
        historyMn.getItems().add(viewPurchaseHistoryMi);
        getMenus().addAll(homeMn, wishlistMn, historyMn);
    }

    @Override
    public void setStyle() {

    }

    @Override
    public void setEvent() {
        viewWishlistMi.setOnAction(e -> {
        });
    }

    private static NavigationBar instance;

    public static NavigationBar getInstance() {
        return instance = (instance == null) ? new BuyerNavigationBar() : instance;
    }

    private BuyerNavigationBar(){
    }
}
