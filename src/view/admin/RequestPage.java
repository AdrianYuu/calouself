package view.admin;

import com.mysql.cj.xdevapi.Table;
import config.AppConfig;
import controller.ItemController;
import enums.ItemStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
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

    private TextInputDialog declineTD;


    private RequestPage() {
        itemController = ItemController.getInstance();
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

        itemTV = new TableView<>();

        declineTD = new TextInputDialog();

        declineTD.setTitle("Decline Reason");

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        TableColumn<Item, String> sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        TableColumn<Item, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        TableColumn<Item, String> categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        TableColumn<Item, String> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        TableColumn<Item, Void> actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(createActionCellFactory());

        itemTV.getColumns().addAll(nameColumn, sizeColumn, priceColumn, categoryColumn, statusColumn, actionsColumn );

        itemTV.setItems(items);
    }

    private Callback<TableColumn<Item, Void>, TableCell<Item, Void>> createActionCellFactory() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                declineTD.showAndWait();
                declineTD.setContentText("skibdi");

            }
        };
        return new Callback<>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                return new TableCell<>() {

                    private final Button acceptButton = new Button("Approve");
                    private final Button declineButton = new Button("Decline");
                    {
                        acceptButton.setOnAction(event -> {
                            Item item = getTableView().getItems().get(getIndex());
                            itemController.approveItem(item.getItemId());
                            createOrRefreshPage();
                        });

                        declineButton.setOnAction(event);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox actionButtons = new HBox(acceptButton, declineButton);
                            actionButtons.setSpacing(10);
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        };
    }


    @Override
    public void setLayout() {
        setTop(NavigationBar.getNavigationBar());
        container.getChildren().addAll(pageLbl, itemTV);
        container.setSpacing(10);

        container.setAlignment(Pos.CENTER);
        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;
        container.setMaxWidth(tableWidth);

        setCenter(container);

        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 32px; -fx-font-weight: bolder;");

        itemTV.getColumns().forEach(col -> {
            ((TableColumn)col).setMinWidth(tableWidth / itemTV.getColumns().size());
        });
    }



    @Override
    public void createOrRefreshPage() {
        Response<List<Item>> response = itemController.viewRequestedItem();

        if (response.isSuccess()) {
            items = FXCollections.observableArrayList(response.getData());
        }
        else {
            items = FXCollections.emptyObservableList();
        }

        super.createOrRefreshPage();
    }

    @Override
    public void setStyle() {

    }

    @Override
    public void setEvent() {

    }

}
