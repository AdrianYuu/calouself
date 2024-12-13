package view.admin;

import config.AppConfig;
import controller.ItemController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;

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

        if (response.isSuccess()) {
            items = FXCollections.observableArrayList(response.getData());
        }
        else {
            items = FXCollections.emptyObservableList();
        }

        container = new VBox();

        pageLbl = new Label("Item Requests");

        itemTV = new TableView<>();

        declineTD = new TextInputDialog();

        declineTD.setTitle("Decline Reason");
        declineTD.setContentText("Enter decline reason:");
        declineTD.setHeaderText("");
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

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        container.setMaxWidth(tableWidth);
        container.setPadding(new Insets(20, 0, 0, 0));
        setCenter(container);
        itemTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / itemTV.getColumns().size());
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
        return new Callback<>() {
            @Override
            public TableCell<Item, Void> call(final TableColumn<Item, Void> param) {
                return new TableCell<>() {
                    private final Button approveBtn = new Button("Approve");
                    private final Button declineBtn = new Button("Decline");

                    {
                        approveBtn.setOnAction(event -> {
                            Item item = getTableView().getItems().get(getIndex());
                            Response<Item> response = itemController.approveItem(item.getItemId());

                            if (!response.isSuccess()) {
                                AlertHelper.showError("Operation Failed", response.getMessage());
                                return;
                            }

                            AlertHelper.showInfo("Item Approved", response.getMessage());
                            createOrRefreshPage();
                        });


                        declineBtn.setOnAction(event -> {
                            Item item = getTableView().getItems().get(getIndex());

                            declineTD.showAndWait();

                            String reason = declineTD.getEditor().getText();

                            if (reason.isBlank()) {
                                AlertHelper.showError("Invalid Input", "Reason cannot be empty.");
                                return;
                            }

                            Response<Item> response = itemController.declineItem(item.getItemId(), reason);

                            if (!response.isSuccess()) {
                                AlertHelper.showError("Operation Failed", response.getMessage());
                                return;
                            }

                            AlertHelper.showInfo("Item Declined", response.getMessage());
                            createOrRefreshPage();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox actionButtons = new HBox(approveBtn, declineBtn);
                            actionButtons.setSpacing(10);
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        };
    }

    private static RequestPage instance;

    public static RequestPage getInstance() {
        return instance = (instance == null) ? new RequestPage() : instance;
    }

    private RequestPage() {
        itemController = ItemController.getInstance();
        createOrRefreshPage();
    }

}
