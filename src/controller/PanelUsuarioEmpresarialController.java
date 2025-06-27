package controller;

import config.Session;
import core.Controller;
import core.Observer;
import entities.UsuarioEmpresarial;
import entities.Vacante;
import model.VacanteModel;
import model.repository.impl.VacanteRepository;
import view.app.PanelUsuarioEmpresarialView;

import javax.swing.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PanelUsuarioEmpresarialController extends Controller implements Observer<Object> {
    private PanelUsuarioEmpresarialView view;
    private final VacanteModel vacanteModel;

    public PanelUsuarioEmpresarialController(VacanteModel vacanteModel) {
        this.vacanteModel = vacanteModel;

        Session.get().addObserver(this);
    }

    @Override
    public void run() {
        this.view = new PanelUsuarioEmpresarialView();
    }

    private List<Vacante> getVacantesDeEmpresa() {
        UsuarioEmpresarial empresa = (UsuarioEmpresarial) Session.get().getUsuario();
        return vacanteModel.getAll().stream()
                .filter(v -> v.getIdEmpresa() == empresa.getId())
                .collect(Collectors.toList());
    }

    private void crearVacante() {
        JOptionPane.showMessageDialog(view, "Funcionalidad para crear vacante próximamente.",
                "Crear vacante", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarVacante(Vacante v) {
        int confirm = JOptionPane.showConfirmDialog(view,
                "\u00bfEst\u00e1s seguro de eliminar esta vacante?",
                "Eliminar vacante",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = vacanteModel.deleteById(v.getId());
            if (ok) {
                vacanteModel.loadFromDB();
                view.showVacantes(getVacantesDeEmpresa(), this::eliminarVacante, this::verPostulantes);
            } else {
                JOptionPane.showMessageDialog(view, "Error al eliminar la vacante.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verPostulantes(Vacante v) {
        JOptionPane.showMessageDialog(view,
                "Funcionalidad de postulantes en desarrollo para vacante: " + v.getTitulo(),
                "Postulantes", JOptionPane.INFORMATION_MESSAGE);
    }

    public PanelUsuarioEmpresarialView getView() {
        return view;
    }

    @Override
    public void update(Object value) {
        if (value instanceof UsuarioEmpresarial empresa) {
            view.setWelcome(empresa.getNombreEmpresa());
            view.setCreateListener(this::crearVacante);
            view.showVacantes(getVacantesDeEmpresa(), this::eliminarVacante, this::verPostulantes);
        }
    }
}
