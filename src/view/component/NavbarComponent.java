package view.component;

import config.Session;
import core.Observer;
import entities.UsuarioPersonal;

import javax.swing.*;
import java.awt.*;

public class NavbarComponent extends JPanel implements Observer<Object> {
    private final JLabel lbl_user;
    private final JButton btn_logout;

    public NavbarComponent() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        setPreferredSize(new Dimension(1000, 60));

        JLabel lbl_title = new JLabel("ConectaPerú");
        lbl_title.setFont(new Font("SansSerif", Font.BOLD, 18));
        lbl_title.setForeground(new Color(200, 40, 40));
        add(lbl_title, BorderLayout.WEST);

        lbl_user = new JLabel("👤 Usuario");
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

        Session.get().addObserver(this);
    }

    public JButton getBtn_logout() {
        return btn_logout;
    }

    @Override
    public void update(Object value) {
        if (value instanceof UsuarioPersonal u) {
            lbl_user.setText("👤 " + u.getNombre());
        }
    }
}