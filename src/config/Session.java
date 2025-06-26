package config;

import core.Observable;

public class Session extends Observable<Object> {
    private static final Session instance = new Session();
    private Object currentUser;

    private Session() {}

    public static Session get() {
        return instance;
    }

    public void setUsuario(Object user) {
        this.currentUser = user;
        notifyObserver(user);
    }

    public void clear() {
        this.currentUser = null;
        notifyObserver(null);
    }

    public Object getUsuario() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
