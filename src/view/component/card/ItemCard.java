package view.component.card;

import config.AppConfig;
import controller.ItemController;
import controller.OfferController;
import controller.TransactionController;
import enums.UserRole;
import interfaces.IComponent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lib.manager.PageManager;
import lib.manager.SessionManager;
import lib.response.Response;
import model.Item;
import model.Offer;
import model.Transaction;
import utils.AlertHelper;
import view.HomePage;
import view.seller.EditItemPage;

import java.util.Optional;

public final class ItemCard extends BorderPane implements IComponent {

    private final ItemController itemController;
    private final TransactionController transactionController;
    private final OfferController offerController;

    private final Item item;
    private final boolean isOwner;

    private VBox container;

    private Label itemNameLbl;
    private Label itemCategoryLbl;
    private Label itemSizeLbl;
    private Label itemPriceLbl;

    private HBox btnContainer;
    private Button editBtn;
    private Button deleteBtn;
    private Button buyBtn;
    private Button offerBtn;

    private TextInputDialog offerTD;

    private HBox bottomContainer;

    @Override
    public void init() {
        itemNameLbl = new Label(item.getItemName());
        itemCategoryLbl = new Label(item.getItemCategory());
        itemSizeLbl = new Label(item.getItemSize());
        itemPriceLbl = new Label(Integer.toString(item.getItemPrice()));
        btnContainer = new HBox();
        bottomContainer = new HBox();

        if (isOwner) {
            editBtn = new Button("Edit");
            deleteBtn = new Button("Delete");
        } else if (SessionManager.getCurrentUser().getRole() != UserRole.ADMIN) {
            buyBtn = new Button("Buy");
            offerBtn = new Button("Make offer");
            offerTD = new TextInputDialog();

            offerTD.setHeaderText("");
            offerTD.setTitle("Make offer");
        }

        container = new VBox();
    }

    @Override
    public void setLayout() {
        container.getChildren().addAll(itemNameLbl, itemCategoryLbl, itemSizeLbl);
        setCenter(container);
        setBottom(bottomContainer);
        bottomContainer.getChildren().addAll(itemPriceLbl, btnContainer);

        if (isOwner) {
            btnContainer.getChildren().addAll(editBtn, deleteBtn);
        } else if (SessionManager.getCurrentUser().getRole() != UserRole.ADMIN) {
            btnContainer.getChildren().addAll(buyBtn, offerBtn);
        }
    }

    @Override
    public void setStyle() {
        setPrefSize(AppConfig.ITEM_CARD_WIDTH, AppConfig.ITEM_CARD_HEIGHT);
        setPadding(new Insets(10));
        setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-color: #E0E0E0;" +
                        "-fx-border-width: 1px;" +
                        "-fx-border-radius: 10px;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 8, 0, 0, 2)"
        );

        itemNameLbl.setStyle(
                "-fx-font-size: 24px; " +
                        "-fx-font-weight: bolder"
        );

        itemCategoryLbl.setStyle(
                "-fx-font-size: 16px;" +
                        "-fx-text-fill: grey"
        );

        itemPriceLbl.setStyle(
                "-fx-font-size: 20px;" +
                        "-fx-text-fill: green;" +
                        "-fx-font-weight: bold"
        );

        container.setSpacing(8);
        btnContainer.setSpacing(8);
        bottomContainer.setSpacing(8);
    }

    @Override
    public void setEvent() {
        if (isOwner) {
            editBtn.setOnMouseClicked(e -> {
                PageManager.changePage(EditItemPage.getInstance(item), "Edit Item Page");
            });

            deleteBtn.setOnMouseClicked(e -> {
                delete();
            });
        } else if (SessionManager.getCurrentUser().getRole() != UserRole.ADMIN) {
            buyBtn.setOnMouseClicked(e -> {
                buy();
            });

            offerBtn.setOnMouseClicked(evt -> {
                makeOffer();
            });
        }
    }

    private void delete() {
        boolean isConfirmed = AlertHelper.showConfirmation("Item Deletion", "Are you sure want to delete this item?");
        if (!isConfirmed) return;
        Response<Item> response = itemController.deleteItem(item.getItemId());
        if (!response.isSuccess()) {
            AlertHelper.showError("Item Deletion", response.getMessage());
            return;
        }
        AlertHelper.showInfo("Item Deletion", response.getMessage());
        HomePage.getInstance().createOrRefreshPage();
    }

    private void makeOffer() {
        Response<Offer> highestOfferResponse = offerController.getItemHighestOffer(item.getItemId());

        if (!highestOfferResponse.isSuccess()) {
            AlertHelper.showError("Operation failed", "Cannot get highest offer, please try again later.");
            return;
        }

        Offer highestOffer = highestOfferResponse.getData();

        offerTD.setContentText(String.format("Input your offer (Highest offer: %d): ", highestOffer == null ? 0 : highestOffer.getOfferPrice()));

        Optional<String> result = offerTD.showAndWait();

        result.ifPresent(input -> {
            System.out.println(input);
            if (input.isBlank()) {
                AlertHelper.showError("Invalid Input", "Input cannot be empty");
                return;
            }

            try {
                int offerPrice = Integer.parseInt(input);
                Response<Offer> response =  offerController.createOffer(item.getItemId(), SessionManager.getCurrentUser().getUserId(), offerPrice);

                if (!response.isSuccess()) {
                    AlertHelper.showError("Operation Failed", response.getMessage());
                    return;
                }

                AlertHelper.showInfo("Operation Success", response.getMessage());

            } catch (NumberFormatException e) {
                AlertHelper.showError("Invalid Input", "Input must be a number");
            }
        });

    }

    private void buy() {
        boolean isConfirmed = AlertHelper.showConfirmation("Item Buy", "Are you sure want to buy this item?");
        if (!isConfirmed) return;
        Response<Transaction> response = transactionController.purchaseItem(SessionManager.getCurrentUser().getUserId(), item.getItemId());
        if (!response.isSuccess()) {
            AlertHelper.showError("Item Buy", response.getMessage());
            return;
        }
        AlertHelper.showInfo("Item Buy", response.getMessage());
    }

    public ItemCard(Item item, boolean isOwned) {
        this.item = item;
        this.isOwner = isOwned;
        this.itemController = ItemController.getInstance();
        this.transactionController = TransactionController.getInstance();
        this.offerController = OfferController.getInstance();
        init();
        setLayout();
        setStyle();
        setEvent();
    }

}
