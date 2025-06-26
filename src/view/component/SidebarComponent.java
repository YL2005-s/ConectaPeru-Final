package view.component;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class SidebarComponent extends JPanel {
    private final JButton btnPerfil;
    private final JButton btnVacantes;
    private final JButton btnConfiguracion;

    public SidebarComponent() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(245, 245, 245));
        setBorder(new EmptyBorder(20, 15, 20, 15));
        setPreferredSize(new Dimension(220, 0));


        JLabel lblTitulo = new JLabel("📌 Panel de usuario");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitulo.setForeground(new Color(80, 80, 80));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        add(lblTitulo);

        add(Box.createRigidArea(new Dimension(0, 15)));

        // SUBTÍTULO motivacional
        JLabel lblFrase = new JLabel("🔍 Busque su trabajo ideal");
        lblFrase.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblFrase.setForeground(new Color(120, 120, 120));
        lblFrase.setAlignmentX(CENTER_ALIGNMENT);
        add(lblFrase);

        add(Box.createRigidArea(new Dimension(0, 25)));

        // BOTONES de navegación
        btnPerfil = crearBoton("👤 Mi perfil");
        btnVacantes = crearBoton("📄 Vacantes");
        btnConfiguracion = crearBoton("⚙️ Configuración");

        add(btnPerfil);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnVacantes);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnConfiguracion);
    }

    private JButton crearBoton(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
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

    // Getters
    public JButton getBtnPerfil() {
        return btnPerfil;
    }

    public JButton getBtnVacantes() {
        return btnVacantes;
    }

    public JButton getBtnConfiguracion() {
        return btnConfiguracion;
    }
}
