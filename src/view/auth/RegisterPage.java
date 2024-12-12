package view.auth;

import controller.UserController;
import enums.UserRole;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lib.manager.PageManager;
import lib.response.Response;
import model.User;
import utils.AlertHelper;
import view.base.Page;

import java.util.ArrayList;

public final class RegisterPage extends Page {

    private final UserController userController;

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
    private ArrayList<RadioButton> rolesRadioBtn;

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
        rolesRadioBtn = new ArrayList<>();

        for (UserRole role : UserRole.values()) {
            if (role.equals(UserRole.ADMIN)) continue;
            RadioButton btn = new RadioButton(role.toString());
            btn.setToggleGroup(roleGroup);
            rolesRadioBtn.add(btn);
        }

        errorLbl = new Label();

        submitBtn = new Button("Submit");
        loginHl = new Hyperlink("Login");
    }

    @Override
    public void setLayout() {
        usernameContainer.getChildren().addAll(usernameLbl, usernameTf);
        passwordContainer.getChildren().addAll(passwordLbl, passwordPf);
        phoneNumberContainer.getChildren().addAll(phoneNumberLbl, phoneNumberTf);
        addressContainer.getChildren().addAll(addressLbl, addressTf);
        roleContainer.getChildren().addAll(roleLbl, radioContainer);
        radioContainer.getChildren().addAll(rolesRadioBtn);
        container.getChildren().addAll(pageLbl, titleLbl, usernameContainer, passwordContainer, phoneNumberContainer, addressContainer, roleContainer, errorLbl, submitBtn, loginHl);
        setCenter(container);
    }

    @Override
    public void setStyle() {
        usernameContainer.setSpacing(8);
        passwordContainer.setSpacing(8);
        phoneNumberContainer.setSpacing(8);
        addressContainer.setSpacing(8);
        roleContainer.setSpacing(8);
        radioContainer.setSpacing(8);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(14);
        container.setMaxWidth(600);
        errorLbl.setTextFill(Color.RED);
        errorLbl.setVisible(false);

        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 64px; -fx-font-weight: bolder; -fx-font-style: italic");
        titleLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-font-weight: bold");
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
        UserRole selectedRole = null;

        if (roleGroup.getSelectedToggle() != null) {
            RadioButton selectedRadioButton = (RadioButton) roleGroup.getSelectedToggle();
            String roleString = selectedRadioButton.getText();

            for (UserRole role : UserRole.values()) {
                if (role.toString().equals(roleString)) {
                    selectedRole = role;
                    break;
                }
            }
        }

        Response<User> response = userController.register(usernameTf.getText(), passwordPf.getText(), phoneNumberTf.getText(), addressTf.getText(), selectedRole);

        if (!response.isSuccess()) {
            errorLbl.setText(response.getMessage());
            errorLbl.setVisible(true);
            return;
        }

        AlertHelper.showInfo("Register Success", "Your new account is registered successfully.");
        navigateToLogin();
    }

    private static RegisterPage instance;

    public static RegisterPage getInstance() {
        return instance = (instance == null) ? new RegisterPage() : instance;
    }

    private RegisterPage() {
        createOrRefreshPage();
        userController = UserController.getInstance();
    }

}
