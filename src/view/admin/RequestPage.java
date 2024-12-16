package view.admin;

import config.AppConfig;
import controller.ItemController;
import enums.UserRole;
import interfaces.IMiddleware;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;
import utils.AlertHelper;
import view.auth.LoginPage;
import view.base.Page;
import view.component.navbar.NavigationBar;

import java.util.List;
import java.util.Optional;

public class RequestPage extends Page {

    private final ItemController itemController;

    private VBox container;
    private Label pageLbl;

    private TableView<Item> itemTV;
    private TableColumn<Item, String> nameColumn;
    private TableColumn<Item, String> sizeColumn;
    private TableColumn<Item, Integer> priceColumn;
    private TableColumn<Item, String> categoryColumn;
    private TableColumn<Item, String> statusColumn;
    private TableColumn<Item, Void> actionsColumn;

    private TextInputDialog declineTID;

    private ObservableList<Item> items;

    @Override
    public void init() {
        Response<List<Item>> response = itemController.viewRequestedItems();
        items = response.isSuccess() ? FXCollections.observableArrayList(response.getData()) : FXCollections.emptyObservableList();

        container = new VBox();
        pageLbl = new Label("Item Requests");

        itemTV = new TableView<>();

        nameColumn = new TableColumn<>("Name");
        sizeColumn = new TableColumn<>("Size");
        priceColumn = new TableColumn<>("Price");
        categoryColumn = new TableColumn<>("Category");
        statusColumn = new TableColumn<>("Status");
        actionsColumn = new TableColumn<>("Actions");

        declineTID = new TextInputDialog();
    }

    @Override
    public void setLayout() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<>("itemSize"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("itemStatus"));
        actionsColumn.setCellFactory(createActionCellFactory());

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        itemTV.getColumns().addAll(nameColumn, sizeColumn, priceColumn, categoryColumn, statusColumn, actionsColumn);
        itemTV.setItems(items);
        itemTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / itemTV.getColumns().size());
        });

        container.getChildren().addAll(pageLbl, itemTV);
        container.setSpacing(14);
        container.setMaxWidth(tableWidth);
        container.setPadding(new Insets(20, 0, 0, 0));

        declineTID.setTitle("Decline Reason");
        declineTID.setContentText("Enter decline reason:");
        declineTID.setHeaderText("");
        declineTID.setGraphic(null);

        setTop(NavigationBar.getNavigationBar());
        setCenter(container);
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

                            AlertHelper.showInfo("Operation Success", response.getMessage());
                            createOrRefreshPage();
                        });

                        declineBtn.setOnAction(event -> {
                            Item item = getTableView().getItems().get(getIndex());

                            Optional<String> result = declineTID.showAndWait();

                            result.ifPresent(reason -> {
                                Response<Item> response = itemController.declineItem(item.getItemId(), reason);

                                if (!response.isSuccess()) {
                                    AlertHelper.showError("Operation Failed", response.getMessage());
                                    return;
                                }

                                AlertHelper.showInfo("Operation Success", response.getMessage());
                                createOrRefreshPage();
                            });
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
        this.itemController = ItemController.getInstance();
        createOrRefreshPage();
    }

    @Override
    public void check() {
        if(SessionManager.getCurrentUser() == null || !SessionManager.getCurrentUser().getRole().equals(UserRole.ADMIN)){
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        }
    }
}
