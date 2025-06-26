package model.repository.impl;

import entities.Capacitacion;
import model.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CapacitacionRepository extends Repository<Capacitacion> {

    @Override
    public boolean create(Capacitacion cap) {
        String sql = "INSERT INTO capacitaciones (titulo, descripcion, proveedor, url_certificado) VALUES (?, ?, ?, ?)";
        return executeUpdate(sql, stmt -> {
            stmt.setString(1, cap.getTitulo());
            stmt.setString(2, cap.getDescripcion());
            stmt.setString(3, cap.getProveedor());
            stmt.setString(4, cap.getUrlCertificado());
        }) > 0;
    }

    @Override
    public boolean update(Capacitacion cap) {
        String sql = "UPDATE capacitaciones SET titulo = ?, descripcion = ?, proveedor = ?, url_certificado = ? WHERE id = ?";
        return executeUpdate(sql, stmt -> {
            stmt.setString(1, cap.getTitulo());
            stmt.setString(2, cap.getDescripcion());
            stmt.setString(3, cap.getProveedor());
            stmt.setString(4, cap.getUrlCertificado());
            stmt.setInt(5, cap.getId());
        }) > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return executeUpdate("DELETE FROM capacitaciones WHERE id = ?", stmt -> stmt.setInt(1, id)) > 0;
    }

    @Override
    public Capacitacion searchById(int id) {
        return executeQueryOne("SELECT * FROM capacitaciones WHERE id = ?", stmt -> stmt.setInt(1, id), this::mapToEntity);
    }

    @Override
    public boolean existsById(int id) {
        return Boolean.TRUE.equals(executeQueryOne("SELECT EXISTS(SELECT 1 FROM capacitaciones WHERE id = ?)",
                stmt -> stmt.setInt(1, id), rs -> rs.getBoolean(1)));
    }

    @Override
    public List<Capacitacion> searchAll() {
        return executeQueryList("SELECT * FROM capacitaciones", stmt -> {}, this::mapToEntity);
    }

    private Capacitacion mapToEntity(ResultSet rs) throws SQLException {
        return new Capacitacion.Builder()
                .id(rs.getInt("id"))
                .titulo(rs.getString("titulo"))
                .descripcion(rs.getString("descripcion"))
                .proveedor(rs.getString("proveedor"))
                .urlCertificado(rs.getString("url_certificado"))
                .build();
    }
}
