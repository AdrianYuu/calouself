package view.component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lib.manager.PageManager;
import view.HomePage;
import view.UploadItemPage;

public class SellerNavigationBar extends NavigationBar {

    private Menu homeMn;
    private MenuItem homeMi;

    private Menu itemMn;
    private MenuItem uploadItemMi;
    private MenuItem viewOfferedItemMi;

    @Override
    public void init() {
        homeMn = new Menu("Home");
        itemMn = new Menu("Item");

        homeMi = new MenuItem("Home");
        uploadItemMi = new MenuItem("Upload Item");
        viewOfferedItemMi = new MenuItem("View Offered Item");
    }

    @Override
    public void setLayout() {
        homeMn.getItems().add(homeMi);
        itemMn.getItems().addAll(uploadItemMi, viewOfferedItemMi);
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

        viewOfferedItemMi.setOnAction(e -> {
        });
    }

    private static NavigationBar instance;

    public static NavigationBar getInstance() {
        return instance = (instance == null) ? new SellerNavigationBar() : instance;
    }

    private SellerNavigationBar(){
    }
}
