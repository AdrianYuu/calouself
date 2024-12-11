package view.component;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import lib.manager.PageManager;
import view.UploadItemPage;

public class SellerNavigationBar extends NavigationBar {

    private Menu itemMn;
    private MenuItem uploadItemMi;

    public static NavigationBar create(){
        return new SellerNavigationBar();
    }

    @Override
    public void init() {
        itemMn = new Menu("Item");
        uploadItemMi = new MenuItem("Upload Item");
    }

    @Override
    public void setLayout() {
        itemMn.getItems().add(uploadItemMi);
        this.getMenus().add(itemMn);
    }

    @Override
    public void setStyle() {

    }

    @Override
    public void setEvent() {
        uploadItemMi.setOnAction(e -> {
            PageManager.changePage(UploadItemPage.getInstance(), "Upload Item Page");
        });
    }
}
