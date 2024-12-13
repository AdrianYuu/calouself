package view.seller;

import controller.ItemController;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lib.response.Response;
import model.Item;
import utils.AlertHelper;
import view.base.Page;
import view.component.navbar.NavigationBar;

public final class UploadItemPage extends Page {

    private final ItemController itemController;

    private VBox container;

    private Label pageLbl;

    private VBox itemNameContainer;
    private Label itemNameLbl;
    private TextField itemNameTf;

    private VBox itemCategoryContainer;
    private Label itemCategoryLbl;
    private TextField itemCategoryTf;

    private VBox itemSizeContainer;
    private Label itemSizeLbl;
    private TextField itemSizeTf;

    private VBox itemPriceContainer;
    private Label itemPriceLbl;
    private TextField itemPriceTf;

    private Label errorLbl;

    private Button submitBtn;

    @Override
    public void init() {
        setTop(NavigationBar.getNavigationBar());

        container = new VBox();

        pageLbl = new Label("Upload Item");

        itemNameContainer = new VBox();
        itemNameLbl = new Label("Item name");
        itemNameTf = new TextField();

        itemCategoryContainer = new VBox();
        itemCategoryLbl = new Label("Item category");
        itemCategoryTf = new TextField();

        itemSizeContainer = new VBox();
        itemSizeLbl = new Label("Item size");
        itemSizeTf = new TextField();

        itemPriceContainer = new VBox();
        itemPriceLbl = new Label("Item price");
        itemPriceTf = new TextField();

        errorLbl = new Label();

        submitBtn = new Button("Submit");
    }

    @Override
    public void setLayout() {
        itemNameContainer.getChildren().addAll(itemNameLbl, itemNameTf);
        itemCategoryContainer.getChildren().addAll(itemCategoryLbl, itemCategoryTf);
        itemSizeContainer.getChildren().addAll(itemSizeLbl, itemSizeTf);
        itemPriceContainer.getChildren().addAll(itemPriceLbl, itemPriceTf);
        container.getChildren().addAll(pageLbl, itemNameContainer, itemCategoryContainer, itemSizeContainer, itemPriceContainer, submitBtn, errorLbl);
        setCenter(container);
    }

    @Override
    public void setStyle() {
        itemNameContainer.setSpacing(8);
        itemCategoryContainer.setSpacing(8);
        itemSizeContainer.setSpacing(8);
        itemPriceContainer.setSpacing(8);
        container.setSpacing(14);
        container.setMaxWidth(600);
        container.setPadding(new Insets(20, 0, 0, 0));
        errorLbl.setTextFill(Color.RED);
        errorLbl.setVisible(false);

        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-font-weight: bold");
    }

    @Override
    public void setEvent() {
        submitBtn.setOnMouseClicked(e -> {
            uploadItem();
        });
    }

    private void uploadItem() {
        Response<Item> response = itemController.uploadItem(
                itemNameTf.getText(),
                itemSizeTf.getText(),
                itemPriceTf.getText(),
                itemCategoryTf.getText()
        );

        if (!response.isSuccess()) {
            errorLbl.setText(response.getMessage());
            errorLbl.setVisible(true);
            return;
        }

        errorLbl.setText("");
        errorLbl.setVisible(false);

        AlertHelper.showInfo("Upload Item Success", "Your item is uploaded successfully.");
        createOrRefreshPage();
    }

    private static UploadItemPage instance;

    public static UploadItemPage getInstance() {
        return instance = (instance == null) ? new UploadItemPage() : instance;
    }

    private UploadItemPage() {
        createOrRefreshPage();
        itemController = ItemController.getInstance();
    }
}
