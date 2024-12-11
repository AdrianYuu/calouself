package view;

import controller.UserController;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lib.manager.PageManager;
import lib.response.Response;
import model.User;
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
    
    private Label errorLbl;

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
        
        errorLbl = new Label();

        submitBtn = new Button("Submit");

        registerHl = new Hyperlink("Register");
    }

    @Override
    public void setLayout() {
        usernameContainer.getChildren().addAll(usernameLbl, usernameTf);
        usernameContainer.setSpacing(8);

        passwordContainer.getChildren().addAll(passwordLbl, passwordPf);
        passwordContainer.setSpacing(8);

        container.getChildren().addAll(pageLbl, titleLbl, usernameContainer, passwordContainer, errorLbl, submitBtn, registerHl);
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
            login();
        });

        registerHl.setOnMouseClicked(e -> {
            PageManager.changePage(RegisterPage.getInstance(), "Register Page");
        });
    }

    private void login() {
    	Response<User> response = _userController.login(usernameTf.getText(), passwordPf.getText());

    	if (!response.isSuccess()) {
    		errorLbl.setText(response.getMessage());
    		errorLbl.setVisible(true);
            return;
    	}
    	
    	PageManager.changePage(HomePage.getInstance(), "Home Page");
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
