package model.repository.impl;


import entities.Capacitacion;
import entities.CertificadoUsuario;
import entities.UsuarioPersonal;
import model.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CertificadoUsuarioRepository extends Repository<CertificadoUsuario> {

    @Override
    public boolean create(CertificadoUsuario certificado) {
        return executeTransaction(() -> {
            if (certificado.getIdUsuario() == 0) return false;

            String sql = "INSERT INTO certificados_usuario (id_usuario, id_capacitacion, fecha_obtencion) VALUES (?, ?, ?)";
            return executeUpdate(sql, stmt -> {
                stmt.setInt(1, certificado.getIdUsuario());
                stmt.setInt(2, certificado.getCapacitacion().getId());
                stmt.setDate(3, java.sql.Date.valueOf(certificado.getFechaObtencion()));
            }) > 0;
        });
    }

    @Override
    public boolean update(CertificadoUsuario certificado) {
        String sql = "UPDATE certificados_usuario SET fecha_obtencion = ? WHERE id = ?";
        return executeUpdate(sql, stmt -> {
            stmt.setDate(1, java.sql.Date.valueOf(certificado.getFechaObtencion()));
            stmt.setInt(2, certificado.getId());
        }) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return executeUpdate("DELETE FROM certificados_usuario WHERE id = ?", stmt -> stmt.setInt(1, id)) > 0;
    }

    @Override
    public CertificadoUsuario searchById(int id) {
        String sql = """
            SELECT cu.id, cu.fecha_obtencion,
                   u.dni, u.correo, u.contraseña, u.telefono,
                   up.nombre, up.apellido, up.fecha_nacimiento, up.experiencia, up.habilidades, up.formacion,
                   c.id AS id_cap, c.titulo, c.descripcion, c.proveedor, c.url_certificado
            FROM certificados_usuario cu
            JOIN usuarios u ON cu.id_usuario = u.id
            JOIN usuarios_personales up ON u.id = up.id
            JOIN capacitaciones c ON cu.id_capacitacion = c.id
            WHERE cu.id = ?
        """;
        return executeQueryOne(sql, stmt -> stmt.setInt(1, id), this::mapToEntity);
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM certificados_usuario WHERE id = ?)";
        Boolean result = executeQueryOne(sql, stmt -> stmt.setInt(1, id), rs -> rs.getBoolean(1));
        return result != null && result;
    }

    @Override
    public List<CertificadoUsuario> searchAll() {
        String sql = """
            SELECT cu.id, cu.fecha_obtencion,
                   u.dni, u.correo, u.contraseña, u.telefono,
                   up.nombre, up.apellido, up.fecha_nacimiento, up.experiencia, up.habilidades, up.formacion,
                   c.id AS id_cap, c.titulo, c.descripcion, c.proveedor, c.url_certificado
            FROM certificados_usuario cu
            JOIN usuarios u ON cu.id_usuario = u.id
            JOIN usuarios_personales up ON u.id = up.id
            JOIN capacitaciones c ON cu.id_capacitacion = c.id
        """;
        return executeQueryList(sql, stmt -> {}, this::mapToEntity);
    }

    private Integer getIdUsuarioByDni(String dni) {
        String sql = "SELECT u.id FROM usuarios u JOIN usuarios_personales up ON u.id = up.id WHERE u.dni = ?";
        return executeQueryOne(sql, stmt -> stmt.setString(1, dni), rs -> rs.getInt(1));
    }

    private CertificadoUsuario mapToEntity(ResultSet rs) throws SQLException {
        UsuarioPersonal usuario = new UsuarioPersonal.Builder()
                .dni(rs.getString("dni"))
                .correo(rs.getString("correo"))
                .contraseña(rs.getString("contraseña"))
                .telefono(rs.getString("telefono"))
                .nombre(rs.getString("nombre"))
                .apellido(rs.getString("apellido"))
                .fechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate())
                .experiencia(rs.getString("experiencia"))
                .habilidades(rs.getString("habilidades"))
                .formacion(rs.getString("formacion"))
                .build();

        Capacitacion cap = new Capacitacion.Builder()
                .id(rs.getInt("id_cap"))
                .titulo(rs.getString("titulo"))
                .descripcion(rs.getString("descripcion"))
                .proveedor(rs.getString("proveedor"))
                .urlCertificado(rs.getString("url_certificado"))
                .build();

        return new CertificadoUsuario.Builder()
                .id(rs.getInt("id"))
                .usuario(usuario)
                .capacitacion(cap)
                .fechaObtencion(rs.getDate("fecha_obtencion").toLocalDate())
                .build();
    }
}
