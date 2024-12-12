package view.component.navbar;

import enums.UserRole;
import interfaces.IComponent;
import javafx.scene.control.MenuBar;
import lib.manager.SessionManager;

public abstract class NavigationBar extends MenuBar implements IComponent {

    public NavigationBar() {
        init();
        setLayout();
        setStyle();
        setEvent();
    }

    public static NavigationBar getNavigationBar() {
        UserRole role = SessionManager.getCurrentUser().getRole();

        if (role.equals(UserRole.SELLER)) {
            return SellerNavigationBar.getInstance();
        } else if (role.equals(UserRole.BUYER)) {
            return BuyerNavigationBar.getInstance();
        } else if (role.equals(UserRole.ADMIN)) {
            return AdminNavigationBar.getInstance();
        }

        return null;
    }

}
