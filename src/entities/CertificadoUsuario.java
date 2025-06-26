package entities;

import java.time.LocalDate;

public class CertificadoUsuario {
    private final int id;
    private final int idUsuario;
    private final int idCapacitacion;
    private final UsuarioPersonal usuario;
    private final Capacitacion capacitacion;
    private final LocalDate fechaObtencion;

    private CertificadoUsuario(Builder builder) {
        this.id = builder.id;
        this.idUsuario = builder.idUsuario;
        this.idCapacitacion = builder.idCapacitacion;
        this.usuario = builder.usuario;
        this.capacitacion = builder.capacitacion;
        this.fechaObtencion = builder.fechaObtencion;
    }

    public static class Builder {
        private int id;
        private int idUsuario;
        private int idCapacitacion;
        private UsuarioPersonal usuario;
        private Capacitacion capacitacion;
        private LocalDate fechaObtencion;

        public Builder id(int id) { this.id = id; return this; }
        public Builder idUsuario(int idUsuario) { this.idUsuario = idUsuario; return this; }
        public Builder idCapacitacion(int idCapacitacion) { this.idCapacitacion = idCapacitacion; return this; }
        public Builder usuario(UsuarioPersonal usuario) { this.usuario = usuario; return this; }
        public Builder capacitacion(Capacitacion capacitacion) { this.capacitacion = capacitacion; return this; }
        public Builder fechaObtencion(LocalDate fechaObtencion) { this.fechaObtencion = fechaObtencion; return this; }

        public CertificadoUsuario build() {
            return new CertificadoUsuario(this);
        }
    }

    public int getId() { return id; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdCapacitacion() { return idCapacitacion; }
    public UsuarioPersonal getUsuario() { return usuario; }
    public Capacitacion getCapacitacion() { return capacitacion; }
    public LocalDate getFechaObtencion() { return fechaObtencion; }
}
