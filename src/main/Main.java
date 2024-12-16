package main;

import javafx.application.Application;
import javafx.stage.Stage;
import lib.db.Migration;
import lib.db.Seeder;
import lib.manager.PageManager;

public final class Main extends Application {

    private final boolean runMigrateAndSeeder = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if(runMigrateAndSeeder){
            Migration.run();
            Seeder.run();
        }

        PageManager.initialize(stage);
    }

}
