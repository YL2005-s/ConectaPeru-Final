package view.auth;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RegisterPersonalView extends JPanel {
    private final Map<String, JTextField> textFields = new HashMap<>();
    private final JButton btn_register;
    private final JButton btn_back;

    public RegisterPersonalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(500, 750));

        make_header(card);
        make_fields(card);

        btn_register = make_btn("Registrarse");
        card.add(btn_register);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        btn_back = make_btn_back();
        card.add(btn_back);

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setBackground(Color.WHITE);
        wrapper.add(Box.createVerticalGlue());
        wrapper.add(card);
        wrapper.add(Box.createVerticalGlue());

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void make_header(JPanel parent) {
        JLabel lblTitle = new JLabel("Registro - Candidato");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(40, 40, 40));
        lblTitle.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Completa todos los campos requeridos");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setAlignmentX(CENTER_ALIGNMENT);

        parent.add(lblTitle);
        parent.add(Box.createRigidArea(new Dimension(0, 10)));
        parent.add(lblSubtitle);
        parent.add(Box.createRigidArea(new Dimension(0, 25)));
    }

    private void make_fields(JPanel parent) {
        String[] labels = {
                "DNI", "Correo", "Contraseña", "Teléfono", "Nombre", "Apellido",
                "Fecha de nacimiento (yyyy-MM-dd)", "Experiencia", "Habilidades", "Formación"
        };

        for (String label : labels) {
            JPanel campo = new JPanel(new BorderLayout(5, 5));
            campo.setBackground(Color.WHITE);
            campo.setMaximumSize(new Dimension(400, 60));

            JLabel lbl = new JLabel(label);
            lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
            lbl.setForeground(Color.DARK_GRAY);
            campo.add(lbl, BorderLayout.NORTH);

            JTextField tf = new JTextField();
            tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
            tf.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(new Color(180, 180, 180), 1, true),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
            tf.setBackground(new Color(245, 245, 245));
            campo.add(tf, BorderLayout.CENTER);

            textFields.put(label, tf);
            parent.add(campo);
            parent.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private JButton make_btn(String label) {
        JButton btn = new JButton(label);
        btn.setFont(new Font("SansSerif", Font.BOLD, 18));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(244, 33, 46));
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(300, 45));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.getModel().addChangeListener(e -> {
            ButtonModel model = btn.getModel();
            btn.setBackground(model.isPressed() ? new Color(200, 0, 0) : new Color(244, 33, 46));
        });

        return btn;
    }

    private JButton make_btn_back() {
        JButton btn = new JButton("← Volver");
        btn.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn.setForeground(new Color(80, 80, 160));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setAlignmentX(CENTER_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public String getField(String name) {
        return textFields.get(name).getText().trim();
    }

    public JButton getBtn_register() {
        return btn_register;
    }

    public JButton getBtn_back() {
        return btn_back;
    }
}