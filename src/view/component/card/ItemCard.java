package view.component.card;

import config.AppConfig;
import controller.ItemController;
import controller.OfferController;
import controller.TransactionController;
import controller.WishlistController;
import enums.UserRole;
import interfaces.IComponent;
import javafx.geometry.Insets;
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
import model.Wishlist;
import utils.AlertHelper;
import view.page.HomePage;
import view.page.seller.EditItemPage;

import java.util.Optional;

public final class ItemCard extends BorderPane implements IComponent {

    private final ItemController itemController;
    private final TransactionController transactionController;
    private final OfferController offerController;
    private final WishlistController wishlistController;

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
    private Button wishlistBtn;

    private TextInputDialog offerTID;

    private HBox bottomContainer;

    private final Item item;
    private final boolean isOwner;

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
        } else if (SessionManager.getCurrentUser().getRole() == UserRole.BUYER) {
            buyBtn = new Button("Buy");
            offerBtn = new Button("Make Offer");
            offerTID = new TextInputDialog();
            wishlistBtn = new Button("Wishlist");
        }

        container = new VBox();
    }

    @Override
    public void setLayout() {
        container.getChildren().addAll(itemNameLbl, itemCategoryLbl, itemSizeLbl);

        bottomContainer.getChildren().addAll(btnContainer, itemPriceLbl);

        if (isOwner) {
            btnContainer.getChildren().addAll(editBtn, deleteBtn);
        } else if (SessionManager.getCurrentUser().getRole() == UserRole.BUYER) {
            btnContainer.getChildren().addAll(buyBtn, offerBtn, wishlistBtn);
            offerTID.setHeaderText("");
            offerTID.setTitle("Make Offer");
        }

        setCenter(container);
        setBottom(bottomContainer);
    }

    @Override
    public void setStyle() {
        setPrefSize(AppConfig.ITEM_CARD_WIDTH, AppConfig.ITEM_CARD_HEIGHT);
        setPadding(new Insets(10));
        setStyle(
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
        } else if (SessionManager.getCurrentUser().getRole() == UserRole.BUYER) {
            buyBtn.setOnMouseClicked(e -> {
                buy();
            });

            offerBtn.setOnMouseClicked(e -> {
                makeOffer();
            });

            wishlistBtn.setOnMouseClicked(e -> {
                wishlist();
            });
        }
    }

    private void delete() {
        boolean isConfirmed = AlertHelper.showConfirmation("Item Deletion", "Are you sure want to delete this item?");

        if (!isConfirmed) {
            return;
        }

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
            AlertHelper.showError("Operation Failed", "Cannot get highest offer, please try again later.");
            return;
        }

        Offer highestOffer = highestOfferResponse.getData();

        offerTID.setContentText(String.format("Input your offer (highest offer: %d): ", highestOffer == null ? 0 : highestOffer.getOfferPrice()));

        Optional<String> result = offerTID.showAndWait();

        result.ifPresent(input -> {
            Response<Offer> response = offerController.createOffer(item.getItemId(), SessionManager.getCurrentUser().getUserId(), input);

            if (!response.isSuccess()) {
                AlertHelper.showError("Invalid Input", response.getMessage());
                return;
            }

            AlertHelper.showInfo("Make Offer", response.getMessage());
            offerTID.close();
        });

    }

    private void buy() {
        boolean isConfirmed = AlertHelper.showConfirmation("Item Buy", "Are you sure want to buy this item?");

        if (!isConfirmed) {
            return;
        }

        Response<Transaction> response = transactionController.purchaseItem(SessionManager.getCurrentUser().getUserId(), item.getItemId());

        if (!response.isSuccess()) {
            AlertHelper.showError("Item Buy", response.getMessage());
            return;
        }

        AlertHelper.showInfo("Item Buy", response.getMessage());
    }

    private void wishlist() {
        boolean isConfirmed = AlertHelper.showConfirmation("Item Wishlist", "Are you sure want to wishlist this item?");

        if (!isConfirmed) {
            return;
        }

        Response<Wishlist> response = wishlistController.addWishlist(SessionManager.getCurrentUser().getUserId(), item.getItemId());

        if (!response.isSuccess()) {
            AlertHelper.showError("Item Wishlist", response.getMessage());
            return;
        }

        AlertHelper.showInfo("Item Wishlist", response.getMessage());
    }

    public ItemCard(Item item, boolean isOwned) {
        this.item = item;
        this.isOwner = isOwned;
        this.itemController = ItemController.getInstance();
        this.transactionController = TransactionController.getInstance();
        this.offerController = OfferController.getInstance();
        this.wishlistController = WishlistController.getInstance();
        init();
        setLayout();
        setStyle();
        setEvent();
    }

}
