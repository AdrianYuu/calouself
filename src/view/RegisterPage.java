package view;

import config.AppConfig;
import interfaces.IComponent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import lib.manager.PageManager;
import view.base.Page;

public final class RegisterPage extends Page implements IComponent {

    private BorderPane bp;
    private Button btn;

    private static RegisterPage instance;

    public static RegisterPage getInstance() {
        return instance = (instance == null) ? new RegisterPage() : instance;
    }

    private RegisterPage() {
        createOrRefreshPage();
    }

    @Override
    public void init() {
        btn = new Button("To Login");
        bp = new BorderPane(btn);

        this.setScene(new Scene(bp, AppConfig.SCREEN_WIDTH, AppConfig.SCREEN_HEIGHT));
    }

    @Override
    public void setLayout() {

    }

    @Override
    public void setStyle() {

    }

    @Override
    public void setEvent() {
        btn.setOnMouseClicked(e -> {
            PageManager.setScene(LoginPage.getInstance().getScene());
        });
    }

    @Override
    public void createOrRefreshPage() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }
}
