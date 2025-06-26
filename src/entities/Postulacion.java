package entities;

import java.time.LocalDate;

public class Postulacion {
    private final int id;
    private final int idUsuario;
    private final int idVacante;
    private final UsuarioPersonal usuario;
    private final Vacante vacante;
    private final LocalDate fechaPostulacion;
    private final Estado estado;

    public enum Estado {
        ENVIADO, EN_PROCESO, RECHAZADO, ACEPTADO
    }

    private Postulacion(Builder builder) {
        this.id = builder.id;
        this.idUsuario = builder.idUsuario;
        this.idVacante = builder.idVacante;
        this.usuario = builder.usuario;
        this.vacante = builder.vacante;
        this.fechaPostulacion = builder.fechaPostulacion;
        this.estado = builder.estado;
    }

    public static class Builder {
        private int id;
        private int idUsuario;
        private int idVacante;
        private UsuarioPersonal usuario;
        private Vacante vacante;
        private LocalDate fechaPostulacion = LocalDate.now();
        private Estado estado = Estado.ENVIADO;

        public Builder id(int id) { this.id = id; return this; }
        public Builder idUsuario(int idUsuario) { this.idUsuario = idUsuario; return this; }
        public Builder idVacante(int idVacante) { this.idVacante = idVacante; return this; }
        public Builder usuario(UsuarioPersonal usuario) { this.usuario = usuario; return this; }
        public Builder vacante(Vacante vacante) { this.vacante = vacante; return this; }
        public Builder fechaPostulacion(LocalDate fechaPostulacion) { this.fechaPostulacion = fechaPostulacion; return this; }
        public Builder estado(Estado estado) { this.estado = estado; return this; }

        public Postulacion build() {
            return new Postulacion(this);
        }
    }

    public int getId() { return id; }
    public int getIdUsuario() { return idUsuario; }
    public int getIdVacante() { return idVacante; }
    public UsuarioPersonal getUsuario() { return usuario; }
    public Vacante getVacante() { return vacante; }
    public LocalDate getFechaPostulacion() { return fechaPostulacion; }
    public Estado getEstado() { return estado; }
}
