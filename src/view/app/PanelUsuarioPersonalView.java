package view.app;

import config.Session;
import core.Observer;
import entities.UsuarioPersonal;
import entities.Vacante;

import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.Consumer;

public class PanelUsuarioPersonalView extends JPanel implements Observer<Object> {
    private final JTextField tf_search;
    private final JComboBox<String> cb_location;
    private final JButton btn_search;
    private final JPanel panelResults;
    private final JLabel lbl_welcome;
    private Consumer<Vacante> applyListener;

    public PanelUsuarioPersonalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lbl_welcome = new JLabel("Bienvenido, usuario");
        lbl_welcome.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl_welcome.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(lbl_welcome);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setMaximumSize(new Dimension(600, 40));
        searchPanel.setBackground(Color.WHITE);

        tf_search = new JTextField();
        tf_search.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf_search.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        searchPanel.add(tf_search);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        cb_location = new JComboBox<>();
        cb_location.setMaximumSize(new Dimension(160, 30));
        searchPanel.add(cb_location);
        searchPanel.add(Box.createRigidArea(new Dimension(10, 0)));

        btn_search = new JButton("Buscar");
        btn_search.setFocusPainted(false);
        btn_search.setBackground(new Color(244, 33, 46));
        btn_search.setForeground(Color.WHITE);
        btn_search.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchPanel.add(btn_search);

        mainPanel.add(searchPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        panelResults = new JPanel();
        panelResults.setLayout(new BoxLayout(panelResults, BoxLayout.Y_AXIS));
        panelResults.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(panelResults);
        scroll.setPreferredSize(new Dimension(800, 400));
        scroll.setBorder(null);
        mainPanel.add(scroll);

        add(mainPanel, BorderLayout.CENTER);

        Session.get().addObserver(this);
    }

    public void setLocationOptions(List<String> locations) {
        cb_location.removeAllItems();
        cb_location.addItem("Todas");
        for (String loc : locations) {
            cb_location.addItem(loc);
        }
    }

    public String getSelectedLocation() {
        return (String) cb_location.getSelectedItem();
    }

    public void showVacant(List<Vacante> vacant, Consumer<Vacante> applyListener) {
        this.applyListener = applyListener;
        panelResults.removeAll();

        if (vacant.isEmpty()) {
            JLabel empty = new JLabel("No se encontraron vacantes.");
            empty.setFont(new Font("SansSerif", Font.ITALIC, 14));
            empty.setForeground(Color.GRAY);
            empty.setAlignmentX(CENTER_ALIGNMENT);
            panelResults.add(empty);
        } else {
            for (Vacante v : vacant) {
                panelResults.add(make_vacantCard(v));
                panelResults.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        panelResults.revalidate();
        panelResults.repaint();
    }

    private JPanel make_vacantCard(Vacante v) {
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

        JLabel lbl_title = new JLabel("💼 " + v.getTitulo());
        lbl_title.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel lbl_enterprise = new JLabel("🏢 " + v.getIdEmpresa());
        lbl_enterprise.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lbl_location = new JLabel("📍 " + v.getUbicacion());
        lbl_location.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lbl_type = new JLabel("🗂 Tipo: " + (v.getModalidad() != null ? v.getModalidad() : "No especificado"));
        lbl_type.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel lbl_date = new JLabel("📅 Publicado: " + (v.getFechaPublicacion() != null ? v.getFechaPublicacion().toString() : "N/A"));
        lbl_date.setFont(new Font("SansSerif", Font.PLAIN, 13));

        info.add(lbl_title);
        info.add(lbl_enterprise);
        info.add(lbl_location);
        info.add(lbl_type);
        info.add(lbl_date);

        JButton btn_apply = new JButton("📝 Postular");
        btn_apply.setBackground(new Color(30, 144, 255));
        btn_apply.setForeground(Color.WHITE);
        btn_apply.setFocusPainted(false);
        btn_apply.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_apply.setPreferredSize(new Dimension(120, 35));

        btn_apply.addActionListener(e -> {
            if (applyListener != null) {
                applyListener.accept(v);
            }
        });

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtn.setBackground(Color.WHITE);
        panelBtn.add(btn_apply);

        card.add(info, BorderLayout.CENTER);
        card.add(panelBtn, BorderLayout.SOUTH);

        return card;
    }

    public void setSearchListener(Consumer<String> listener) {
        btn_search.addActionListener(e -> listener.accept(tf_search.getText().trim()));
    }

    @Override
    public void update(Object valor) {
        if (valor instanceof UsuarioPersonal up) {
            lbl_welcome.setText("Bienvenido, " + up.getNombre());
        }
    }
}