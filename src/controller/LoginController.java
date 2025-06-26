package controller;

import config.Session;
import core.Controller;
import model.UsuarioEmpresarialModel;
import model.UsuarioPersonalModel;
import view.app.PanelUsuarioPersonalView;
import view.auth.LoginView;

import javax.swing.*;

public class LoginController extends Controller {
    private LoginView loginView;
    private final UsuarioPersonalModel personalModel;
    private final UsuarioEmpresarialModel empresarialModel;

    public LoginController(UsuarioPersonalModel personalModel, UsuarioEmpresarialModel empresarialModel) {
        this.personalModel = personalModel;
        this.empresarialModel = empresarialModel;
    }

    @Override
    public void run() {
        this.loginView = new LoginView(this);

        setupListeners();
    }

    private void setupListeners() {
        loginView.getBtnLogin().addActionListener(e -> {
            String dni = loginView.getDni().trim();
            String pass = loginView.getPassword().trim();

            if (dni.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (personalModel.validateLogin(dni, pass)) {
                Session.get().setUsuario(personalModel.searchByDni(dni));
                JOptionPane.showMessageDialog(null, "Bienvenido, candidato.");
                loadView("PanelUsuarioPersonalView");
                return;
            }

            if (empresarialModel.validateLogin(dni, pass)) {
                Session.get().setUsuario(empresarialModel.searchByDni(dni));
                JOptionPane.showMessageDialog(null, "Bienvenido, empresa.");
                return;
            }

            JOptionPane.showMessageDialog(null, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
        });

        loginView.getLblRegister().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new RegisterSelectorController().run();
            }
        });
    }

    public LoginView getView() {
        return loginView;
    }
}