package view.component.navbar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lib.manager.PageManager;
import view.HomePage;
import view.seller.ItemOffersPage;
import view.seller.UploadItemPage;

public final class SellerNavigationBar extends NavigationBar {

    private Menu homeMn;
    private MenuItem homeMi;

    private Menu itemMn;
    private MenuItem uploadItemMi;
    private MenuItem viewItemOffers;

    @Override
    public void init() {
        homeMn = new Menu("Home");
        itemMn = new Menu("Item");

        homeMi = new MenuItem("Home");
        uploadItemMi = new MenuItem("Upload Item");
        viewItemOffers = new MenuItem("View Item Offers");
    }

    @Override
    public void setLayout() {
        homeMn.getItems().add(homeMi);
        itemMn.getItems().addAll(uploadItemMi, viewItemOffers);
        getMenus().addAll(homeMn, itemMn);
    }

    @Override
    public void setStyle() {

    }

    @Override
    public void setEvent() {
        homeMn.setOnAction(e -> {
            PageManager.changePage(HomePage.getInstance(), "Home Page");
        });

        uploadItemMi.setOnAction(e -> {
            PageManager.changePage(UploadItemPage.getInstance(), "Upload Item Page");
        });

        viewItemOffers.setOnAction(e -> {
            PageManager.changePage(ItemOffersPage.getInstance(), "Item Offers Page");
        });
    }

    private static NavigationBar instance;

    public static NavigationBar getInstance() {
        return instance = (instance == null) ? new SellerNavigationBar() : instance;
    }

    private SellerNavigationBar(){
    }
}
