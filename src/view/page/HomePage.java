package view.page;

import config.AppConfig;
import controller.ItemController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;
import view.page.auth.LoginPage;
import view.page.base.Page;
import view.component.card.ItemCard;
import view.component.navbar.NavigationBar;

import java.util.List;

public final class HomePage extends Page {

    private final ItemController itemController;

    private ScrollPane container;
    private FlowPane itemContainer;
    private HBox searchContainer;

    private TextField searchTf;
    private Button searchBtn;
    private Label emptyLbl;
    private VBox wrapper;

    @Override
    public void init() {
        container = new ScrollPane();
        searchContainer = new HBox();
        itemContainer = new FlowPane();
        wrapper = new VBox();

        searchTf = new TextField();
        searchBtn = new Button("Search");
        emptyLbl = new Label();


        Response<List<Item>> response = itemController.viewItems();

        if (!response.isSuccess()) {
            emptyLbl.setText(response.getMessage());
            itemContainer.getChildren().add(emptyLbl);
            return;
        }

        for (Item item : response.getData()) {
            itemContainer.getChildren().add((new ItemCard(item, item.getSellerId().equals(SessionManager.getCurrentUser().getUserId()))));
        }
    }

    @Override
    public void setLayout() {
        searchContainer.getChildren().addAll(searchTf, searchBtn);
        searchContainer.setSpacing(8);
        container.setContent(itemContainer);
        wrapper.getChildren().addAll(searchContainer, container);
        setTop(NavigationBar.getNavigationBar());
        setCenter(wrapper);
    }

    @Override
    public void setStyle() {
        searchContainer.setPadding(new Insets(20));
        searchTf.setMinWidth(AppConfig.SCREEN_WIDTH - 110);
        container.setPadding(new Insets(20));
        container.setFitToWidth(true);
        itemContainer.setAlignment(Pos.CENTER);
        itemContainer.setVgap(16);
        itemContainer.setHgap(16);
    }

    @Override
    public void setEvent() {
        searchBtn.setOnMouseClicked(e -> {
            search();
        });
    }

    private void search() {
        itemContainer.getChildren().clear();

        Response<List<Item>> response = itemController.browseItem(searchTf.getText());

        if (!response.isSuccess()) {
            emptyLbl.setText(response.getMessage());
            itemContainer.getChildren().add(emptyLbl);
            return;
        }

        for (Item item : response.getData()) {
            itemContainer.getChildren().add((new ItemCard(item, item.getSellerId().equals(SessionManager.getCurrentUser().getUserId()))));
        }
    }

    private static HomePage instance;

    public static HomePage getInstance() {
        return instance = (instance == null) ? new HomePage() : instance;
    }

    private HomePage() {
        this.itemController = ItemController.getInstance();
        createOrRefreshPage();
    }

    @Override
    public void check() {
        if(SessionManager.getCurrentUser() == null){
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        }
    }

}
