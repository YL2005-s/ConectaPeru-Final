package controller;

import config.Session;
import core.Controller;
import entities.UsuarioPersonal;
import model.UsuarioPersonalModel;
import view.auth.RegisterPersonalView;

import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegisterPersonalController extends Controller {
    private RegisterPersonalView view;
    private final UsuarioPersonalModel model;

    public RegisterPersonalController(UsuarioPersonalModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        this.view = new RegisterPersonalView();
        setupListeners();
    }

    private void setupListeners() {
        view.getBtn_register().addActionListener(e -> {
            registerUser();
        });

        view.getBtn_back().addActionListener(e -> {
            new RegisterSelectorController().run();
        });
    }

    private void registerUser() {
        try {
            String dni = view.getField("DNI");
            String correo = view.getField("Correo");
            String contraseña = view.getField("Contraseña");
            String telefono = view.getField("Teléfono");
            String nombre = view.getField("Nombre");
            String apellido = view.getField("Apellido");
            String fechaNacimientoStr = view.getField("Fecha de nacimiento (yyyy-MM-dd)");
            String experiencia = view.getField("Experiencia");
            String habilidades = view.getField("Habilidades");
            String formacion = view.getField("Formación");

            if (dni.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || telefono.isEmpty() ||
                    nombre.isEmpty() || apellido.isEmpty() || fechaNacimientoStr.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Completa todos los campos obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!dni.matches("\\d{8}")) {
                JOptionPane.showMessageDialog(view, "El DNI debe tener 8 dígitos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (model.exists(dni)) {
                JOptionPane.showMessageDialog(view, "Ya existe un usuario registrado con este DNI.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            LocalDate fechaNacimiento;
            try {
                fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(view, "Formato de fecha inválido. Usa yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            UsuarioPersonal user = new UsuarioPersonal.Builder()
                    .dni(dni)
                    .correo(correo)
                    .contraseña(contraseña)
                    .telefono(telefono)
                    .nombre(nombre)
                    .apellido(apellido)
                    .fechaNacimiento(fechaNacimiento)
                    .experiencia(experiencia)
                    .habilidades(habilidades)
                    .formacion(formacion)
                    .build();

            model.add(user);
            Session.get().setUsuario(user);
            JOptionPane.showMessageDialog(view, "Registro exitoso. ¡Bienvenido!");
            // loadView("PanelUsuarioPersonal");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public RegisterPersonalView getView() {
        return view;
    }
}
