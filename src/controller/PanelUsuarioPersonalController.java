package controller;

import config.Session;
import core.Controller;
import entities.UsuarioPersonal;
import entities.Vacante;
import model.UsuarioEmpresarialModel;
import model.UsuarioPersonalModel;
import model.VacanteModel;
import view.app.PanelUsuarioPersonalView;

import java.util.List;

public class PanelUsuarioPersonalController extends Controller {
    private PanelUsuarioPersonalView view;
    private final VacanteModel vacanteModel;
    private final UsuarioPersonalModel personalModel;

    public PanelUsuarioPersonalController(UsuarioPersonalModel personalModel, VacanteModel vacanteModel) {
        this.vacanteModel = vacanteModel;
        this.personalModel = personalModel;
    }

    @Override
    public void run() {
        this.view = new PanelUsuarioPersonalView();
        addView("PanelUsuarioPersonalView", view);
        loadView("PanelUsuarioPersonalView");

        mostrarVacantesRecientes();

        view.getNavbar().getBtn_logout().addActionListener(e -> {
            Session.get().clear();
            loadView("LoginView");
        });

        view.getSidebar().getBtnPerfil().addActionListener(e -> {
            loadView("PerfilUsuarioPersonalComponent");
        });

        view.setBuscarListener(texto -> {
            List<Vacante> resultado = texto.trim().isEmpty()
                    ? vacanteModel.getAll()
                    : vacanteModel.searchByText(texto);
            view.mostrarVacantes(resultado);
        });
    }

    private void mostrarVacantesRecientes() {
        view.mostrarVacantes(vacanteModel.getAll());
    }

    public PanelUsuarioPersonalView getView() {
        return view;
    }
}