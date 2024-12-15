package view.seller;

import config.AppConfig;
import controller.ItemController;
import controller.OfferController;
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
import utils.AlertHelper;
import view.base.Page;
import view.component.navbar.NavigationBar;

import java.util.List;
import java.util.Optional;

public class ItemOffersPage extends Page {
    private final ItemController itemController;
    private final OfferController offerController;

    private ObservableList<Offer> offers;

    private VBox container;

    private Label pageLbl;

    private TableView offerTV;
    private TableColumn<Offer, String> nameColumn;
    private TableColumn<Offer, String> categoryColumn;
    private TableColumn<Offer, String> sizeColumn;
    private TableColumn<Offer, Integer> initialPriceColumn;
    private TableColumn<Offer, String> offeredPriceColumn;
    private TableColumn<Offer, Void> actionsColumn;


    private TextInputDialog declineTD;

    @Override
    public void init() {
        Response<List<Offer>> response = offerController.getPendingOffers(SessionManager.getCurrentUser().getUserId());

        if (response.isSuccess()) {
            offers = FXCollections.observableArrayList(response.getData());
        }
        else {
            offers = FXCollections.emptyObservableList();
        }

        container = new VBox();

        pageLbl = new Label("Item Offers");

        offerTV = new TableView<>();

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

        categoryColumn = new TableColumn<>("Category");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));

        initialPriceColumn = new TableColumn<>("Initial Price");
        initialPriceColumn.setCellValueFactory(new PropertyValueFactory<>("itemPrice"));

        offeredPriceColumn = new TableColumn<>("Offered Price");
        offeredPriceColumn.setCellValueFactory(new PropertyValueFactory<>("offerPrice"));

        actionsColumn = new TableColumn<>("Actions");
        actionsColumn.setCellFactory(createActionCellFactory());

        offerTV.getColumns().addAll(nameColumn, sizeColumn, categoryColumn, initialPriceColumn, offeredPriceColumn, actionsColumn);

        offerTV.setItems(offers);
    }

    private Callback<TableColumn<Offer, Void>, TableCell<Offer, Void>> createActionCellFactory() {
        return new Callback<>() {
            @Override
            public TableCell<Offer, Void> call(final TableColumn<Offer, Void> param) {
                return new TableCell<>() {
                    private final Button acceptBtn = new Button("Accept");
                    private final Button declineBtn = new Button("Decline");

                    {
                        acceptBtn.setOnAction(event -> {
                            Offer offer = getTableView().getItems().get(getIndex());
                            Response<Offer> response = offerController.acceptOffer(offer.getOfferId());

                            if (!response.isSuccess()) {
                                AlertHelper.showError("Operation Failed", response.getMessage());
                                return;
                            }

                            AlertHelper.showInfo("Operation Success", response.getMessage());
                            createOrRefreshPage();
                        });


                        declineBtn.setOnAction(event -> {
                            Offer offer = getTableView().getItems().get(getIndex());

                            Optional<String> result = declineTD.showAndWait();

                            result.ifPresent(reason -> {
                                Response<Offer> response = offerController.declineOffer(offer.getOfferId(), reason);

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

    @Override
    public void setLayout() {
        setTop(NavigationBar.getNavigationBar());
        container.getChildren().addAll(pageLbl, offerTV);
        container.setSpacing(14);

        double tableWidth = AppConfig.SCREEN_WIDTH * 0.8;

        container.setMaxWidth(tableWidth);
        container.setPadding(new Insets(20, 0, 0, 0));
        setCenter(container);

        offerTV.getColumns().forEach(col -> {
            ((TableColumn<?, ?>) col).setMinWidth(tableWidth / offerTV.getColumns().size());
        });
    }

    @Override
    public void setStyle() {
        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 32px; -fx-font-weight: bolder;");
    }

    @Override
    public void setEvent() {

    }

    private static ItemOffersPage instance;

    public static ItemOffersPage getInstance() {
        return instance = (instance == null) ? new ItemOffersPage() : instance;
    }

    private ItemOffersPage() {
        itemController = ItemController.getInstance();
        offerController = OfferController.getInstance();

        createOrRefreshPage();
    }
}
