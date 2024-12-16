package lib.manager;

import config.AppConfig;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.page.auth.LoginPage;
import view.page.base.Page;

import java.util.Objects;

public final class PageManager {

    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(PageManager.class.getResource(AppConfig.APP_ICON_URL)).toExternalForm()));
        primaryStage.setScene(new Scene(LoginPage.getInstance(), AppConfig.SCREEN_WIDTH, AppConfig.SCREEN_HEIGHT));
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
