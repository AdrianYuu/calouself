package view;

import view.base.Page;
import view.component.SellerNavigationBar;

public final class HomePage extends Page {

    @Override
    public void init() {
        this.setTop(SellerNavigationBar.create());
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
    private static HomePage instance;

    public static HomePage getInstance() {
        return instance = (instance == null) ? new HomePage() : instance;
    }

    private HomePage() {
        createOrRefreshPage();
    }

}
