package model;

import entities.UsuarioPersonal;
import model.repository.impl.UsuarioPersonalRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UsuarioPersonalModel {
    private final Map<String, UsuarioPersonal> usersByDni = new HashMap<>();
    private final UsuarioPersonalRepository repository = new UsuarioPersonalRepository();

    public void loadFromDB() {
        usersByDni.clear();
        for (UsuarioPersonal user : repository.searchAll()) {
            usersByDni.put(user.getDni(), user);
        }
    }

    public UsuarioPersonal searchByDni(String dni) {
        return usersByDni.get(dni);
    }

    public boolean exists(String dni) {
        return usersByDni.containsKey(dni);
    }

    public boolean validateLogin(String dni, String password) {
        UsuarioPersonal user = searchByDni(dni);
        return user != null && user.getContraseña().equals(password);
    }

    public void add(UsuarioPersonal user) {
        repository.create(user);
        usersByDni.put(user.getDni(), user);
    }

    public void update(UsuarioPersonal user) {
        repository.update(user);
        usersByDni.put(user.getDni(), user);
    }

    public void delete(String dni) {
        UsuarioPersonal user = searchByDni(dni);
        if (user != null) {
            repository.deleteById(user.getId());
            usersByDni.remove(dni);
        }
    }

    public Collection<UsuarioPersonal> listAll() {
        return usersByDni.values();
    }
}
