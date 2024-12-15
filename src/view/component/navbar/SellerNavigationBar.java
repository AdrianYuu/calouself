package view.component.navbar;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import view.HomePage;
import view.auth.LoginPage;
import view.seller.ItemOffersPage;
import view.seller.UploadItemPage;

public final class SellerNavigationBar extends NavigationBar {

    private Menu homeMn;
    private MenuItem homeMi;
    private MenuItem logoutMi;

    private Menu itemMn;
    private MenuItem uploadItemMi;
    private MenuItem viewItemOffers;

    @Override
    public void init() {
        homeMn = new Menu("Home");
        itemMn = new Menu("Item");

        homeMi = new MenuItem("Home");
        logoutMi = new MenuItem("Logout");
        uploadItemMi = new MenuItem("Upload Item");
        viewItemOffers = new MenuItem("View Item Offers");
    }

    @Override
    public void setLayout() {
        homeMn.getItems().addAll(homeMi, logoutMi);
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

        logoutMi.setOnAction(e -> {
            SessionManager.logout();
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        });
    }

    private static NavigationBar instance;

    public static NavigationBar getInstance() {
        return instance = (instance == null) ? new SellerNavigationBar() : instance;
    }

    private SellerNavigationBar() {
    }
}
