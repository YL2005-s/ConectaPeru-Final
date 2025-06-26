package view.app;

import config.Session;
import core.Observer;
import entities.UsuarioPersonal;
import entities.Vacante;
import view.component.NavbarComponent;
import view.component.SidebarComponent;

import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.function.Consumer;

public class PanelUsuarioPersonalView extends JPanel implements Observer<Object> {
    private final NavbarComponent navbar;
    private final SidebarComponent sidebar;
    private final JTextField tfBuscar;
    private final JButton btnBuscar;
    private final JPanel panelResultados;
    private final JLabel lblBienvenida;

    public PanelUsuarioPersonalView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        navbar = new NavbarComponent("Usuario");
        add(navbar, BorderLayout.NORTH);

        sidebar = new SidebarComponent();
        add(sidebar, BorderLayout.WEST);

        // CONTENIDO PRINCIPAL
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(Color.WHITE);
        panelContenido.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        lblBienvenida = new JLabel("Bienvenido, usuario");
        lblBienvenida.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblBienvenida.setAlignmentX(CENTER_ALIGNMENT);
        panelContenido.add(lblBienvenida);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 20)));

        // BUSCADOR
        JPanel panelBuscar = new JPanel(new BorderLayout(10, 5));
        panelBuscar.setMaximumSize(new Dimension(600, 40));
        panelBuscar.setBackground(Color.WHITE);
        tfBuscar = new JTextField();
        tfBuscar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tfBuscar.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(180, 180, 180), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        panelBuscar.add(tfBuscar, BorderLayout.CENTER);

        btnBuscar = new JButton("Buscar");
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBackground(new Color(244, 33, 46));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        panelBuscar.add(btnBuscar, BorderLayout.EAST);

        panelContenido.add(panelBuscar);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 25)));

        // VACANTES
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        panelResultados.setBackground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(panelResultados);
        scroll.setPreferredSize(new Dimension(800, 400));
        scroll.setBorder(null);
        panelContenido.add(scroll);

        add(panelContenido, BorderLayout.CENTER);
    }

    public void mostrarVacantes(List<Vacante> vacantes) {
        panelResultados.removeAll();

        if (vacantes.isEmpty()) {
            JLabel vacio = new JLabel("No se encontraron vacantes.");
            vacio.setFont(new Font("SansSerif", Font.ITALIC, 14));
            vacio.setForeground(Color.GRAY);
            vacio.setAlignmentX(CENTER_ALIGNMENT);
            panelResultados.add(vacio);
        } else {
            for (Vacante v : vacantes) {
                panelResultados.add(crearTarjetaVacante(v));
                panelResultados.add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }

    private JPanel crearTarjetaVacante(Vacante v) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));

        // Panel izquierdo (info)
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);

        JLabel lblTitulo = new JLabel("💼 " + v.getTitulo());
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel lblEmpresa = new JLabel("🏢 " + v.getIdEmpresa());
        lblEmpresa.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblUbicacion = new JLabel("📍 " + v.getUbicacion());
        lblUbicacion.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JLabel lblTipo = new JLabel("🗂 Tipo: " + (v.getModalidad() != null ? v.getModalidad() : "No especificado"));
        lblTipo.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel lblFecha = new JLabel("📅 Publicado: " + (v.getFechaPublicacion() != null ? v.getFechaPublicacion().toString() : "N/A"));
        lblFecha.setFont(new Font("SansSerif", Font.PLAIN, 13));

        info.add(lblTitulo);
        info.add(lblEmpresa);
        info.add(lblUbicacion);
        info.add(lblTipo);
        info.add(lblFecha);

        // Botón de postular
        JButton btnAplicar = new JButton("📝 Postular");
        btnAplicar.setBackground(new Color(30, 144, 255));
        btnAplicar.setForeground(Color.WHITE);
        btnAplicar.setFocusPainted(false);
        btnAplicar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAplicar.setPreferredSize(new Dimension(120, 35));

        JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBtn.setBackground(Color.WHITE);
        panelBtn.add(btnAplicar);

        card.add(info, BorderLayout.CENTER);
        card.add(panelBtn, BorderLayout.SOUTH);

        return card;
    }


    public void setBuscarListener(Consumer<String> listener) {
        btnBuscar.addActionListener(e -> listener.accept(tfBuscar.getText().trim()));
    }

    public NavbarComponent getNavbar() {
        return navbar;
    }

    public SidebarComponent getSidebar() {
        return sidebar;
    }

    @Override
    public void update(Object valor) {
        if (valor instanceof UsuarioPersonal up) {
            lblBienvenida.setText("Bienvenido, " + up.getNombre());
            navbar.setUsuario(up.getNombre() + " " + up.getApellido());
        }
    }
}