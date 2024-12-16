package view.component.navbar;

import config.AppConfig;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import view.page.HomePage;
import view.page.admin.RequestPage;
import view.page.auth.LoginPage;

public final class AdminNavigationBar extends NavigationBar {

    private Menu mainMn;
    private MenuItem homeMi;
    private MenuItem logoutMi;

    private Menu itemMn;
    private MenuItem viewRequestedItemMi;

    @Override
    public void init() {
        mainMn = new Menu(AppConfig.APP_NAME);
        itemMn = new Menu("Item");

        homeMi = new MenuItem("Home");
        logoutMi = new MenuItem("Logout");
        viewRequestedItemMi = new MenuItem("View Requested Item");
    }

    @Override
    public void setLayout() {
        mainMn.getItems().addAll(homeMi, logoutMi);
        itemMn.getItems().add(viewRequestedItemMi);
        getMenus().addAll(mainMn, itemMn);
    }

    @Override
    public void setStyle() {
    }

    @Override
    public void setEvent() {
        viewRequestedItemMi.setOnAction(e -> {
            PageManager.changePage(RequestPage.getInstance(), "Request Page");
        });

        homeMi.setOnAction(e -> {
            PageManager.changePage(HomePage.getInstance(), "Home Page");
        });

        logoutMi.setOnAction(e -> {
            SessionManager.logout();
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        });
    }

    private static NavigationBar instance;

    public static NavigationBar getInstance() {
        return instance = (instance == null) ? new AdminNavigationBar() : instance;
    }

    private AdminNavigationBar() {
    }
}
