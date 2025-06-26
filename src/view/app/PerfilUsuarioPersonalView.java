package view.app;

import core.Observer;
import entities.UsuarioPersonal;
import view.component.NavbarComponent;
import view.component.SidebarComponent;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PerfilUsuarioPersonalView extends JPanel implements Observer<Object> {

    private final NavbarComponent navbar;
    private final SidebarComponent sidebar;

    private final JTextField tfNombre;
    private final JTextField tfApellido;
    private final JTextField tfCorreo;
    private final JTextField tfTelefono;
    private final JTextArea taExperiencia;
    private final JTextArea taHabilidades;
    private final JTextArea taFormacion;

    private final JButton btnGuardar;

    public PerfilUsuarioPersonalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 🔝 Navbar
        navbar = new NavbarComponent("Usuario");
        add(navbar, BorderLayout.NORTH);

        // 📚 Sidebar
        sidebar = new SidebarComponent();
        add(sidebar, BorderLayout.WEST);

        // 🔲 Contenido centrado
        JPanel contenido = new JPanel(new GridBagLayout());
        contenido.setBackground(Color.WHITE);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        card.setMaximumSize(new Dimension(600, 800));

        JLabel lblTitulo = new JLabel("👤 Mi perfil");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitulo.setAlignmentX(CENTER_ALIGNMENT);
        card.add(lblTitulo);
        card.add(Box.createRigidArea(new Dimension(0, 25)));

        tfNombre = crearCampo(card, "Nombre");
        tfApellido = crearCampo(card, "Apellido");
        tfCorreo = crearCampo(card, "Correo");
        tfTelefono = crearCampo(card, "Teléfono");
        taExperiencia = crearArea(card, "Experiencia");
        taHabilidades = crearArea(card, "Habilidades");
        taFormacion = crearArea(card, "Formación");

        btnGuardar = new JButton("💾 Guardar cambios");
        btnGuardar.setAlignmentX(CENTER_ALIGNMENT);
        btnGuardar.setBackground(new Color(0, 160, 100));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setMaximumSize(new Dimension(200, 40));

        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(btnGuardar);

        contenido.add(card);
        add(contenido, BorderLayout.CENTER);
    }

    private JTextField crearCampo(JPanel parent, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        parent.add(lbl);
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(500, 30));
        tf.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        parent.add(tf);
        parent.add(Box.createRigidArea(new Dimension(0, 12)));
        return tf;
    }

    private JTextArea crearArea(JPanel parent, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lbl.setAlignmentX(LEFT_ALIGNMENT);
        parent.add(lbl);
        JTextArea ta = new JTextArea(3, 20);
        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        ta.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        ta.setMaximumSize(new Dimension(500, 70));
        parent.add(ta);
        parent.add(Box.createRigidArea(new Dimension(0, 12)));
        return ta;
    }

    public void actualizarDatos(UsuarioPersonal u) {
        tfNombre.setText(u.getNombre());
        tfApellido.setText(u.getApellido());
        tfCorreo.setText(u.getCorreo());
        tfTelefono.setText(u.getTelefono());
        taExperiencia.setText(u.getExperiencia());
        taHabilidades.setText(u.getHabilidades());
        taFormacion.setText(u.getFormacion());
        navbar.setUsuario(u.getNombre() + " " + u.getApellido());
    }

    public UsuarioPersonal getDatosEditados(UsuarioPersonal original) {
        return new UsuarioPersonal.Builder()
                .id(original.getId())
                .dni(original.getDni())
                .correo(tfCorreo.getText().trim())
                .contraseña(original.getContraseña())
                .telefono(tfTelefono.getText().trim())
                .nombre(tfNombre.getText().trim())
                .apellido(tfApellido.getText().trim())
                .fechaNacimiento(original.getFechaNacimiento())
                .experiencia(taExperiencia.getText().trim())
                .habilidades(taHabilidades.getText().trim())
                .formacion(taFormacion.getText().trim())
                .build();
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public SidebarComponent getSidebar() {
        return sidebar;
    }

    public NavbarComponent getNavbar() {
        return navbar;
    }

    @Override
    public void update(Object value) {
        if (value instanceof UsuarioPersonal u) {
            actualizarDatos(u);
        }
    }
}