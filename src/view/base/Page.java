package view.base;

import interfaces.IComponent;
import javafx.scene.Scene;

public abstract class Page implements IComponent{

    private Scene scene;

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void createOrRefreshPage() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }


}
