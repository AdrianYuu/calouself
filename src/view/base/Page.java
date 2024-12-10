package view.base;

import interfaces.IComponent;
import javafx.scene.layout.BorderPane;

public abstract class Page extends BorderPane implements IComponent {

    public void createOrRefreshPage() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }

    public Page getPage() {
        return this;
    }

}
