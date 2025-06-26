package view.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SidebarComponent extends JPanel {
    private final JButton btn_profile;
    private final JButton btn_vacants;
    private final JButton btn_courses;
    private final JButton btn_postulations;

    public SidebarComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(20, 15, 20, 15));
        setPreferredSize(new Dimension(220, 0));

        JLabel lbl_title = new JLabel("📌 Panel de usuario");
        lbl_title.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl_title.setForeground(new Color(80, 80, 80));
        lbl_title.setAlignmentX(CENTER_ALIGNMENT);
        add(lbl_title);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel lbl_slogan = new JLabel("🔍 Busque su trabajo ideal");
        lbl_slogan.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lbl_slogan.setForeground(new Color(120, 120, 120));
        lbl_slogan.setAlignmentX(CENTER_ALIGNMENT);
        add(lbl_slogan);
        add(Box.createRigidArea(new Dimension(0, 30)));

        Dimension btn_size = new Dimension(180, 40);
        btn_profile = make_button("👤 Mi perfil", btn_size);
        btn_vacants = make_button("📄 Vacantes", btn_size);
        btn_courses = make_button("\uD83D\uDCD6 Cursos", btn_size);
        btn_postulations = make_button("\uD83D\uDCC8 Postulaciones", btn_size);

        add(btn_profile);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btn_vacants);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btn_courses);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btn_postulations);
    }

    private JButton make_button(String text, Dimension size) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setPreferredSize(size);
        btn.setMaximumSize(size);
        btn.setFocusPainted(false);
        btn.setBackground(new Color(240, 240, 240));
        btn.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 12, 8, 12)
        ));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }

    public JButton getBtn_profile() {
        return btn_profile;
    }

    public JButton getBtn_vacant() {
        return btn_vacants;
    }

    public JButton getBtn_courses() {
        return btn_courses;
    }

    public JButton getBtn_postulations() {
        return btn_postulations;
    }
}