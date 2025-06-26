package entities;

import java.time.LocalDate;

public class UsuarioPersonal extends Usuario {
    private final String nombre;
    private final String apellido;
    private final LocalDate fechaNacimiento;
    private final String experiencia;
    private final String habilidades;
    private final String formacion;

    private UsuarioPersonal(Builder builder) {
        super(builder);
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.experiencia = builder.experiencia;
        this.habilidades = builder.habilidades;
        this.formacion = builder.formacion;
    }

    public static class Builder extends Usuario.Builder<Builder> {
        private String nombre;
        private String apellido;
        private LocalDate fechaNacimiento;
        private String experiencia;
        private String habilidades;
        private String formacion;

        public Builder nombre(String nombre) { this.nombre = nombre; return this; }
        public Builder apellido(String apellido) { this.apellido = apellido; return this; }
        public Builder fechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; return this; }
        public Builder experiencia(String experiencia) { this.experiencia = experiencia; return this; }
        public Builder habilidades(String habilidades) { this.habilidades = habilidades; return this; }
        public Builder formacion(String formacion) { this.formacion = formacion; return this; }

        @Override
        protected Builder self() { return this; }

        @Override
        public UsuarioPersonal build() {
            super.tipo(TipoUsuario.CANDIDATO);
            return new UsuarioPersonal(this);
        }
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public String getExperiencia() { return experiencia; }
    public String getHabilidades() { return habilidades; }
    public String getFormacion() { return formacion; }
}
