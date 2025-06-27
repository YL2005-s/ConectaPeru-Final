package view.app;

import core.Observer;
import entities.Postulacion;

import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PostulacionesUsuarioPersonalView  extends JPanel {
    private final JComboBox<String> cb_estado;
    private final JButton btn_filter;
    private final JPanel panel_results;

    public PostulacionesUsuarioPersonalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lbl_title = new JLabel("\uD83D\uDCCA Mis postulaciones");
        lbl_title.setFont(new Font("SansSerif", Font.BOLD, 24));
        lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl_title, BorderLayout.NORTH);

        JPanel panel_filter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_filter.setBackground(Color.WHITE);
        panel_filter.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panel_filter.add(new JLabel("\uD83D\uDD0D Filtrar por estado:"));
        cb_estado = new JComboBox<>(new String[]{"Todos", "ENVIADO", "EN_PROCESO", "RECHAZADO", "ACEPTADO"});
        cb_estado.setPreferredSize(new Dimension(180, 25));
        panel_filter.add(cb_estado);

        btn_filter = new JButton("Filtrar");
        panel_filter.add(btn_filter);

        add(panel_filter, BorderLayout.BEFORE_FIRST_LINE);

        panel_results = new JPanel();
        panel_results.setLayout(new BoxLayout(panel_results, BoxLayout.Y_AXIS));
        panel_results.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(panel_results);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }

    public String getSelectedEstado() {
        return (String) cb_estado.getSelectedItem();
    }

    public void setFilterListener(Runnable action) {
        btn_filter.addActionListener(e -> action.run());
    }

    public void showPostulaciones(List<Postulacion> postulaciones) {
        panel_results.removeAll();

        for (Postulacion p : postulaciones) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)));
            card.setBackground(Color.WHITE);
            card.setMaximumSize(new Dimension(800, 100));

            JLabel lbl_title = new JLabel("\uD83D\uDCBC " + p.getVacante().getTitulo());
            lbl_title.setFont(new Font("SansSerif", Font.BOLD, 16));

            JLabel lbl_empresa = new JLabel("\uD83C\uDFE2 Empresa ID: " + p.getVacante().getIdEmpresa());
            JLabel lbl_fecha = new JLabel("\uD83D\uDCC5 Postulado: " + p.getFechaPostulacion());
            JLabel lbl_estado = new JLabel("\uD83D\uDD04 Estado: " + p.getEstado().name());
            lbl_estado.setForeground(getColorForEstado(p.getEstado()));

            card.add(lbl_title);
            card.add(lbl_empresa);
            card.add(lbl_fecha);
            card.add(lbl_estado);

            panel_results.add(card);
            panel_results.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panel_results.revalidate();
        panel_results.repaint();
    }

    private Color getColorForEstado(Postulacion.Estado estado) {
        return switch (estado) {
            case ENVIADO -> Color.BLUE;
            case EN_PROCESO -> new Color(255, 165, 0);
            case RECHAZADO -> Color.RED;
            case ACEPTADO -> new Color(0, 160, 100);
        };
    }
}