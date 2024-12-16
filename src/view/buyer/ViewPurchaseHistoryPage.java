package view.buyer;

import config.AppConfig;
import controller.TransactionController;
import controller.WishlistController;
import enums.UserRole;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import lib.response.Response;
import view.HomePage;
import view.auth.LoginPage;
import view.base.Page;
import view.component.navbar.NavigationBar;
import viewmodel.PurchaseHistoryViewModel;

import java.util.List;

public class ViewPurchaseHistoryPage extends Page {
    private final TransactionController transactionController;

    private VBox container;
    private Label pageLbl;

    private TableView<PurchaseHistoryViewModel> purchaseHistoryTV;
    private TableColumn<PurchaseHistoryViewModel, String> transactionIdColumn;
    private TableColumn<PurchaseHistoryViewModel, String> nameColumn;
    private TableColumn<PurchaseHistoryViewModel, String> sizeColumn;
    private TableColumn<PurchaseHistoryViewModel, String> priceColumn;
    private TableColumn<PurchaseHistoryViewModel, String> categoryColumn;

    private ObservableList<PurchaseHistoryViewModel> purchaseHistories;

    @Override
    public void init() {
        Response<List<PurchaseHistoryViewModel>> response = transactionController.viewHistory(SessionManager.getCurrentUser().getUserId());
        purchaseHistories = response.isSuccess() ? FXCollections.observableArrayList(response.getData()) : FXCollections.emptyObservableList();

        container = new VBox();
        pageLbl = new Label("Purchase Histories");

        purchaseHistoryTV = new TableView<>();

        transactionIdColumn = new TableColumn<>("Transaction Id");
        nameColumn = new TableColumn<>("Name");
        sizeColumn = new TableColumn<>("Size");
        priceColumn = new TableColumn<>("Price");
        categoryColumn = new TableColumn<>("Category");
    }

    public void setLayout() {
        transactionIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTransaction().getTransactionId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemName()));
        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemSize()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemPrice().toString()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemCategory()));

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        purchaseHistoryTV.getColumns().addAll(transactionIdColumn, nameColumn, sizeColumn, priceColumn, categoryColumn);
        purchaseHistoryTV.setItems(purchaseHistories);
        purchaseHistoryTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / purchaseHistoryTV.getColumns().size());
        });

        container.getChildren().addAll(pageLbl, purchaseHistoryTV);
        container.setSpacing(14);
        container.setMaxWidth(tableWidth);
        container.setPadding(new Insets(20, 0, 0, 0));

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

    private static ViewPurchaseHistoryPage instance;

    public static ViewPurchaseHistoryPage getInstance() {
        return instance = (instance == null) ? new ViewPurchaseHistoryPage() : instance;
    }

    private ViewPurchaseHistoryPage() {
        this.transactionController = TransactionController.getInstance();
        createOrRefreshPage();
    }

    @Override
    public void check() {
        if(SessionManager.getCurrentUser() == null || !SessionManager.getCurrentUser().getRole().equals(UserRole.BUYER)){
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        }
    }

}
