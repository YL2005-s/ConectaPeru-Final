package view.auth;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RegisterSelectorView extends JPanel {
    private JButton btn_personal;
    private JButton btn_enterprise;
    private JButton btn_back;

    public RegisterSelectorView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(460, 500));

        make_header(card);
        make_buttons(card);
        make_back_button(card);

        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(card);

        add(wrapper, BorderLayout.CENTER);
    }

    private void make_header(JPanel parent) {
        JLabel lbl_title = new JLabel("Crear cuenta");
        lbl_title.setFont(new Font("SansSerif", Font.BOLD, 24));
        lbl_title.setForeground(new Color(50, 50, 50));
        lbl_title.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSubtitle = new JLabel("Selecciona el tipo de cuenta que deseas registrar");
        lblSubtitle.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblSubtitle.setForeground(new Color(100, 100, 100));
        lblSubtitle.setAlignmentX(CENTER_ALIGNMENT);

        parent.add(lbl_title);
        parent.add(Box.createRigidArea(new Dimension(0, 10)));
        parent.add(lblSubtitle);
        parent.add(Box.createRigidArea(new Dimension(0, 40)));
    }

    private void make_buttons(JPanel parent) {
        btn_personal = make_button("Candidato");
        btn_enterprise = make_button("Empresa");

        parent.add(btn_personal);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
        parent.add(btn_enterprise);
        parent.add(Box.createRigidArea(new Dimension(0, 30)));
    }

    private void make_back_button(JPanel parent) {
        btn_back = new JButton("← Volver al inicio");
        btn_back.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btn_back.setForeground(new Color(80, 80, 160));
        btn_back.setContentAreaFilled(false);
        btn_back.setBorderPainted(false);
        btn_back.setFocusPainted(false);
        btn_back.setAlignmentX(CENTER_ALIGNMENT);
        btn_back.setCursor(new Cursor(Cursor.HAND_CURSOR));

        parent.add(btn_back);
    }

    private JButton make_button(String texto) {
        JButton btn = new JButton(texto);
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

    public JButton getBtn_personal() {
        return btn_personal;
    }

    public JButton getBtn_enterprise() {
        return btn_enterprise;
    }

    public JButton getBtn_back() {
        return btn_back;
    }
}
