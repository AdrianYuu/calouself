package lib.manager;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.RegisterPage;

import java.util.Objects;

public final class PageManager {

    private static Stage primaryStage;

    public static void initialize(Stage stage) {
        primaryStage = stage;

        Image appIcon = new Image(Objects.requireNonNull(PageManager.class.getResource("/assets/app_icon.png")).toExternalForm());
        primaryStage.getIcons().add(appIcon);

        primaryStage.setScene(RegisterPage.getInstance().getScene());

        primaryStage.setResizable(false);

        primaryStage.setTitle("CaLouselF");

        primaryStage.show();
    }

    public static Scene getScene() {
        return primaryStage.getScene();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

}
