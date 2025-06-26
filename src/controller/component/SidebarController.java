package controller.component;

import core.Controller;
import view.component.SidebarComponent;

import java.awt.event.ActionListener;

public class SidebarController extends Controller {
    private final SidebarComponent sidebar;

    public SidebarController(SidebarComponent sidebar) {
        this.sidebar = sidebar;
    }

    @Override
    public void run() {
        setupListeners();
    }

    private void setupListeners() {
        sidebar.getBtn_profile().addActionListener(goToProfile());
        sidebar.getBtn_vacant().addActionListener(goToVacants());
        sidebar.getBtn_courses().addActionListener(goToCourses());
        sidebar.getBtn_postulations().addActionListener(goToConfig());
    }

    private ActionListener goToProfile() {
        return e -> showLayoutWithNav("PerfilUsuarioPersonalView");
    }

    private ActionListener goToVacants() {
        return e -> showLayoutWithNav("PanelUsuarioPersonalView");
    }

    private ActionListener goToCourses() {
        return  e -> showLayoutWithNav("CursosUsuarioPersonalView");
    }

    private ActionListener goToConfig() {
        return e -> showLayoutWithNav("ConfiguracionView");
    }

}
