package lib.manager;

import config.AppConfig;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.auth.RegisterPage;
import view.base.Page;

import java.util.Objects;

public final class PageManager {

    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;
        Image appIcon = new Image(Objects.requireNonNull(PageManager.class.getResource("/assets/app_icon.png")).toExternalForm());
        primaryStage.getIcons().add(appIcon);
        primaryStage.setScene(new Scene(RegisterPage.getInstance(), AppConfig.SCREEN_WIDTH, AppConfig.SCREEN_HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.setTitle(AppConfig.APP_NAME);
        primaryStage.show();
    }

    public static void changePage(Page page, String pageTitle) {
        primaryStage.getScene().setRoot(page);
        primaryStage.setTitle(pageTitle);
        page.getPage().createOrRefreshPage();
    }

}
