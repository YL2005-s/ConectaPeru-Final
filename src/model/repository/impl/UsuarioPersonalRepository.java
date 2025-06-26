package model.repository.impl;

import entities.Usuario;
import entities.UsuarioPersonal;
import model.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioPersonalRepository extends Repository<UsuarioPersonal> {

    @Override
    public boolean create(UsuarioPersonal user) {
        return executeTransaction(() -> {
            String sqlUsuario = "INSERT INTO usuarios (dni, correo, contraseña, telefono, tipo) VALUES (?, ?, ?, ?, ?)";
            int filas = executeUpdate(sqlUsuario, stmt -> {
                stmt.setString(1, user.getDni());
                stmt.setString(2, user.getCorreo());
                stmt.setString(3, user.getContraseña());
                stmt.setString(4, user.getTelefono());
                stmt.setString(5, Usuario.TipoUsuario.CANDIDATO.name());
            });

            if (filas == 0) return false;

            String sqlPersonal = """
                INSERT INTO usuarios_personales (id, nombre, apellido, fecha_nacimiento, experiencia, habilidades, formacion)
                SELECT id, ?, ?, ?, ?, ?, ? FROM usuarios WHERE dni = ?
            """;

            int resultado = executeUpdate(sqlPersonal, stmt -> {
                stmt.setString(1, user.getNombre());
                stmt.setString(2, user.getApellido());
                stmt.setDate(3, java.sql.Date.valueOf(user.getFechaNacimiento()));
                stmt.setString(4, user.getExperiencia());
                stmt.setString(5, user.getHabilidades());
                stmt.setString(6, user.getFormacion());
                stmt.setString(7, user.getDni());
            });

            return resultado > 0;
        });
    }

    @Override
    public boolean update(UsuarioPersonal user) {
        return executeTransaction(() -> {
            String sql1 = "UPDATE usuarios SET correo = ?, contraseña = ?, telefono = ? WHERE dni = ?";
            executeUpdate(sql1, stmt -> {
                stmt.setString(1, user.getCorreo());
                stmt.setString(2, user.getContraseña());
                stmt.setString(3, user.getTelefono());
                stmt.setString(4, user.getDni());
            });

            String sql2 = """
                UPDATE usuarios_personales up
                JOIN usuarios u ON up.id = u.id
                SET nombre = ?, apellido = ?, fecha_nacimiento = ?, experiencia = ?, habilidades = ?, formacion = ?
                WHERE u.dni = ?
            """;

            int updated = executeUpdate(sql2, stmt -> {
                stmt.setString(1, user.getNombre());
                stmt.setString(2, user.getApellido());
                stmt.setDate(3, java.sql.Date.valueOf(user.getFechaNacimiento()));
                stmt.setString(4, user.getExperiencia());
                stmt.setString(5, user.getHabilidades());
                stmt.setString(6, user.getFormacion());
                stmt.setString(7, user.getDni());
            });

            return updated > 0;
        });
    }

    @Override
    public boolean deleteById(int id) {
        throw new UnsupportedOperationException("Usar deleteByDNI()");
    }

    public UsuarioPersonal searchByDni(String dni) {
        String sql = """
            SELECT u.dni, u.correo, u.contraseña, u.telefono,
                   up.nombre, up.apellido, up.fecha_nacimiento, up.experiencia, up.habilidades, up.formacion
            FROM usuarios u
            JOIN usuarios_personales up ON u.id = up.id
            WHERE u.dni = ?
        """;

        return executeQueryOne(sql, stmt -> stmt.setString(1, dni), this::mapToEntity);
    }

    public boolean existsByDni(String dni) {
        String sql = "SELECT EXISTS(SELECT 1 FROM usuarios WHERE dni = ? AND tipo = 'CANDIDATO')";
        Boolean result = executeQueryOne(sql, stmt -> stmt.setString(1, dni), rs -> rs.getBoolean(1));
        return result != null && result;
    }

    public boolean deleteByDni(String dni) {
        return executeUpdate("DELETE FROM usuarios WHERE dni = ?", stmt -> stmt.setString(1, dni)) > 0;
    }

    @Override
    public UsuarioPersonal searchById(int id) {
        throw new UnsupportedOperationException("Usar searchByDni()");
    }

    @Override
    public boolean existsById(int id) {
        throw new UnsupportedOperationException("Usar existsByDni()");
    }

    @Override
    public List<UsuarioPersonal> searchAll() {
        String sql = """
            SELECT u.dni, u.correo, u.contraseña, u.telefono,
                   up.nombre, up.apellido, up.fecha_nacimiento, up.experiencia, up.habilidades, up.formacion
            FROM usuarios u
            JOIN usuarios_personales up ON u.id = up.id
        """;

        return executeQueryList(sql, stmt -> {}, this::mapToEntity);
    }

    private UsuarioPersonal mapToEntity(ResultSet rs) throws SQLException {
        return new UsuarioPersonal.Builder()
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
    }
}
