package entities;

public class UsuarioEmpresarial extends Usuario {
    private final String nombreEmpresa;
    private final String descripcion;
    private final String ubicacion;

    private UsuarioEmpresarial(Builder builder) {
        super(builder);
        this.nombreEmpresa = builder.nombreEmpresa;
        this.descripcion = builder.descripcion;
        this.ubicacion = builder.ubicacion;
    }

    public static class Builder extends Usuario.Builder<Builder> {
        private String nombreEmpresa;
        private String descripcion;
        private String ubicacion;

        public Builder nombreEmpresa(String nombreEmpresa) { this.nombreEmpresa = nombreEmpresa; return this; }
        public Builder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public Builder ubicacion(String ubicacion) { this.ubicacion = ubicacion; return this; }

        @Override
        protected Builder self() { return this; }

        @Override
        public UsuarioEmpresarial build() {
            super.tipo(TipoUsuario.EMPRESA);
            return new UsuarioEmpresarial(this);
        }
    }

    public String getNombreEmpresa() { return nombreEmpresa; }
    public String getDescripcion() { return descripcion; }
    public String getUbicacion() { return ubicacion; }
}
