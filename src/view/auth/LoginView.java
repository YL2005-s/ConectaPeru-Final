package view.auth;

import controller.LoginController;
import utils.ImageUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginView extends JPanel {
    private final LoginController loginController;

    private JTextField tf_dni;
    private JPasswordField pf_password;
    private JButton btn_login;
    private JLabel lbl_register;

    public LoginView(LoginController loginController) {
        this.loginController = loginController;
        make_frame();
    }

    private void make_frame() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setMaximumSize(new Dimension(460, 650));

        make_lbl_logo(card);
        make_field_dni(card);
        make_field_password(card);
        make_btn_enter(card);
        make_lbl_register(card);

        JPanel wrapper = new JPanel();
        wrapper.setBackground(Color.WHITE);
        wrapper.setLayout(new GridBagLayout());
        wrapper.add(card);

        add(wrapper, BorderLayout.CENTER);
    }

    private void make_lbl_logo(JPanel parent) {
        JLabel lbl_logo = new JLabel();
        lbl_logo.setIcon(ImageUtils.loadIcon("img/logo_cpr.png", 410, 210));
        lbl_logo.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lbl_welcome = new JLabel("Bienvenido a ConectaPerú", SwingConstants.CENTER);
        lbl_welcome.setFont(new Font("SansSerif", Font.BOLD, 22));
        lbl_welcome.setForeground(new Color(50, 50, 50));
        lbl_welcome.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lbl_sub = new JLabel("Conectamos talento con oportunidades", SwingConstants.CENTER);
        lbl_sub.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl_sub.setForeground(new Color(100, 100, 100));
        lbl_sub.setAlignmentX(CENTER_ALIGNMENT);

        parent.add(lbl_logo);
        parent.add(Box.createRigidArea(new Dimension(0, 10)));
        parent.add(lbl_welcome);
        parent.add(Box.createRigidArea(new Dimension(0, 5)));
        parent.add(lbl_sub);
        parent.add(Box.createRigidArea(new Dimension(0, 30)));
    }

    private void make_field_dni(JPanel parent) {
        JPanel panel = createLabel("DNI");
        tf_dni = createTextField();
        panel.add(tf_dni, BorderLayout.CENTER);

        parent.add(panel);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void make_field_password(JPanel parent) {
        JPanel panel = createLabel("Contraseña");

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 40));

        pf_password = new JPasswordField();
        pf_password.setFont(new Font("SansSerif", Font.PLAIN, 16));
        pf_password.setBounds(0, 0, 400, 40);
        pf_password.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 40)
        ));
        pf_password.setBackground(new Color(245, 245, 245));
        layeredPane.add(pf_password, JLayeredPane.DEFAULT_LAYER);

        JButton btn_eye = new JButton(ImageUtils.loadIcon("icon/icon_eye.png", 22, 22));
        btn_eye.setBounds(360, 9, 22, 22);
        btn_eye.setBorderPainted(false);
        btn_eye.setContentAreaFilled(false);
        btn_eye.setFocusPainted(false);
        btn_eye.setCursor(new Cursor(Cursor.HAND_CURSOR));
        layeredPane.add(btn_eye, JLayeredPane.PALETTE_LAYER);

        btn_eye.addActionListener(e -> {
            if (pf_password.getEchoChar() != 0) {
                pf_password.setEchoChar((char) 0);
                btn_eye.setIcon(ImageUtils.loadIcon("icon/icon_closeEye.png", 20, 20));
            } else {
                pf_password.setEchoChar('•');
                btn_eye.setIcon(ImageUtils.loadIcon("icon/icon_eye.png", 20, 20));
            }
        });

        panel.add(layeredPane, BorderLayout.CENTER);
        parent.add(panel);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void make_btn_enter(JPanel parent) {
        btn_login = new JButton("Ingresar");
        btn_login.setFont(new Font("SansSerif", Font.BOLD, 20));
        btn_login.setForeground(Color.WHITE);
        btn_login.setBackground(new Color(244, 33, 46));
        btn_login.setFocusPainted(false);
        btn_login.setContentAreaFilled(false);
        btn_login.setOpaque(true);
        btn_login.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_login.setMaximumSize(new Dimension(300, 45));
        btn_login.setAlignmentX(CENTER_ALIGNMENT);

        btn_login.getModel().addChangeListener(ev -> {
            ButtonModel model = btn_login.getModel();
            btn_login.setBackground(model.isPressed() ? new Color(200, 0, 0) : new Color(244, 33, 46));
        });

        parent.add(btn_login);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private void make_lbl_register(JPanel parent) {
        lbl_register = new JLabel("¿No tienes una cuenta? Regístrate aquí", SwingConstants.CENTER);
        lbl_register.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl_register.setForeground(new Color(80, 80, 160));
        lbl_register.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lbl_register.setAlignmentX(CENTER_ALIGNMENT);

        parent.add(lbl_register);
    }

    private JPanel createLabel(String labelTexto) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(400, 65));
        panel.setAlignmentX(CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelTexto);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        label.setForeground(Color.DARK_GRAY);
        panel.add(label, BorderLayout.NORTH);

        return panel;
    }

    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 16));
        tf.setMargin(new Insets(8, 10, 8, 10));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        tf.setBackground(new Color(245, 245, 245));
        return tf;
    }

    public JButton getBtnLogin() {
        return btn_login;
    }

    public JLabel getLblRegister() {
        return lbl_register;
    }

    public String getDni() {
        return tf_dni.getText();
    }

    public String getPassword() {
        return new String(pf_password.getPassword());
    }

}