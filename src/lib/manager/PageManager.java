package lib.manager;

import config.AppConfig;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.RegisterPage;
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

        primaryStage.setTitle("CaLouselF");

        primaryStage.show();
    }

    public static void setSceneRoot(Page root, String pageTitle) {
        primaryStage.getScene().setRoot(root);
        primaryStage.setTitle(pageTitle);
        root.getPage().createOrRefreshPage();
    }

}
