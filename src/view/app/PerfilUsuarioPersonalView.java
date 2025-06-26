package view.app;

import config.Session;
import core.Observer;
import entities.UsuarioPersonal;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PerfilUsuarioPersonalView extends JPanel implements Observer<Object> {
    private final JTextField tf_name;
    private final JTextField tf_lastName;
    private final JTextField tf_email;
    private final JTextField tf_phone;

    private final JTextArea ta_experience;
    private final JTextArea ta_skills;
    private final JTextArea ta_training;

    private final JButton btn_save;

    public PerfilUsuarioPersonalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));
        formPanel.setMaximumSize(new Dimension(800, 800));

        JLabel lbl_title = new JLabel("👤 Mi perfil");
        lbl_title.setFont(new Font("SansSerif", Font.BOLD, 26));
        lbl_title.setAlignmentX(CENTER_ALIGNMENT);
        formPanel.add(lbl_title);
        formPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        tf_name = make_field(formPanel, "Nombre");
        tf_lastName = make_field(formPanel, "Apellido");
        tf_email = make_field(formPanel, "Correo");
        tf_phone = make_field(formPanel, "Teléfono");
        ta_experience = make_area(formPanel, "Experiencia");
        ta_skills = make_area(formPanel, "Habilidades");
        ta_training = make_area(formPanel, "Formación");

        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        btn_save = new JButton("💾 Guardar cambios");
        btn_save.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn_save.setAlignmentX(CENTER_ALIGNMENT);
        btn_save.setBackground(new Color(0, 160, 100));
        btn_save.setForeground(Color.WHITE);
        btn_save.setFocusPainted(false);
        btn_save.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn_save.setMaximumSize(new Dimension(180, 40));

        formPanel.add(btn_save);

        contentPanel.add(formPanel);
        add(contentPanel, BorderLayout.CENTER);

        Session.get().addObserver(this);
    }

    private JTextField make_field(JPanel parent, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        parent.add(lbl);

        JTextField tf = new JTextField();
        tf.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tf.setMaximumSize(new Dimension(700, 30));
        tf.setAlignmentX(CENTER_ALIGNMENT);
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        parent.add(tf);
        parent.add(Box.createRigidArea(new Dimension(0, 14)));
        return tf;
    }

    private JTextArea make_area(JPanel parent, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 15));
        lbl.setAlignmentX(CENTER_ALIGNMENT);
        parent.add(lbl);

        JTextArea ta = new JTextArea(3, 20);
        ta.setFont(new Font("SansSerif", Font.PLAIN, 14));
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        JScrollPane scrollPane = new JScrollPane(ta);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setAlignmentX(CENTER_ALIGNMENT);
        scrollPane.setMaximumSize(new Dimension(700, 80));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));

        parent.add(scrollPane);
        parent.add(Box.createRigidArea(new Dimension(0, 14)));

        return ta;
    }

    public void updateFields(UsuarioPersonal u) {
        tf_name.setText(u.getNombre());
        tf_lastName.setText(u.getApellido());
        tf_email.setText(u.getCorreo());
        tf_phone.setText(u.getTelefono());
        ta_experience.setText(u.getExperiencia());
        ta_skills.setText(u.getHabilidades());
        ta_training.setText(u.getFormacion());
    }

    public UsuarioPersonal getUserData() {
        UsuarioPersonal original = (UsuarioPersonal) Session.get().getUsuario();
        return new UsuarioPersonal.Builder()
                .id(original.getId())
                .dni(original.getDni())
                .correo(tf_email.getText().trim())
                .contraseña(original.getContraseña())
                .telefono(tf_phone.getText().trim())
                .nombre(tf_name.getText().trim())
                .apellido(tf_lastName.getText().trim())
                .fechaNacimiento(original.getFechaNacimiento())
                .experiencia(ta_experience.getText().trim())
                .habilidades(ta_skills.getText().trim())
                .formacion(ta_training.getText().trim())
                .build();
    }

    public JButton getBtn_save() {
        return btn_save;
    }

    @Override
    public void update(Object value) {
        if (value instanceof UsuarioPersonal u) {
            updateFields(u);
        }
    }
}