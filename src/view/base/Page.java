package view.base;

import interfaces.IComponent;
<<<<<<< HEAD
import javafx.scene.Scene;

public abstract class Page implements IComponent{
=======
import javafx.scene.layout.BorderPane;

public abstract class Page extends BorderPane implements IComponent {
>>>>>>> b0c21f5fb1a47e6e68ca48faa2ceed8a9e479d66

    public void createOrRefreshPage() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }

    public Page getPage() {
        return this;
    }

<<<<<<< HEAD
    public void createOrRefreshPage() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }


=======
>>>>>>> b0c21f5fb1a47e6e68ca48faa2ceed8a9e479d66
}
