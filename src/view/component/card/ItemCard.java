package view.component.card;

import config.AppConfig;
import interfaces.IComponent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Item;

public final class ItemCard extends BorderPane implements IComponent {

    private final Item item;
    private final boolean isOwned;

    private VBox container;

    private Label itemNameLbl;
    private Label itemCategoryLbl;
    private Label itemSizeLbl;
    private Label itemPriceLbl;

    private HBox btnContainer;
    private Button editBtn;
    private Button deleteBtn;

    @Override
    public void init() {
        itemNameLbl = new Label(item.getItemName());
        itemCategoryLbl = new Label(item.getItemCategory());
        itemSizeLbl = new Label(item.getItemSize());
        itemPriceLbl = new Label(item.getItemPrice());

        if (isOwned) {
            btnContainer = new HBox();
            editBtn = new Button("Edit");
            deleteBtn = new Button("Delete");
        }

        container = new VBox();
    }

    @Override
    public void setLayout() {
        container.getChildren().addAll(itemNameLbl, itemCategoryLbl, itemSizeLbl, itemPriceLbl);
        setCenter(container);

        if (isOwned) {
            setBottom(btnContainer);
            btnContainer.getChildren().addAll(editBtn, deleteBtn);
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

        if (isOwned) btnContainer.setSpacing(8);
    }

    @Override
    public void setEvent() {
    }

    public ItemCard(Item item, boolean isOwned) {
        this.item = item;
        this.isOwned = isOwned;
        init();
        setLayout();
        setStyle();
        setEvent();
    }

}
