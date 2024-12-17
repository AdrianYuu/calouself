package view.page.seller;

import config.AppConfig;
import controller.OfferController;
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
import model.Item;
import model.Offer;
import utils.AlertHelper;
import view.page.auth.LoginPage;
import view.page.base.Page;
import view.component.navbar.NavigationBar;
import viewmodel.OfferViewModel;

import java.util.List;
import java.util.Optional;

public final class OfferItemPage extends Page {

    private final OfferController offerController;

    private ObservableList<OfferViewModel> offers;

    private VBox container;

    private Label pageLbl;

    private TableView<OfferViewModel> offerTV;
    private TableColumn<OfferViewModel, String> nameColumn;
    private TableColumn<OfferViewModel, String> categoryColumn;
    private TableColumn<OfferViewModel, String> sizeColumn;
    private TableColumn<OfferViewModel, String> initialPriceColumn;
    private TableColumn<OfferViewModel, String> offeredPriceColumn;
    private TableColumn<OfferViewModel, Void> actionsColumn;

    private TextInputDialog declineTID;

    @Override
    public void init() {
        Response<List<OfferViewModel>> response = offerController.viewPendingOffers(SessionManager.getCurrentUser().getUserId());
        offers = response.isSuccess() ? FXCollections.observableArrayList(response.getData()) : FXCollections.emptyObservableList();

        container = new VBox();
        pageLbl = new Label("Item Offers");

        offerTV = new TableView<>();

        nameColumn = new TableColumn<>("Name");
        sizeColumn = new TableColumn<>("Size");
        categoryColumn = new TableColumn<>("Category");
        initialPriceColumn = new TableColumn<>("Initial Price");
        offeredPriceColumn = new TableColumn<>("Offered Price");
        actionsColumn = new TableColumn<>("Actions");

        declineTID = new TextInputDialog();
    }

    @Override
    public void setLayout() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemName()));
        sizeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemSize()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemCategory()));
        initialPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getItem().getItemPrice().toString()));
        offeredPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOffer().getOfferPrice().toString()));
        actionsColumn.setCellFactory(createActionCellFactory());

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        offerTV.getColumns().addAll(nameColumn, sizeColumn, categoryColumn, initialPriceColumn, offeredPriceColumn, actionsColumn);
        offerTV.setItems(offers);
        offerTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / offerTV.getColumns().size());
        });

        declineTID.setTitle("Decline Reason");
        declineTID.setContentText("Enter decline reason:");
        declineTID.setHeaderText("");
        declineTID.setGraphic(null);

        container.getChildren().addAll(pageLbl, offerTV);
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

    private Callback<TableColumn<OfferViewModel, Void>, TableCell<OfferViewModel, Void>> createActionCellFactory() {
        return new Callback<TableColumn<OfferViewModel, Void>, TableCell<OfferViewModel, Void>>() {
            @Override
            public TableCell<OfferViewModel, Void> call(final TableColumn<OfferViewModel, Void> param) {
                return new TableCell<OfferViewModel, Void>() {
                    private final Button acceptBtn = new Button("Accept");
                    private final Button declineBtn = new Button("Decline");

                    {
                        acceptBtn.setOnAction(event -> {
                            OfferViewModel offerVM = getTableView().getItems().get(getIndex());
                            Response<Offer> response = offerController.acceptOffer(offerVM.getOffer().getOfferId());

                            if (!response.isSuccess()) {
                                AlertHelper.showError("Operation Failed", response.getMessage());
                                return;
                            }

                            AlertHelper.showInfo("Operation Success", response.getMessage());
                            createOrRefreshPage();
                        });


                        declineBtn.setOnAction(event -> {
                            OfferViewModel offerVM = getTableView().getItems().get(getIndex());

                            Optional<String> result = declineTID.showAndWait();

                            result.ifPresent(reason -> {
                                Response<Offer> response = offerController.declineOffer(offerVM.getOffer().getOfferId(), reason);

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
                            HBox actionButtons = new HBox(acceptBtn, declineBtn);
                            actionButtons.setSpacing(10);
                            setGraphic(actionButtons);
                        }
                    }
                };
            }
        };
    }

    private static OfferItemPage instance;

    public static OfferItemPage getInstance() {
        return instance = (instance == null) ? new OfferItemPage() : instance;
    }

    private OfferItemPage() {
        this.offerController = OfferController.getInstance();
        createOrRefreshPage();
    }

    @Override
    public void check() {
        if(SessionManager.getCurrentUser() == null || !SessionManager.getCurrentUser().getRole().equals(UserRole.SELLER)){
            PageManager.changePage(LoginPage.getInstance(), "Login Page");
        }
    }

}
