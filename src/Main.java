import controller.MainController;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainController mc = new MainController();
            mc.run();
        });
    }
}