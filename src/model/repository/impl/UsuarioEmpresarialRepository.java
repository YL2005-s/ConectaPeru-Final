package model.repository.impl;


import entities.Usuario;
import entities.UsuarioEmpresarial;
import model.repository.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UsuarioEmpresarialRepository extends Repository<UsuarioEmpresarial> {

    @Override
    public boolean create(UsuarioEmpresarial user) {
        return executeTransaction(() -> {
            String sqlUsuario = "INSERT INTO usuarios (dni, correo, contraseña, telefono, tipo) VALUES (?, ?, ?, ?, ?)";
            int filas = executeUpdate(sqlUsuario, stmt -> {
                stmt.setString(1, user.getDni());
                stmt.setString(2, user.getCorreo());
                stmt.setString(3, user.getContraseña());
                stmt.setString(4, user.getTelefono());
                stmt.setString(5, Usuario.TipoUsuario.EMPRESA.name());
            });

            if (filas == 0) return false;

            String sqlEmpresa = "INSERT INTO usuarios_empresariales (id, nombre_empresa, descripcion, ubicacion) " +
                    "SELECT id, ?, ?, ? FROM usuarios WHERE dni = ?";
            int resultado = executeUpdate(sqlEmpresa, stmt -> {
                stmt.setString(1, user.getNombreEmpresa());
                stmt.setString(2, user.getDescripcion());
                stmt.setString(3, user.getUbicacion());
                stmt.setString(4, user.getDni());
            });

            return resultado > 0;
        });
    }

    @Override
    public boolean update(UsuarioEmpresarial user) {
        return executeTransaction(() -> {
            String sql1 = "UPDATE usuarios SET correo = ?, contraseña = ?, telefono = ? WHERE dni = ?";
            executeUpdate(sql1, stmt -> {
                stmt.setString(1, user.getCorreo());
                stmt.setString(2, user.getContraseña());
                stmt.setString(3, user.getTelefono());
                stmt.setString(4, user.getDni());
            });

            String sql2 = """
                UPDATE usuarios_empresariales ue
                JOIN usuarios u ON ue.id = u.id
                SET nombre_empresa = ?, descripcion = ?, ubicacion = ?
                WHERE u.dni = ?
            """;
            int updated = executeUpdate(sql2, stmt -> {
                stmt.setString(1, user.getNombreEmpresa());
                stmt.setString(2, user.getDescripcion());
                stmt.setString(3, user.getUbicacion());
                stmt.setString(4, user.getDni());
            });

            return updated > 0;
        });
    }

    @Override
    public boolean deleteById(int id) {
        throw new UnsupportedOperationException("deleteById no se usa. Utiliza deleteByDni()");
    }

    public UsuarioEmpresarial searchByDni(String dni) {
        String sql = """
            SELECT u.id, u.dni, u.correo, u.contraseña, u.telefono,
                   ue.nombre_empresa, ue.descripcion, ue.ubicacion
            FROM usuarios u
            JOIN usuarios_empresariales ue ON u.id = ue.id
            WHERE u.dni = ?
        """;

        return executeQueryOne(sql, stmt -> stmt.setString(1, dni), this::mapToEntity);
    }

    public boolean existsByDni(String dni) {
        String sql = "SELECT EXISTS(SELECT 1 FROM usuarios WHERE dni = ? AND tipo = 'EMPRESA')";
        Boolean result = executeQueryOne(sql, stmt -> stmt.setString(1, dni), rs -> rs.getBoolean(1));
        return result != null && result;
    }

    public boolean deleteByDni(String dni) {
        return executeUpdate("DELETE FROM usuarios WHERE dni = ?", stmt -> stmt.setString(1, dni)) > 0;
    }

    @Override
    public UsuarioEmpresarial searchById(int id) {
        String sql = """
            SELECT u.id, u.dni, u.correo, u.contraseña, u.telefono,
                   ue.nombre_empresa, ue.descripcion, ue.ubicacion
            FROM usuarios u
            JOIN usuarios_empresariales ue ON u.id = ue.id
            WHERE u.id = ?
        """;

        return executeQueryOne(sql, stmt -> stmt.setInt(1, id), this::mapToEntity);
    }

    @Override
    public List<UsuarioEmpresarial> searchAll() {
        String sql = """
            SELECT u.id, u.dni, u.correo, u.contraseña, u.telefono,
                   ue.nombre_empresa, ue.descripcion, ue.ubicacion
            FROM usuarios u
            JOIN usuarios_empresariales ue ON u.id = ue.id
        """;

        return executeQueryList(sql, stmt -> {}, this::mapToEntity);
    }

    @Override
    public boolean existsById(int id) {
        throw new UnsupportedOperationException("Usar existsByDni()");
    }

    private UsuarioEmpresarial mapToEntity(ResultSet rs) throws SQLException {
        return new UsuarioEmpresarial.Builder()
                .dni(rs.getString("dni"))
                .correo(rs.getString("correo"))
                .contraseña(rs.getString("contraseña"))
                .telefono(rs.getString("telefono"))
                .nombreEmpresa(rs.getString("nombre_empresa"))
                .descripcion(rs.getString("descripcion"))
                .ubicacion(rs.getString("ubicacion"))
                .build();
    }
}
