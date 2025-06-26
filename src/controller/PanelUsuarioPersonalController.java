package controller;

import config.Session;
import core.Controller;
import entities.UsuarioPersonal;
import entities.Vacante;
import model.PostulacionModel;
import model.VacanteModel;
import view.app.PanelUsuarioPersonalView;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class PanelUsuarioPersonalController extends Controller {
    private PanelUsuarioPersonalView view;
    private final VacanteModel vacanteModel;
    private final PostulacionModel postulacionModel;

    public PanelUsuarioPersonalController(VacanteModel vacanteModel, PostulacionModel postulacionModel) {
        this.vacanteModel = vacanteModel;
        this.postulacionModel = postulacionModel;
    }
    @Override
    public void run() {
        this.view = new PanelUsuarioPersonalView();

        view.setLocationOptions (vacanteModel.getUniqueLocations());
        setupListener();
        showAllVacant();
    }

    private void setupListener() {
        view.setSearchListener(texto -> {
            String location = view.getSelectedLocation();
            List<Vacante> result = vacanteModel.getAll();

            if (!texto.isEmpty()) {
                result = result.stream()
                        .filter(v -> v.getTitulo().toLowerCase().contains(texto.toLowerCase()) ||
                                v.getDescripcion().toLowerCase().contains(texto.toLowerCase()))
                        .collect(Collectors.toList());
            }

            if (location != null && !location.equals("Todas")) {
                result = result.stream()
                        .filter(v -> location.equalsIgnoreCase(v.getUbicacion()))
                        .collect(Collectors.toList());
            }

            view.showVacant(result, this::onApplyClicked);
        });
    }

    private void onApplyClicked(Vacante vacante) {
        UsuarioPersonal user = (UsuarioPersonal) Session.get().getUsuario();
        if (user == null) return;

        boolean postulated = postulacionModel.hasPostulado(user.getId(), vacante.getId());

        if (postulated) {
            int confirm = JOptionPane.showConfirmDialog(view,
                    "\u00a1Ya est\u00e1s postulado!\n\u00bfDeseas cancelar tu postulaci\u00f3n?",
                    "Cancelar postulaci\u00f3n",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean ok = postulacionModel.cancelar(user.getId(), vacante.getId());
                String msg = ok ? "Postulaci\u00f3n cancelada." : "No se pudo cancelar.";
                JOptionPane.showMessageDialog(view, msg);
                showAllVacant();
            }
        } else {
            JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            panel.add(new JLabel("¿Por qué deseas postularte?"));
            JTextArea taMotivo = new JTextArea(4, 30);
            taMotivo.setLineWrap(true);
            taMotivo.setWrapStyleWord(true);
            JScrollPane spMotivo = new JScrollPane(taMotivo);
            panel.add(spMotivo);

            panel.add(new JLabel("¿Qué te hace ideal para el puesto?"));
            JTextArea taRazon = new JTextArea(4, 30);
            taRazon.setLineWrap(true);
            taRazon.setWrapStyleWord(true);
            JScrollPane spRazon = new JScrollPane(taRazon);
            panel.add(spRazon);

            int res = JOptionPane.showConfirmDialog(null, panel,
                    "Formulario de postulación", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (res == JOptionPane.OK_OPTION) {
                boolean ok = postulacionModel.postular(user, vacante);
                String msg = ok ? "¡Te has postulado correctamente!" : "¡Ya te postulaste a este trabajo!.";
                JOptionPane.showMessageDialog(null, msg);
                showAllVacant();
            }
        }

    }

    private void showAllVacant() {
        view.showVacant(vacanteModel.getAll(), this::onApplyClicked);
    }

    public PanelUsuarioPersonalView getView() {
        return view;
    }
}