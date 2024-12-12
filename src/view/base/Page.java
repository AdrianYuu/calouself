package view.base;

import interfaces.IComponent;
import javafx.scene.layout.BorderPane;

public abstract class Page extends BorderPane implements IComponent {

    public void createOrRefreshPage() {
        init();
        setLayout();
        setStyle();
        setEvent();
        this.setStyle("-fx-font-family: 'Arial'");
    }

    public Page getPage() {
        return this;
    }

}
