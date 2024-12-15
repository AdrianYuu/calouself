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

public class ViewWishlistPage extends Page {
    private final TransactionController transactionController;
    private final WishlistController wishlistController;

    private ObservableList<WishlistViewModel> wishlists;

    private VBox container;

    private Label pageLbl;

    private TableView wishlistTV;
    private TableColumn<WishlistViewModel, String> wishlistIdColumn;
    private TableColumn<WishlistViewModel, String> nameColumn;
    private TableColumn<WishlistViewModel, String> sizeColumn;
    private TableColumn<WishlistViewModel, String> priceColumn;
    private TableColumn<WishlistViewModel, String> categoryColumn;
    private TableColumn<WishlistViewModel, Void> actionsColumn;

    @Override
    public void init() {
        Response<List<WishlistViewModel>> response = wishlistController.viewWishlist(SessionManager.getCurrentUser().getUserId());
        wishlists = response.isSuccess() ? FXCollections.observableArrayList(response.getData()) : FXCollections.emptyObservableList();

        container = new VBox();

        pageLbl = new Label("My Wishlists");

        wishlistTV = new TableView<>();

        wishlistIdColumn = new TableColumn<>("Wishlist Id");
        wishlistIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWishlist().getWishlistId()));

        nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemName()));

        sizeColumn = new TableColumn<>("Size");
        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemSize()));

        priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemPrice().toString()));

        categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemCategory()));

        actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(createActionCellFactory());

        wishlistTV.getColumns().addAll(wishlistIdColumn, nameColumn, sizeColumn, priceColumn, categoryColumn, actionsColumn);

        wishlistTV.setItems(wishlists);
    }

    public void setLayout() {
        setTop(NavigationBar.getNavigationBar());
        container.getChildren().addAll(pageLbl, wishlistTV);
        container.setSpacing(14);

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        container.setMaxWidth(tableWidth);
        container.setPadding(new Insets(20, 0, 0, 0));
        setCenter(container);

        wishlistTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / wishlistTV.getColumns().size());
        });
    }

    @Override
    public void setStyle() {
        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 32px; -fx-font-weight: bolder;");
    }

    @Override
    public void setEvent() {

    }

    private Callback<TableColumn<WishlistViewModel, Void>, TableCell<WishlistViewModel, Void>> createActionCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<WishlistViewModel, Void> call(final TableColumn<WishlistViewModel, Void> param) {
                return new TableCell<>() {
                    private final Button removeBtn = new Button("Remove");
                    {
                        removeBtn.setOnAction(event -> {
                            WishlistViewModel wishlistVM = getTableView().getItems().get(getIndex());
                            Response<Wishlist> response = wishlistController.removeWishlist(wishlistVM.getWishlist().getWishlistId());
                            if (!response.isSuccess()) {
                                AlertHelper.showError("Item Wishlist Removal", response.getMessage());
                                return;
                            }

                            AlertHelper.showInfo("Item Wishlist Removal", response.getMessage());
                            createOrRefreshPage();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            HBox actionButtons = new HBox(removeBtn);
                            actionButtons.setSpacing(10);
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        };
    }

    private void fetchData(){

    }

    private static ViewWishlistPage instance;


    public static ViewWishlistPage getInstance() {
        return instance = (instance == null) ? new ViewWishlistPage() : instance;
    }

    private ViewWishlistPage() {
        this.transactionController = TransactionController.getInstance();
        this.wishlistController = WishlistController.getInstance();
        createOrRefreshPage();
    }
}
