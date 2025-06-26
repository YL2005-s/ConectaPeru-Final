package controller.component;

import config.Session;
import core.Controller;
import view.component.NavbarComponent;

public class NavBarController extends Controller {
    private final NavbarComponent navBar;

    public NavBarController(NavbarComponent navBar) {
        this.navBar = navBar;
    }

    @Override
    public void run() {
        setupListeners();
    }

    private void setupListeners() {
        navBar.getBtn_logout().addActionListener(e -> {
            Session.get().clear();
            hideLayoutWithNav();
            loadView("LoginView");
        });
    }
}
