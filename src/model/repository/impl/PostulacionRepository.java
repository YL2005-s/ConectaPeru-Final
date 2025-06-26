package model.repository.impl;


import entities.Postulacion;
import entities.UsuarioPersonal;
import entities.Vacante;
import model.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PostulacionRepository extends Repository<Postulacion> {

    @Override
    public boolean create(Postulacion postulacion) {
        return executeTransaction(() -> {
            if (postulacion.getIdUsuario() == 0) return false;

            String sql = "INSERT INTO postulaciones (id_usuario, id_vacante, fecha_postulacion, estado) VALUES (?, ?, ?, ?)";
            int filas = executeUpdate(sql, stmt -> {
                stmt.setInt(1, postulacion.getIdUsuario());
                stmt.setInt(2, postulacion.getVacante().getId());
                stmt.setDate(3, java.sql.Date.valueOf(postulacion.getFechaPostulacion()));
                stmt.setString(4, postulacion.getEstado().name());
            });

            return filas > 0;
        });
    }

    @Override
    public boolean update(Postulacion postulacion) {
        String sql = "UPDATE postulaciones SET estado = ? WHERE id = ?";
        int filas = executeUpdate(sql, stmt -> {
            stmt.setString(1, postulacion.getEstado().name());
            stmt.setInt(2, postulacion.getId());
        });
        return filas > 0;
    }

    @Override
    public boolean deleteById(int id) {
        return executeUpdate("DELETE FROM postulaciones WHERE id = ?", stmt -> stmt.setInt(1, id)) > 0;
    }

    @Override
    public Postulacion searchById(int id) {
        String sql = """
            SELECT p.id, p.fecha_postulacion, p.estado,
                   u.dni, u.correo, u.contraseña, u.telefono,
                   up.nombre, up.apellido, up.fecha_nacimiento, up.experiencia, up.habilidades, up.formacion,
                   v.id AS id_vac, v.titulo, v.descripcion, v.salario, v.ubicacion, v.modalidad, v.fecha_publicacion
            FROM postulaciones p
            JOIN usuarios u ON p.id_usuario = u.id
            JOIN usuarios_personales up ON u.id = up.id
            JOIN vacantes v ON p.id_vacante = v.id
            WHERE p.id = ?
        """;

        return executeQueryOne(sql, stmt -> stmt.setInt(1, id), this::mapToEntity);
    }

    @Override
    public boolean existsById(int id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM postulaciones WHERE id = ?)";
        Boolean result = executeQueryOne(sql, stmt -> stmt.setInt(1, id), rs -> rs.getBoolean(1));
        return result != null && result;
    }

    @Override
    public List<Postulacion> searchAll() {
        String sql = """
            SELECT p.id, p.fecha_postulacion, p.estado,
                   u.dni, u.correo, u.contraseña, u.telefono,
                   up.nombre, up.apellido, up.fecha_nacimiento, up.experiencia, up.habilidades, up.formacion,
                   v.id AS id_vac, v.titulo, v.descripcion, v.salario, v.ubicacion, v.modalidad, v.fecha_publicacion
            FROM postulaciones p
            JOIN usuarios u ON p.id_usuario = u.id
            JOIN usuarios_personales up ON u.id = up.id
            JOIN vacantes v ON p.id_vacante = v.id
        """;

        return executeQueryList(sql, stmt -> {}, this::mapToEntity);
    }

    private Integer getIdUsuarioByDni(String dni) {
        String sql = "SELECT u.id FROM usuarios u JOIN usuarios_personales up ON u.id = up.id WHERE u.dni = ?";
        return executeQueryOne(sql, stmt -> stmt.setString(1, dni), rs -> rs.getInt(1));
    }

    private Postulacion mapToEntity(ResultSet rs) throws SQLException {
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

        Vacante vacante = new Vacante.Builder()
                .id(rs.getInt("id_vac"))
                .titulo(rs.getString("titulo"))
                .descripcion(rs.getString("descripcion"))
                .salario(rs.getDouble("salario"))
                .ubicacion(rs.getString("ubicacion"))
                .modalidad(Vacante.Modalidad.valueOf(rs.getString("modalidad")))
                .fechaPublicacion(rs.getDate("fecha_publicacion").toLocalDate())
                .build();

        return new Postulacion.Builder()
                .id(rs.getInt("id"))
                .usuario(usuario)
                .vacante(vacante)
                .fechaPostulacion(rs.getDate("fecha_postulacion").toLocalDate())
                .estado(Postulacion.Estado.valueOf(rs.getString("estado")))
                .build();
    }
}
