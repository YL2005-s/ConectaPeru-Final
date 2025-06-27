package controller;

import config.Session;
import core.Controller;
import core.Observer;
import entities.Postulacion;
import entities.UsuarioPersonal;
import model.PostulacionModel;
import view.app.PostulacionesUsuarioPersonalView;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PostulacionesUsuarioPersonalController extends Controller implements Observer<Object> {
    private final PostulacionModel model;
    private PostulacionesUsuarioPersonalView view;

    public PostulacionesUsuarioPersonalController(PostulacionModel model) {
        this.model = model;
        Session.get().addObserver(this);
    }

    @Override
    public void run() {
        this.view = new PostulacionesUsuarioPersonalView();
        view.setFilterListener(this::filter);
    }

    @Override
    public void update(Object value) {
        if (value instanceof Session session && session.getUsuario() instanceof UsuarioPersonal u) {
            List<Postulacion> postulaciones = model.getByUsuario(u.getId());
            view.showPostulaciones(postulaciones);
        }
    }

    private void filter() {
        String estado = view.getSelectedEstado();
        List<Postulacion> filtradas = getFilteredPostulations(estado);
        view.showPostulaciones(filtradas);
    }

    private List<Postulacion> getFilteredPostulations(String estado) {
        UsuarioPersonal u = (UsuarioPersonal) Session.get().getUsuario();
        return model.getByUsuario(u.getId()).stream()
                .filter(p -> estado.equals("Todos") || p.getEstado().name().equalsIgnoreCase(estado))
                .collect(Collectors.toList());
    }

    public PostulacionesUsuarioPersonalView getView() {
        return view;
    }
}