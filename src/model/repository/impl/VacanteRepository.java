package model.repository.impl;
import entities.Vacante;
import model.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class VacanteRepository extends Repository<Vacante> {

    @Override
    public boolean create(Vacante vacante) {
        return executeTransaction(() -> {
            if (vacante.getIdEmpresa() == 0) return false;

            String sql = """
                INSERT INTO vacantes (id_empresa, titulo, descripcion, salario, ubicacion, modalidad, fecha_publicacion)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

            int filas = executeUpdate(sql, stmt -> {
                stmt.setInt(1, vacante.getIdEmpresa());
                stmt.setString(2, vacante.getTitulo());
                stmt.setString(3, vacante.getDescripcion());
                stmt.setDouble(4, vacante.getSalario());
                stmt.setString(5, vacante.getUbicacion());
                stmt.setString(6, vacante.getModalidad().name());
                stmt.setDate(7, java.sql.Date.valueOf(vacante.getFechaPublicacion()));
            });

            return filas > 0;
        });
    }

    @Override
    public boolean update(Vacante vacante) {
        String sql = """
            UPDATE vacantes SET titulo = ?, descripcion = ?, salario = ?, ubicacion = ?, modalidad = ?
            WHERE id = ?
        """;
        int filas = executeUpdate(sql, stmt -> {
            stmt.setString(1, vacante.getTitulo());
            stmt.setString(2, vacante.getDescripcion());
            stmt.setDouble(3, vacante.getSalario());
            stmt.setString(4, vacante.getUbicacion());
            stmt.setString(5, vacante.getModalidad().name());
            stmt.setInt(6, vacante.getId());
        });
        return filas > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return executeUpdate("DELETE FROM vacantes WHERE id = ?", stmt -> stmt.setInt(1, id)) > 0;
    }

    @Override
    public Vacante searchById(int id) {
        String sql = "SELECT * FROM vacantes WHERE id = ?";
        return executeQueryOne(sql, stmt -> stmt.setInt(1, id), this::mapToEntity);
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM vacantes WHERE id = ?)";
        Boolean result = executeQueryOne(sql, stmt -> stmt.setInt(1, id), rs -> rs.getBoolean(1));
        return result != null && result;
    }

    @Override
    public List<Vacante> searchAll() {
        String sql = "SELECT * FROM vacantes";
        return executeQueryList(sql, stmt -> {}, this::mapToEntity);
    }

    private Vacante mapToEntity(ResultSet rs) throws SQLException {
        return new Vacante.Builder()
                .id(rs.getInt("id"))
                .idEmpresa(rs.getInt("id_empresa"))
                .titulo(rs.getString("titulo"))
                .descripcion(rs.getString("descripcion"))
                .salario(rs.getDouble("salario"))
                .ubicacion(rs.getString("ubicacion"))
                .modalidad(Vacante.Modalidad.valueOf(rs.getString("modalidad")))
                .fechaPublicacion(rs.getDate("fecha_publicacion").toLocalDate())
                .build();
    }
}
