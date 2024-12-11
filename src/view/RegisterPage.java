package view;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lib.manager.PageManager;
import lib.response.Response;
import model.User;
//import utils.AlertHelper;
import view.base.Page;

public final class RegisterPage extends Page {

    private final UserController _userController;

    private VBox container;

    private Label pageLbl;
    private Label titleLbl;

    private VBox usernameContainer;
    private Label usernameLbl;
    private TextField usernameTf;

    private VBox passwordContainer;
    private Label passwordLbl;
    private PasswordField passwordPf;

    private VBox phoneNumberContainer;
    private Label phoneNumberLbl;
    private TextField phoneNumberTf;

    private VBox addressContainer;
    private Label addressLbl;
    private TextField addressTf;

    private VBox roleContainer;
    private Label roleLbl;
    private HBox radioContainer;
    private ToggleGroup roleGroup;
    private RadioButton buyerRadioBtn;
    private RadioButton sellerRadioBtn;
    
    private Label errorLbl;

    private Button submitBtn;

    private Hyperlink loginHl;

    @Override
    public void init() {
        container = new VBox();

        pageLbl = new Label("CaLouselF");
        titleLbl = new Label("Register Page");

        usernameContainer = new VBox();
        usernameLbl = new Label("Username");
        usernameTf = new TextField();

        passwordContainer = new VBox();
        passwordLbl = new Label("Password");
        passwordPf = new PasswordField();

        phoneNumberContainer = new VBox();
        phoneNumberLbl = new Label("Phone Number");
        phoneNumberTf = new TextField();

        addressContainer = new VBox();
        addressLbl = new Label("Address");
        addressTf = new TextField();

        roleContainer = new VBox();
        roleLbl = new Label("Role");
        radioContainer = new HBox();
        roleGroup = new ToggleGroup();
        buyerRadioBtn = new RadioButton("Buyer");
        sellerRadioBtn = new RadioButton("Seller");
        
        errorLbl = new Label();

        submitBtn = new Button("Submit");
        loginHl = new Hyperlink("Login");
    }

    @Override
    public void setLayout() {
        usernameContainer.getChildren().addAll(usernameLbl, usernameTf);
        usernameContainer.setSpacing(8);

        passwordContainer.getChildren().addAll(passwordLbl, passwordPf);
        passwordContainer.setSpacing(8);

        phoneNumberContainer.getChildren().addAll(phoneNumberLbl, phoneNumberTf);
        phoneNumberContainer.setSpacing(8);

        addressContainer.getChildren().addAll(addressLbl, addressTf);
        addressContainer.setSpacing(8);

        roleContainer.getChildren().addAll(roleLbl, radioContainer);
        roleContainer.setSpacing(8);

        radioContainer.getChildren().addAll(buyerRadioBtn, sellerRadioBtn);
        radioContainer.setSpacing(8);
        buyerRadioBtn.setToggleGroup(roleGroup);
        sellerRadioBtn.setToggleGroup(roleGroup);

        container.getChildren().addAll(pageLbl, titleLbl, usernameContainer, passwordContainer, phoneNumberContainer, addressContainer, roleContainer, errorLbl, submitBtn, loginHl);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(14);
        container.setMaxWidth(600);

        this.setCenter(container);
    }

    @Override
    public void setStyle() {
        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 64px; -fx-font-weight: bolder; -fx-font-style: italic");
        
        titleLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-font-weight: bold");
        
        errorLbl.setTextFill(Color.RED);
        errorLbl.setVisible(false);
    }

    @Override
    public void setEvent() {
        submitBtn.setOnMouseClicked(e -> {
            register();
        });

        loginHl.setOnMouseClicked(e -> {
            navigateToLogin();
        });
    }
    
    private void navigateToLogin() {
    	PageManager.changePage(LoginPage.getInstance(), "Login Page");
    }

    private void register() {
        String selectedRole = "";
        if (roleGroup.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) roleGroup.getSelectedToggle();
            selectedRole = selectedRadioButton.getText();
        }

        Response<User> response = _userController.register(usernameTf.getText(), passwordPf.getText(), phoneNumberTf.getText(), addressTf.getText(), selectedRole);
        
        if (!response.isSuccess()) {
//            AlertHelper.showError("Error", response.message);
            errorLbl.setText(response.getMessage());
            errorLbl.setVisible(true);
            return;
        }

        errorLbl.setText("");
        errorLbl.setVisible(false);
        navigateToLogin();
    }

    private static RegisterPage instance;

    public static RegisterPage getInstance() {
        return instance = (instance == null) ? new RegisterPage() : instance;
    }

    private RegisterPage() {
        createOrRefreshPage();
        _userController = UserController.getInstance();
    }

}
