package view.component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public final class AdminNavigationBar extends NavigationBar {

    private Menu homeMn;
    private MenuItem homeMi;

    private Menu itemMn;
    private MenuItem viewRequestedItemMi;

    @Override
    public void init() {
        homeMn = new Menu("Home");
        itemMn = new Menu("Item");

        homeMi = new MenuItem("Home");
        viewRequestedItemMi = new MenuItem("View Requested Item");
    }

    @Override
    public void setLayout() {
        homeMn.getItems().add(homeMi);
        itemMn.getItems().add(viewRequestedItemMi);
        getMenus().addAll(homeMn, itemMn);
    }

    @Override
    public void setStyle() {

    }

    @Override
    public void setEvent() {
        viewRequestedItemMi.setOnAction(e -> {
        });
    }

    private static NavigationBar instance;

    public static NavigationBar getInstance() {
        return instance = (instance == null) ? new AdminNavigationBar() : instance;
    }

    private AdminNavigationBar() {
    }
}
