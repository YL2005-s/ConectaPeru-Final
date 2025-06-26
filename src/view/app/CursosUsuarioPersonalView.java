package view.app;

import core.Observer;
import entities.Capacitacion;

import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.Consumer;

public class CursosUsuarioPersonalView extends JPanel {
    private final JComboBox<String> cb_providerFilter;
    private final JButton btn_applyFilter;
    private final JPanel panel_results;

    public CursosUsuarioPersonalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel lbl_title = new JLabel("\uD83D\uDCDA Capacitaciones");
        lbl_title.setFont(new Font("SansSerif", Font.BOLD, 24));
        lbl_title.setHorizontalAlignment(SwingConstants.CENTER);
        add(lbl_title, BorderLayout.NORTH);

        JPanel panel_filter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel_filter.setBackground(Color.WHITE);
        panel_filter.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panel_filter.add(new JLabel("\uD83D\uDD0D Filtrar por proveedor:"));
        cb_providerFilter = new JComboBox<>();
        cb_providerFilter.setPreferredSize(new Dimension(200, 25));
        panel_filter.add(cb_providerFilter);

        btn_applyFilter = new JButton("Filtrar");
        panel_filter.add(btn_applyFilter);

        add(panel_filter, BorderLayout.BEFORE_FIRST_LINE);

        panel_results = new JPanel();
        panel_results.setLayout(new BoxLayout(panel_results, BoxLayout.Y_AXIS));
        panel_results.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(panel_results);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }

    public void setProviders(List<String> providers) {
        cb_providerFilter.removeAllItems();
        cb_providerFilter.addItem("Todos");
        for (String p : providers) {
            cb_providerFilter.addItem(p);
        }
    }

    public void setFilterListener(Runnable action) {
        btn_applyFilter.addActionListener(e -> action.run());
    }

    public String getSelectedProvider() {
        return (String) cb_providerFilter.getSelectedItem();
    }

    public void showCapacitaciones(List<Capacitacion> cursos, Consumer<Capacitacion> onApply) {
        panel_results.removeAll();

        for (Capacitacion c : cursos) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(new Color(250, 250, 250));
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(Color.LIGHT_GRAY, 1, true),
                    BorderFactory.createEmptyBorder(10, 15, 10, 15)));

            JLabel lbl_title = new JLabel(c.getTitulo());
            lbl_title.setFont(new Font("SansSerif", Font.BOLD, 16));

            JLabel lbl_provider = new JLabel("Proveedor: " + c.getProveedor());
            JLabel lbl_duration = new JLabel("Descripción: " + c.getDescripcion());

            JPanel info = new JPanel();
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setBackground(new Color(250, 250, 250));
            info.add(lbl_title);
            info.add(lbl_provider);
            info.add(lbl_duration);

            JButton btn_apply = new JButton("\uD83D\uDFE2 Inscribirse");
            btn_apply.setBackground(new Color(30, 144, 255));
            btn_apply.setForeground(Color.WHITE);
            btn_apply.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn_apply.addActionListener(e -> onApply.accept(c));

            card.add(info, BorderLayout.CENTER);
            card.add(btn_apply, BorderLayout.EAST);
            card.setMaximumSize(new Dimension(700, 100));

            panel_results.add(card);
            panel_results.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        panel_results.revalidate();
        panel_results.repaint();
    }
}
