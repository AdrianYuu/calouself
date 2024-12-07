package view;

import interfaces.IComponent;
import view.base.Page;

public final class LoginPage extends Page implements IComponent {

    @Override
    public void init() {
    }

    @Override
    public void setLayout() {

    }

    @Override
    public void setStyle() {

    }

    @Override
    public void setEvent() {
    }

    @Override
    public void createOrRefreshPage() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }

    private static LoginPage instance;

    public static LoginPage getInstance() {
        return instance = (instance == null) ? new LoginPage() : instance;
    }

    private LoginPage() {
        createOrRefreshPage();
    }

}
