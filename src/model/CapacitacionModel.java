package model;

import entities.Capacitacion;
import model.repository.impl.CapacitacionRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CapacitacionModel {
    private final Map<Integer, Capacitacion> capacitacionesById = new HashMap<>();
    private final CapacitacionRepository repository = new CapacitacionRepository();

    public void loadFromDB() {
        capacitacionesById.clear();
        for (Capacitacion c : repository.searchAll()) {
            capacitacionesById.put(c.getId(), c);
        }
    }

    public List<Capacitacion> getAll() {
        return new ArrayList<>(capacitacionesById.values());
    }

    public Capacitacion getById(int id) {
        return capacitacionesById.get(id);
    }

    public List<Capacitacion> buscarPorProveedor(String proveedor) {
        return capacitacionesById.values().stream()
                .filter(c -> c.getProveedor().equalsIgnoreCase(proveedor))
                .collect(Collectors.toList());
    }

    public void add(Capacitacion c) {
        repository.create(c);
        capacitacionesById.put(c.getId(), c);
    }

    public void update(Capacitacion c) {
        repository.update(c);
        capacitacionesById.put(c.getId(), c);
    }

    public void delete(int id) {
        repository.deleteById(id);
        capacitacionesById.remove(id);
    }
}
