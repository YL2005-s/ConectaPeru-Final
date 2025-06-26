package view.component;

import javax.swing.*;
import java.awt.*;

public class NavbarComponent extends JPanel {

    private final JLabel lbl_user;
    private final JButton btn_logout;

    public NavbarComponent(String nombreUsuario) {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setPreferredSize(new Dimension(1000, 60));

        JLabel lblTitulo = new JLabel("ConectaPerú");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(200, 40, 40));
        add(lblTitulo, BorderLayout.WEST);

        lbl_user = new JLabel("👤 " + nombreUsuario);
        lbl_user.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl_user.setForeground(Color.DARK_GRAY);

        btn_logout = new JButton("Cerrar sesión");
        btn_logout.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btn_logout.setFocusPainted(false);
        btn_logout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(240, 240, 240));
        rightPanel.add(lbl_user);
        rightPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        rightPanel.add(btn_logout);

        add(rightPanel, BorderLayout.EAST);
    }

    public JButton getBtn_logout() {
        return btn_logout;
    }

    public void setUsuario(String nombre) {
        lbl_user.setText("👤 " + nombre);
    }
}