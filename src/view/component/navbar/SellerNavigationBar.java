package view.component.navbar;

import config.AppConfig;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import view.page.HomePage;
import view.page.auth.LoginPage;
import view.page.seller.OfferItemPage;
import view.page.seller.UploadItemPage;

public final class SellerNavigationBar extends NavigationBar {

    private Menu mainMn;
    private MenuItem homeMi;
    private MenuItem logoutMi;

    private Menu itemMn;
    private MenuItem uploadItemMi;
    private MenuItem viewItemOffers;

    @Override
    public void init() {
        mainMn = new Menu(AppConfig.APP_NAME);
        itemMn = new Menu("Item");

        homeMi = new MenuItem("Home");
        logoutMi = new MenuItem("Logout");
        uploadItemMi = new MenuItem("Upload Item");
        viewItemOffers = new MenuItem("View Item Offers");
    }

    @Override
    public void setLayout() {
        mainMn.getItems().addAll(homeMi, logoutMi);
        itemMn.getItems().addAll(uploadItemMi, viewItemOffers);
        getMenus().addAll(mainMn, itemMn);
    }

    @Override
    public void setStyle() {
    }

    @Override
    public void setEvent() {
        homeMi.setOnAction(e -> {
            PageManager.changePage(HomePage.getInstance(), "Home Page");
        });

        uploadItemMi.setOnAction(e -> {
            PageManager.changePage(UploadItemPage.getInstance(), "Upload Item Page");
        });

        viewItemOffers.setOnAction(e -> {
            PageManager.changePage(OfferItemPage.getInstance(), "Item Offers Page");
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
