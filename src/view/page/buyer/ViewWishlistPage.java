package view.page.buyer;

import config.AppConfig;
import controller.WishlistController;
import enums.UserRole;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Wishlist;
import utils.AlertHelper;
import view.page.auth.LoginPage;
import view.page.base.Page;
import view.component.navbar.NavigationBar;
import viewmodel.WishlistViewModel;

import java.util.List;

public final class ViewWishlistPage extends Page {
    private final WishlistController wishlistController;

    private VBox container;
    private Label pageLbl;

    private TableView<WishlistViewModel> wishlistTV;
    private TableColumn<WishlistViewModel, String> wishlistIdColumn;
    private TableColumn<WishlistViewModel, String> nameColumn;
    private TableColumn<WishlistViewModel, String> sizeColumn;
    private TableColumn<WishlistViewModel, String> priceColumn;
    private TableColumn<WishlistViewModel, String> categoryColumn;
    private TableColumn<WishlistViewModel, Void> actionsColumn;

    private ObservableList<WishlistViewModel> wishlists;

    @Override
    public void init() {
        Response<List<WishlistViewModel>> response = wishlistController.viewWishlist(SessionManager.getCurrentUser().getUserId());
        wishlists = response.isSuccess() ? FXCollections.observableArrayList(response.getData()) : FXCollections.emptyObservableList();

        container = new VBox();
        pageLbl = new Label("My Wishlists");

        wishlistTV = new TableView<>();

        wishlistIdColumn = new TableColumn<>("Wishlist Id");
        nameColumn = new TableColumn<>("Name");
        sizeColumn = new TableColumn<>("Size");
        priceColumn = new TableColumn<>("Price");
        categoryColumn = new TableColumn<>("Category");
        actionsColumn = new TableColumn<>("Actions");
    }

    public void setLayout() {
        wishlistIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWishlist().getWishlistId()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemName()));
        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemSize()));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemPrice().toString()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemCategory()));
        actionsColumn.setCellFactory(createActionCellFactory());

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        wishlistTV.getColumns().addAll(wishlistIdColumn, nameColumn, sizeColumn, priceColumn, categoryColumn, actionsColumn);
        wishlistTV.setItems(wishlists);
        wishlistTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / wishlistTV.getColumns().size());
        });

        container.getChildren().addAll(pageLbl, wishlistTV);
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
                                AlertHelper.showError("Operation Failed", response.getMessage());
                                return;
                            }

                            AlertHelper.showInfo("Operation Success", response.getMessage());
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

    private static ViewWishlistPage instance;

    public static ViewWishlistPage getInstance() {
        return instance = (instance == null) ? new ViewWishlistPage() : instance;
    }

    private ViewWishlistPage() {
        this.wishlistController = WishlistController.getInstance();
        createOrRefreshPage();
    }

    @Override
    public void check() {
        if(SessionManager.getCurrentUser() == null || !SessionManager.getCurrentUser().getRole().equals(UserRole.BUYER)){
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        }
    }

}
