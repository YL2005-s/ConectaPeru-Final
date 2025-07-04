package controller;

import config.Session;
import core.Controller;
import entities.UsuarioPersonal;
import model.UsuarioPersonalModel;
import view.app.PerfilUsuarioPersonalView;

import javax.swing.*;

public class PerfilUsuarioPersonalController extends Controller {
    private final UsuarioPersonalModel model;
    private  PerfilUsuarioPersonalView view;

    public PerfilUsuarioPersonalController(UsuarioPersonalModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        this.view = new PerfilUsuarioPersonalView();

        setupListener();
    }

    private void setupListener() {
        view.getBtn_save().addActionListener(e -> {
            UsuarioPersonal user = view.getUserData();
            if (user == null) return;

            int confirm = JOptionPane.showConfirmDialog(view,
                    "¿Deseas guardar los cambios del perfil?",
                    "Confirmar cambios", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                model.update(user);
                Session.get().setUsuario(user);
                JOptionPane.showMessageDialog(view, "Cambios guardados correctamente.");
            }
        });
    }

    public PerfilUsuarioPersonalView getView() {
        return view;
    }
}
