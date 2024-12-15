package view.buyer;

import config.AppConfig;
import controller.ItemController;
import controller.OfferController;
import controller.TransactionController;
import controller.WishlistController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;
import model.Offer;
import model.Wishlist;
import utils.AlertHelper;
import view.base.Page;
import view.component.navbar.NavigationBar;
import viewmodel.OfferViewModel;
import viewmodel.PurchaseHistoryViewModel;
import viewmodel.WishlistViewModel;

import java.util.List;
import java.util.Optional;

public class ViewPurchaseHistoryPage extends Page {
    private final TransactionController transactionController;
    private final WishlistController wishlistController;

    private ObservableList<PurchaseHistoryViewModel> purchaseHistories;

    private VBox container;

    private Label pageLbl;

    private TableView purchaseHistoryTV;
    private TableColumn<PurchaseHistoryViewModel, String> transactionIdColumn;
    private TableColumn<PurchaseHistoryViewModel, String> nameColumn;
    private TableColumn<PurchaseHistoryViewModel, String> sizeColumn;
    private TableColumn<PurchaseHistoryViewModel, String> priceColumn;
    private TableColumn<PurchaseHistoryViewModel, String> categoryColumn;
    private TableColumn<OfferViewModel, Void> actionsColumn;

    @Override
    public void init() {
        Response<List<PurchaseHistoryViewModel>> response = transactionController.viewHistory(SessionManager.getCurrentUser().getUserId());
        purchaseHistories = response.isSuccess() ? FXCollections.observableArrayList(response.getData()) : FXCollections.emptyObservableList();

        container = new VBox();

        pageLbl = new Label("Purchase Histories");

        purchaseHistoryTV = new TableView<>();

        transactionIdColumn = new TableColumn<>("Transaction Id");
        transactionIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTransaction().getTransactionId()));

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemName()));

        sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemSize()));

        priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemPrice().toString()));

        categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemCategory()));

        purchaseHistoryTV.getColumns().addAll(transactionIdColumn, nameColumn, sizeColumn, priceColumn, categoryColumn);

        purchaseHistoryTV.setItems(purchaseHistories);
    }

    public void setLayout() {
        setTop(NavigationBar.getNavigationBar());
        container.getChildren().addAll(pageLbl, purchaseHistoryTV);
        container.setSpacing(14);

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        container.setMaxWidth(tableWidth);
        container.setPadding(new Insets(20, 0, 0, 0));
        setCenter(container);

        purchaseHistoryTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / purchaseHistoryTV.getColumns().size());
        });
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
        this.wishlistController = WishlistController.getInstance();
        createOrRefreshPage();
    }
}
