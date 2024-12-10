package view;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import lib.manager.PageManager;
import view.base.Page;

public final class LoginPage extends Page {

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

    private Button submitBtn;

    private Hyperlink registerHl;

    @Override
    public void init() {
        container = new VBox();

        pageLbl = new Label("CaLouselF");
        titleLbl = new Label("Login Page");

        usernameContainer = new VBox();
        usernameLbl = new Label("Username");
        usernameTf = new TextField();

        passwordContainer = new VBox();
        passwordLbl = new Label("Password");
        passwordPf = new PasswordField();

        submitBtn = new Button("Submit");

        registerHl = new Hyperlink("Register");
    }

    @Override
    public void setLayout() {
        usernameContainer.getChildren().addAll(usernameLbl, usernameTf);
        usernameContainer.setSpacing(8);

        passwordContainer.getChildren().addAll(passwordLbl, passwordPf);
        passwordContainer.setSpacing(8);

        container.getChildren().addAll(pageLbl, titleLbl, usernameContainer, passwordContainer, submitBtn, registerHl);
        container.setAlignment(Pos.CENTER);
        container.setSpacing(14);
        container.setMaxWidth(600);

        this.setCenter(container);
    }

    @Override
    public void setStyle() {
        pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 64px; -fx-font-weight: bolder; -fx-font-style: italic");
        titleLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-font-weight: bold");
    }

    @Override
    public void setEvent() {
        submitBtn.setOnMouseClicked(e -> {
            login();
        });

        registerHl.setOnMouseClicked(e -> {
            PageManager.changePage(RegisterPage.getInstance(), "Register Page");
        });
    }

    private void login() {
    }

    private static LoginPage instance;

    public static LoginPage getInstance() {
        return instance = (instance == null) ? new LoginPage() : instance;
    }

    private LoginPage() {
        createOrRefreshPage();
        _userController = UserController.getInstance();
    }

}
