package entities;

public abstract class Usuario {
    protected int id;
    protected String dni;
    protected String correo;
    protected String contraseña;
    protected String telefono;
    protected TipoUsuario tipo;

    public enum TipoUsuario {
        CANDIDATO, EMPRESA, ADMIN
    }

    protected Usuario(Builder<?> builder) {
        this.id = builder.id;
        this.dni = builder.dni;
        this.correo = builder.correo;
        this.contraseña = builder.contraseña;
        this.telefono = builder.telefono;
        this.tipo = builder.tipo;
    }

    public abstract static class Builder<T extends Builder<T>> {
        private int id;
        private String dni;
        private String correo;
        private String contraseña;
        private String telefono;
        private TipoUsuario tipo;

        public T id(int id) {
            this.id = id;
            return self();
        }

        public T dni(String dni) {
            this.dni = dni;
            return self();
        }

        public T correo(String correo) {
            this.correo = correo;
            return self();
        }

        public T contraseña(String contraseña) {
            this.contraseña = contraseña;
            return self();
        }

        public T telefono(String telefono) {
            this.telefono = telefono;
            return self();
        }

        public T tipo(TipoUsuario tipo) {
            this.tipo = tipo;
            return self();
        }

        protected abstract T self();

        public abstract Usuario build();
    }

    public int getId() { return id; }
    public String getDni() { return dni; }
    public String getCorreo() { return correo; }
    public String getContraseña() { return contraseña; }
    public String getTelefono() { return telefono; }
    public TipoUsuario getTipo() { return tipo; }
}
