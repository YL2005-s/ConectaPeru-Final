package view.app;

import entities.Vacante;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;
import java.util.function.Consumer;

public class PanelUsuarioEmpresarialView extends JPanel {
    private final JLabel lbl_welcome;
    private final JButton btn_create;
    private final JPanel panelResults;

    public PanelUsuarioEmpresarialView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 10, 30));

        lbl_welcome = new JLabel("Bienvenido, empresa");
        lbl_welcome.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl_welcome.setAlignmentX(CENTER_ALIGNMENT);

        btn_create = new JButton("➕ Publicar vacante");
        btn_create.setAlignmentX(CENTER_ALIGNMENT);
        btn_create.setBackground(new Color(0, 160, 100));
        btn_create.setForeground(Color.WHITE);
        btn_create.setFocusPainted(false);
        btn_create.setCursor(new Cursor(Cursor.HAND_CURSOR));

        topPanel.add(lbl_welcome);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(btn_create);

        add(topPanel, BorderLayout.NORTH);

        panelResults = new JPanel();
        panelResults.setLayout(new BoxLayout(panelResults, BoxLayout.Y_AXIS));
        panelResults.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(panelResults);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }

    public void setWelcome(String nombreEmpresa) {
        lbl_welcome.setText("Bienvenido, " + nombreEmpresa);
    }

    public void setCreateListener(Runnable listener) {
        btn_create.addActionListener(e -> listener.run());
    }

    public void showVacantes(List<Vacante> vacantes, Consumer<Vacante> onEliminar, Consumer<Vacante> onPostulantes) {
        panelResults.removeAll();

        for (Vacante v : vacantes) {
            JPanel card = new JPanel(new BorderLayout());
            card.setMaximumSize(new Dimension(600, 180));
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(200, 200, 200), 1, true),
                    BorderFactory.createEmptyBorder(12, 15, 12, 15)
            ));

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBackground(Color.WHITE);

            JLabel lbl_title = new JLabel("\uD83D\uDCBC " + v.getTitulo());
            lbl_title.setFont(new Font("SansSerif", Font.BOLD, 16));

            JLabel lbl_ubicacion = new JLabel("\uD83D\uDCCD " + v.getUbicacion());
            JLabel lbl_fecha = new JLabel("\uD83D\uDCC5 Publicado: " + v.getFechaPublicacion());

            info.add(lbl_title);
            info.add(lbl_ubicacion);
            info.add(lbl_fecha);

            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnPanel.setBackground(Color.WHITE);

            JButton btn_ver = new JButton("👥 Ver postulantes");
            JButton btn_eliminar = new JButton("🗑 Eliminar");

            btn_ver.setBackground(new Color(70, 130, 180));
            btn_ver.setForeground(Color.WHITE);
            btn_ver.setFocusPainted(false);
            btn_ver.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn_eliminar.setBackground(Color.RED);
            btn_eliminar.setForeground(Color.WHITE);
            btn_eliminar.setFocusPainted(false);
            btn_eliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btn_ver.addActionListener(e -> onPostulantes.accept(v));
            btn_eliminar.addActionListener(e -> onEliminar.accept(v));

            btnPanel.add(btn_ver);
            btnPanel.add(btn_eliminar);

            card.add(info, BorderLayout.CENTER);
            card.add(btnPanel, BorderLayout.SOUTH);

            panelResults.add(card);
            panelResults.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panelResults.revalidate();
        panelResults.repaint();
    }
}
