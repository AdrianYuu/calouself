package main;

import javafx.application.Application;
import javafx.stage.Stage;
import lib.db.Migration;
import lib.db.Seeder;
import lib.manager.PageManager;

public final class Main extends Application {

    /**
     * Flag to control whether database migration and seeding should be run at startup.
     * This value can only be true or false.
     */
    private final boolean runMigrateAndSeeder = true;

    /**
     * Main entry point for launching the JavaFX application.
     *
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes and starts the JavaFX application.
     *
     * @param stage the primary stage for this JavaFX application
     * @throws Exception if there is an error during the startup process
     */
    @Override
    public void start(Stage stage) throws Exception {
        if(runMigrateAndSeeder){
            Migration.run();
            Seeder.run();
        }

        PageManager.initialize(stage);
    }

}
