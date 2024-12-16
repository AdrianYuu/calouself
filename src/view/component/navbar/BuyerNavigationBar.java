package view.component.navbar;

import config.AppConfig;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import view.HomePage;
import view.auth.LoginPage;
import view.buyer.ViewPurchaseHistoryPage;
import view.buyer.ViewWishlistPage;

public final class BuyerNavigationBar extends NavigationBar {

    private Menu mainMn;
    private MenuItem homeMi;
    private MenuItem logoutMi;

    private Menu wishlistMn;
    private MenuItem viewWishlistMi;

    private Menu historyMn;
    private MenuItem viewPurchaseHistoryMi;

    @Override
    public void init() {
        mainMn = new Menu(AppConfig.APP_NAME);
        wishlistMn = new Menu("Wishlist");
        historyMn = new Menu("History");

        homeMi = new MenuItem("Home");
        logoutMi = new MenuItem("Logout");
        viewWishlistMi = new MenuItem("View Wishlist");
        viewPurchaseHistoryMi = new MenuItem("View Purchase History");
    }

    @Override
    public void setLayout() {
        mainMn.getItems().addAll(homeMi, logoutMi);
        wishlistMn.getItems().add(viewWishlistMi);
        historyMn.getItems().add(viewPurchaseHistoryMi);
        getMenus().addAll(mainMn, wishlistMn, historyMn);
    }

    @Override
    public void setStyle() {
    }

    @Override
    public void setEvent() {
        homeMi.setOnAction(e -> {
            PageManager.changePage(HomePage.getInstance(), "Home Page");
        });

        viewWishlistMi.setOnAction(e -> {
            PageManager.changePage(ViewWishlistPage.getInstance(), "View Wishlist Page");
        });

        viewPurchaseHistoryMi.setOnAction(e -> {
            PageManager.changePage(ViewPurchaseHistoryPage.getInstance(), "View Purchase History Page");
        });

        logoutMi.setOnAction(e -> {
            SessionManager.logout();
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        });
    }

    private static NavigationBar instance;

    public static NavigationBar getInstance() {
        return instance = (instance == null) ? new BuyerNavigationBar() : instance;
    }

    private BuyerNavigationBar() {
    }
}
