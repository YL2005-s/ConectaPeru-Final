package model;

import entities.Vacante;
import model.repository.impl.VacanteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class VacanteModel {
    private final List<Vacante> vacantes = new ArrayList<>();
    private final VacanteRepository repository = new VacanteRepository();

    public void loadFromDB() {
        vacantes.clear();
        vacantes.addAll(repository.searchAll());
    }

    public List<Vacante> getAll() {
        return new ArrayList<>(vacantes);
    }

    public boolean deleteById(int id) {
        return repository.deleteById(id);
    }

    public List<Vacante> searchByText(String text) {
        text = text.toLowerCase();
        List<Vacante> resultado = new ArrayList<>();
        for (Vacante v : vacantes) {
            if (v.getTitulo().toLowerCase().contains(text) ||
                    v.getDescripcion().toLowerCase().contains(text) ||
                    v.getUbicacion().toLowerCase().contains(text)) {
                resultado.add(v);
            }
        }
        return resultado;
    }

    public List<String> getUniqueLocations() {
        Set<String> ubicaciones = new TreeSet<>();
        for (Vacante v : vacantes) {
            ubicaciones.add(v.getUbicacion());
        }
        return new ArrayList<>(ubicaciones);
    }
}