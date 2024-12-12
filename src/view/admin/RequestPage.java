package view.admin;

import com.mysql.cj.xdevapi.Table;
import config.AppConfig;
import controller.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import lib.response.Response;
import model.Item;
import view.base.Page;
import view.component.navbar.NavigationBar;

import java.io.ObjectInputFilter;
import java.util.List;

public class RequestPage extends Page {
    private static RequestPage instance;

    private final ItemController itemController;
    private ObservableList<Item> items;

    private VBox container;

    private Label pageLbl;

    private TableView itemTV;

    private RequestPage() {
        itemController = ItemController.getInstance();
        Response<List<Item>> response = itemController.viewItems();

        if (response.isSuccess()) {
            items = FXCollections.observableArrayList(response.getData());
        }
        else {
            items = FXCollections.emptyObservableList();
        }
    }

    public static RequestPage getInstance() {
        if (instance == null) {
            instance = new RequestPage();
        }

        return instance;
    }
    @Override
    public void init() {
        container = new VBox();

        pageLbl = new Label("Item Requests");

        itemTV = new TableView();

        TableColumn nameColumn = new TableColumn("Name");
        TableColumn sizeColumn = new TableColumn("Size");
        TableColumn priceColumn = new TableColumn("Price");
        TableColumn categoryColumn = new TableColumn("Category");
        TableColumn statusColumn = new TableColumn("Status");
        TableColumn actionsColumn = new TableColumn("Actions");

        itemTV.getColumns().addAll(nameColumn, sizeColumn, priceColumn, categoryColumn, statusColumn);

        itemTV.setItems(items);
    }

    @Override
    public void setLayout() {
        setTop(NavigationBar.getNavigationBar());
        container.getChildren().addAll(pageLbl, itemTV);
        container.setSpacing(6);

        container.setAlignment(Pos.CENTER);
        container.setMaxWidth(AppConfig.SCREEN_WIDTH * 0.8 );
        setCenter(container);

        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 32px; -fx-font-weight: bolder;");
    }

    @Override
    public void setStyle() {
    }

    @Override
    public void setEvent() {

    }

}
