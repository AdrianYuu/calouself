package view.admin;

import config.AppConfig;
import controller.ItemController;
import enums.ItemStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lib.response.Response;
import model.Item;
import utils.AlertHelper;
import view.base.Page;
import view.component.navbar.NavigationBar;

import java.util.List;

public class RequestPage extends Page {

    private final ItemController itemController;
    private ObservableList<Item> items;

    private VBox container;

    private Label pageLbl;

    private TableView itemTV;
    private TableColumn<Item, String> nameColumn;
    private TableColumn<Item, String> sizeColumn;
    private TableColumn<Item, Integer> priceColumn;
    private TableColumn<Item, String> categoryColumn;
    private TableColumn<Item, String> statusColumn;
    private TableColumn<Item, Void> actionsColumn;


    private TextInputDialog declineTD;

    @Override
    public void init() {
        Response<List<Item>> response = itemController.viewRequestedItems();
        items = response.isSuccess() ? FXCollections.observableArrayList(response.getData()) : FXCollections.emptyObservableList();

        container = new VBox();

        pageLbl = new Label("Item Requests");

        itemTV = new TableView<>();


        declineTD = new TextInputDialog();

        declineTD.setTitle("Decline Reason");
        declineTD.setContentText("Enter decline reason:");
        declineTD.setGraphic(null);

        TableColumn<Item, String> nameColumn = new TableColumn<>("Name");

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));

        sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));

        priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));

        actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(createActionCellFactory());

        itemTV.getColumns().addAll(nameColumn, sizeColumn, priceColumn, categoryColumn, statusColumn, actionsColumn);

        itemTV.setItems(items);
    }

    @Override
    public void setLayout() {
        setTop(NavigationBar.getNavigationBar());
        container.getChildren().addAll(pageLbl, itemTV);
        container.setSpacing(14);
        container.setMaxWidth(AppConfig.SCREEN_WIDTH * 0.8);
        container.setPadding(new Insets(20, 0, 0, 0));
        setCenter(container);
        itemTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(AppConfig.SCREEN_WIDTH * 0.8 / itemTV.getColumns().size());
        });
    }

    @Override
    public void setStyle() {
        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 32px; -fx-font-weight: bolder;");
    }

    @Override
    public void setEvent() {

    }

    private Callback<TableColumn<Item, Void>, TableCell<Item, Void>> createActionCellFactory() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {

                declineTD.showAndWait();

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
                            Response<Item> response = itemController.approveItem(item.getItemId());

                            if (!response.isSuccess()) {
                                AlertHelper.showError("Accept Item", response.getMessage());
                                return;
                            }

                            AlertHelper.showInfo("Accept Item", response.getMessage());
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

    private static RequestPage instance;

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


    public static RequestPage getInstance() {
        return instance = (instance == null) ? new RequestPage() : instance;
    }

    private RequestPage() {
        itemController = ItemController.getInstance();
        createOrRefreshPage();
    }

}
