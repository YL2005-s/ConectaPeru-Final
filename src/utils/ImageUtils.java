package utils;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageUtils {

    public static ImageIcon loadIcon(String path, int width, int height) {
        try {
            URL resource = ImageUtils.class.getResource("/resources/" + path);
            if (resource == null) throw new IllegalArgumentException("No se encontró el recurso: " + path);
            Image image = new ImageIcon(resource).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
