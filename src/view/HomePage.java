package view;

import controller.ItemController;
import enums.ItemStatus;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;
import view.base.Page;
import view.component.card.ItemCard;
import view.component.navbar.NavigationBar;

import java.util.List;
import java.util.stream.Collectors;

public final class HomePage extends Page {

    private final ItemController itemController;
    private ScrollPane container;

    private FlowPane itemContainer;

    @Override
    public void init() {
        container = new ScrollPane();
        itemContainer = new FlowPane();

        Response<List<Item>> response = itemController.viewItems();

        if (response.isSuccess()) {
            for (Item item : response.getData())
                itemContainer.getChildren().add((new ItemCard(item, item.getSellerId().equals(SessionManager.getCurrentUser().getUserId()))));
        }
    }

    @Override
    public void setLayout() {
        setTop(NavigationBar.getNavigationBar());

        container.setContent(itemContainer);
        setCenter(container);
    }

    @Override
    public void setStyle() {
        container.setPadding(new Insets(20));
        container.setFitToWidth(true);
        itemContainer.setAlignment(Pos.CENTER);
        itemContainer.setVgap(16);
        itemContainer.setHgap(16);
    }

    @Override
    public void setEvent() {
    }

    private static HomePage instance;

    public static HomePage getInstance() {
        return instance = (instance == null) ? new HomePage() : instance;
    }

    private HomePage() {
        itemController = ItemController.getInstance();
        createOrRefreshPage();
    }

}
