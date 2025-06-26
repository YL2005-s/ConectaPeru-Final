package model;

import entities.UsuarioEmpresarial;
import model.repository.impl.UsuarioEmpresarialRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UsuarioEmpresarialModel {
    private final Map<String, UsuarioEmpresarial> usersByDni = new HashMap<>();
    private final UsuarioEmpresarialRepository repository = new UsuarioEmpresarialRepository();

    public void loadFromDB() {
        usersByDni.clear();
        for (UsuarioEmpresarial u : repository.searchAll()) {
            usersByDni.put(u.getDni(), u);
        }
    }

    public UsuarioEmpresarial searchByDni(String dni) {
        return usersByDni.get(dni);
    }

    public boolean exists(String dni) {
        return usersByDni.containsKey(dni);
    }

    public boolean validateLogin(String dni, String pass) {
        UsuarioEmpresarial u = searchByDni(dni);
        return u != null && u.getContraseña().equals(pass);
    }

    public void add(UsuarioEmpresarial u) {
        repository.create(u);
        usersByDni.put(u.getDni(), u);
    }

    public void update(UsuarioEmpresarial u) {
        repository.update(u);
        usersByDni.put(u.getDni(), u);
    }

    public void delete(String dni) {
        UsuarioEmpresarial u = searchByDni(dni);
        if (u != null) {
            repository.deleteById(u.getId());
            usersByDni.remove(dni);
        }
    }

    public Collection<UsuarioEmpresarial> listarTodos() {
        return usersByDni.values();
    }
}
