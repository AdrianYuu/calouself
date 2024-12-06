package lib.manager;

import javafx.scene.Scene;
import javafx.stage.Stage;
import view.RegisterPage;

public final class PageManager {

    private static Stage primaryStage;

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;

        // Set the initial page to RegisterPage <Adrian>
        primaryStage.setScene(RegisterPage.getInstance().getScene());
        primaryStage.show();
    }

    public static Scene getScene() {
        return primaryStage.getScene();
    }

    public static void setScene(Scene scene) {
        primaryStage.setScene(scene);
    }

}
