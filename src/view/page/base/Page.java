package view.page.base;

import interfaces.IComponent;
import interfaces.IMiddleware;
import javafx.scene.layout.BorderPane;

public abstract class Page extends BorderPane implements IComponent, IMiddleware {

    public void createOrRefreshPage() {
        check();
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
