package view.base;

import javafx.scene.Scene;

public abstract class Page {

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public abstract void createOrRefreshPage();

}
