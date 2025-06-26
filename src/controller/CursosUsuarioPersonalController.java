package controller;

import core.Controller;
import entities.Capacitacion;
import model.CapacitacionModel;
import view.app.CursosUsuarioPersonalView;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CursosUsuarioPersonalController extends Controller {
    private CursosUsuarioPersonalView view;
    private final CapacitacionModel model;

    public CursosUsuarioPersonalController(CapacitacionModel model) {
        this.model = model;
    }

    @Override
    public void run() {
        view = new CursosUsuarioPersonalView();


        Set<String> proveedores = model.getAll().stream()
                .map(Capacitacion::getProveedor)
                .collect(Collectors.toSet());
        view.setProviders(proveedores.stream().sorted().toList());

        view.showCapacitaciones(model.getAll(), this::onSubscribe);

        view.setFilterListener(() -> {
            String prov = view.getSelectedProvider();
            List<Capacitacion> filtrados = prov.equals("Todos")
                    ? model.getAll()
                    : model.buscarPorProveedor(prov);
            view.showCapacitaciones(filtrados, this::onSubscribe);
        });
    }

    private void onSubscribe(Capacitacion c) {
        try {
            Desktop.getDesktop().browse(new java.net.URI(c.getUrlCertificado()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view,
                    "No se pudo abrir el enlace:\n" + c.getUrlCertificado(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public CursosUsuarioPersonalView getView() {
        return view;
    }
}