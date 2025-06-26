package controller;

import config.Session;
import core.Controller;
import entities.UsuarioEmpresarial;
import model.UsuarioEmpresarialModel;
import view.auth.RegisterEnterpriseView;

import javax.swing.*;

public class RegisterEnterpriseController extends Controller {
    private RegisterEnterpriseView view;
    private final UsuarioEmpresarialModel model;

    public RegisterEnterpriseController(UsuarioEmpresarialModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        this.view = new RegisterEnterpriseView();
        setupListeners();
    }

    private void setupListeners() {
        view.getBtn_register().addActionListener(e -> {
            registrarEmpresa();
        });

        view.getBtn_back().addActionListener(e -> {
            new RegisterSelectorController().run();
        });
    }

    private void registrarEmpresa() {
        try {
            String dni = view.getCampo("DNI");
            String correo = view.getCampo("Correo");
            String contraseña = view.getCampo("Contraseña");
            String telefono = view.getCampo("Teléfono");
            String nombreEmpresa = view.getCampo("Nombre Empresa");
            String descripcion = view.getCampo("Descripción");
            String ubicacion = view.getCampo("Ubicación");

            if (dni.isEmpty() || correo.isEmpty() || contraseña.isEmpty() ||
                    telefono.isEmpty() || nombreEmpresa.isEmpty() || ubicacion.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Completa todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!dni.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(view, "El DNI debe tener 8 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (model.exists(dni)) {
                JOptionPane.showMessageDialog(view, "Ya existe una empresa registrada con este DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UsuarioEmpresarial user = new UsuarioEmpresarial.Builder()
                    .dni(dni)
                    .correo(correo)
                    .contraseña(contraseña)
                    .telefono(telefono)
                    .nombreEmpresa(nombreEmpresa)
                    .descripcion(descripcion)
                    .ubicacion(ubicacion)
                    .build();

            model.add(user);
            Session.get().setUsuario(user);

            JOptionPane.showMessageDialog(view, "Registro exitoso. Bienvenido(a) a ConectaPerú.");
            // loadView("PanelUsuarioEmpresarial");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al registrar la empresa.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public RegisterEnterpriseView getView() {
        return view;
    }
}
