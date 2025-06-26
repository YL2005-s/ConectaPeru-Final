package config;

public class Session {
    private static final Session instance = new Session();
    private Object currentUser;

    private Session() {}

    public static Session get() {
        return instance;
    }

    public void setUsuario(Object user) {
        this.currentUser = user;
    }

    public Object getUsuario() {
        return currentUser;
    }

    public void clear() {
        this.currentUser = null;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}
