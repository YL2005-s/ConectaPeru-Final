package core;

import javax.swing.*;
import java.awt.*;

public abstract class Controller {
    protected static final JFrame mainFrame = new JFrame("Conecta Perú");
    private static final JPanel viewsPanel = new JPanel(new CardLayout());

    static {
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 800);
        mainFrame.setResizable(true);
        mainFrame.setLocationRelativeTo(null);

        mainFrame.add(viewsPanel);
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
