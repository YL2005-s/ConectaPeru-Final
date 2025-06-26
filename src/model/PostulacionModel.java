package model;

import entities.Postulacion;
import entities.UsuarioPersonal;
import entities.Vacante;
import model.repository.impl.PostulacionRepository;

import java.util.*;

public class PostulacionModel {
    private final PostulacionRepository repository = new PostulacionRepository();
    private final Map<Integer, List<Postulacion>> postulacionesPorUsuario = new HashMap<>();
    private final Map<Integer, List<Postulacion>> postulacionesPorVacante = new HashMap<>();

    public void loadFromDB() {
        postulacionesPorUsuario.clear();
        postulacionesPorVacante.clear();

        for (Postulacion p : repository.searchAll()) {
            postulacionesPorUsuario
                    .computeIfAbsent(p.getIdUsuario(), k -> new ArrayList<>())
                    .add(p);
            postulacionesPorVacante
                    .computeIfAbsent(p.getIdVacante(), k -> new ArrayList<>())
                    .add(p);
        }
    }

    public boolean hasPostulado(int idUsuario, int idVacante) {
        return postulacionesPorUsuario.getOrDefault(idUsuario, List.of())
                .stream().anyMatch(p -> p.getIdVacante() == idVacante);
    }

    public boolean postular(UsuarioPersonal usuario, Vacante vacante) {
        if (hasPostulado(usuario.getId(), vacante.getId())) return false;

        Postulacion p = new Postulacion.Builder()
                .idUsuario(usuario.getId())
                .idVacante(vacante.getId())
                .usuario(usuario)
                .vacante(vacante)
                .build();

        boolean ok = repository.create(p);
        if (ok) {
            postulacionesPorUsuario.computeIfAbsent(usuario.getId(), k -> new ArrayList<>()).add(p);
            postulacionesPorVacante.computeIfAbsent(vacante.getId(), k -> new ArrayList<>()).add(p);
        }
        return ok;
    }

    public boolean cancelar(int idUsuario, int idVacante) {
        Optional<Postulacion> toDelete = postulacionesPorUsuario.getOrDefault(idUsuario, List.of())
                .stream().filter(p -> p.getIdVacante() == idVacante).findFirst();

        if (toDelete.isEmpty()) return false;

        boolean deleted = repository.deleteById(toDelete.get().getId());
        if (deleted) {
            postulacionesPorUsuario.get(idUsuario).remove(toDelete.get());
            postulacionesPorVacante.get(idVacante).remove(toDelete.get());
        }
        return deleted;
    }

    public List<Postulacion> getByUsuario(int idUsuario) {
        return postulacionesPorUsuario.getOrDefault(idUsuario, List.of());
    }

    public List<Postulacion> getByVacante(int idVacante) {
        return postulacionesPorVacante.getOrDefault(idVacante, List.of());
    }
}
