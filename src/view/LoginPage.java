package view;

import config.AppConfig;
import interfaces.IComponent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lib.manager.PageManager;
import view.base.Page;

public final class LoginPage extends Page implements IComponent {
	
	private BorderPane mainContainer;
	private VBox container;
	
	private VBox usernameContainer;
	
	private Label pageLbl;
	private Label titleLbl;
	
	private Label usernameLbl;
    private TextField usernameTf;
    
    private VBox passwordContainer;
    private Label passwordLbl;
    private PasswordField passwordPf;
    
    private Button loginBtn;
    
    private Hyperlink registerHl;
    
	
    @Override
    public void init() {
    	mainContainer = new BorderPane();
    	container = new VBox();
    	
    	pageLbl = new Label("CaLouselF");
        titleLbl = new Label("Login Page");
        
    	usernameContainer = new VBox();
    	usernameLbl = new Label("Username");
    	usernameTf = new TextField();
    	
    	passwordContainer = new VBox();
    	passwordLbl = new Label("Password");
    	passwordPf = new PasswordField();
    	
    	loginBtn = new Button("Login");
    	
    	registerHl = new Hyperlink("Register");
    }

    @Override
    public void setLayout() {
    	usernameContainer.getChildren().addAll(usernameLbl, usernameTf);
        usernameContainer.setSpacing(8);

        passwordContainer.getChildren().addAll(passwordLbl, passwordPf);
        passwordContainer.setSpacing(8);
        
        container.getChildren().addAll(pageLbl, titleLbl, usernameContainer, passwordContainer, loginBtn, registerHl);
        
        container.setAlignment(Pos.CENTER);
        container.setSpacing(14);
        container.setMaxWidth(600);
        
        mainContainer.setCenter(container);
        
        setScene(new Scene(mainContainer, AppConfig.SCREEN_WIDTH, AppConfig.SCREEN_HEIGHT));
        
    }

    @Override
    public void setStyle() {
    	pageLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 64px; -fx-font-weight: bolder; -fx-font-style: italic");
        titleLbl.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-font-weight: bold");
    }

    @Override
    public void setEvent() {
    	registerHl.setOnMouseClicked(e -> {
    		PageManager.setScene(RegisterPage.getInstance().getScene());
    		createOrRefreshPage();
    	});
    }

 
    private static LoginPage instance;

    public static LoginPage getInstance() {
    	return instance = (instance == null) ? new LoginPage() : instance;
    }

    private LoginPage() {
        createOrRefreshPage();
    }

}
