package view.component;

import interfaces.IComponent;
import javafx.scene.control.MenuBar;

public abstract class NavigationBar extends MenuBar implements IComponent {

    public NavigationBar() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }
}
