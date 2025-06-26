package core;

import view.component.NavbarComponent;
import view.component.SidebarComponent;

import javax.swing.*;
import java.awt.*;

public abstract class Controller {
    protected static final JFrame mainFrame = new JFrame("Conecta Perú");
    protected static final JPanel viewsPanel = new JPanel(new CardLayout());
    protected static final JPanel layoutWrapper = new JPanel(new BorderLayout());

    protected static SidebarComponent sidebarComponent;
    protected static NavbarComponent navbarComponent;

    static {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 800);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);

        layoutWrapper.add(viewsPanel, BorderLayout.CENTER);
        mainFrame.setContentPane(layoutWrapper);
    }

    public abstract void run();

    public static void addView(String viewName, Component view) {
        viewsPanel.add(view, viewName);
    }

    public static void loadView(String viewName) {
        CardLayout cl = (CardLayout) viewsPanel.getLayout();
        cl.show(viewsPanel, viewName);
    }
}
