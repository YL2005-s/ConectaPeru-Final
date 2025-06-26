package entities;

public class Capacitacion {
    private final int id;
    private final String titulo;
    private final String descripcion;
    private final String proveedor;
    private final String urlCertificado;

    private Capacitacion(Builder builder) {
        this.id = builder.id;
        this.titulo = builder.titulo;
        this.descripcion = builder.descripcion;
        this.proveedor = builder.proveedor;
        this.urlCertificado = builder.urlCertificado;
    }

    public static class Builder {
        private int id;
        private String titulo;
        private String descripcion;
        private String proveedor;
        private String urlCertificado;

        public Builder id(int id) { this.id = id; return this; }
        public Builder titulo(String titulo) { this.titulo = titulo; return this; }
        public Builder descripcion(String descripcion) { this.descripcion = descripcion; return this; }
        public Builder proveedor(String proveedor) { this.proveedor = proveedor; return this; }
        public Builder urlCertificado(String url) { this.urlCertificado = url; return this; }

        public Capacitacion build() {
            return new Capacitacion(this);
        }
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getProveedor() { return proveedor; }
    public String getUrlCertificado() { return urlCertificado; }
}
