package entities;

import java.time.LocalDate;

public class Vacante {
    private final int id;
    private final int idEmpresa;
    private final String titulo;
    private final String descripcion;
    private final double salario;
    private final String ubicacion;
    private final Modalidad modalidad;
    private final LocalDate fechaPublicacion;

    public enum Modalidad {
        PRESENCIAL, REMOTO, HIBRIDO
    }

    private Vacante(Builder builder) {
        this.id = builder.id;
        this.idEmpresa = builder.idEmpresa;
        this.titulo = builder.titulo;
        this.descripcion = builder.descripcion;
        this.salario = builder.salario;
        this.ubicacion = builder.ubicacion;
        this.modalidad = builder.modalidad;
        this.fechaPublicacion = builder.fechaPublicacion;
    }

    public static class Builder {
        private int id;
        private int idEmpresa;
        private String titulo;
        private String descripcion;
        private double salario;
        private String ubicacion;
        private Modalidad modalidad;
        private LocalDate fechaPublicacion = LocalDate.now();

        public Builder id(int id) { this.id = id; return this; }
        public Builder idEmpresa(int idEmpresa) { this.idEmpresa = idEmpresa; return this; }
        public Builder titulo(String titulo) { this.titulo = titulo; return this; }
        public Builder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public Builder salario(double salario) { this.salario = salario; return this; }
        public Builder ubicacion(String ubicacion) { this.ubicacion = ubicacion; return this; }
        public Builder modalidad(Modalidad modalidad) { this.modalidad = modalidad; return this; }
        public Builder fechaPublicacion(LocalDate fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; return this; }

        public Vacante build() {
            return new Vacante(this);
        }
    }

    public int getId() { return id; }
    public int getIdEmpresa() { return idEmpresa; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public double getSalario() { return salario; }
    public String getUbicacion() { return ubicacion; }
    public Modalidad getModalidad() { return modalidad; }
    public LocalDate getFechaPublicacion() { return fechaPublicacion; }
}
