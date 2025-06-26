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
    private UsuarioPersonal user;

    public PerfilUsuarioPersonalController(UsuarioPersonalModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        this.view = new PerfilUsuarioPersonalView();

        view.getBtnGuardar().addActionListener(e -> {
            UsuarioPersonal actualizado = view.getDatosEditados(user);

            int confirm = JOptionPane.showConfirmDialog(view,
                    "¿Deseas guardar los cambios del perfil?",
                    "Confirmar cambios", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                model.update(actualizado);
                Session.get().setUsuario(actualizado);
                JOptionPane.showMessageDialog(view, "Cambios guardados correctamente.");
            }
        });
    }

    public PerfilUsuarioPersonalView getView() {
        return view;
    }
}
