package main;

import javafx.application.Application;
import javafx.stage.Stage;
import lib.manager.PageManager;

public final class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        PageManager.initialize(stage);
    }

}
