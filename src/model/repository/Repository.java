package model.repository;

import config.ConexionDB;
import model.repository.interfaces.ResultSetMapper;
import model.repository.interfaces.SQLExecutor;
import model.repository.interfaces.StatementMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class Repository<T> {
    protected static final Connection conexion;

    static {
        try {
            conexion = ConexionDB.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract boolean create(T entity);
    public abstract boolean update(T entity);
    public abstract boolean deleteById(int id);
    public abstract T searchById(int id);
    public abstract List<T> searchAll();
    public abstract boolean existsById(int id);

    protected int executeUpdate(String sql, StatementMapper mapper) {
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            mapper.map(ps);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected int executeUpdateWithKeys(String sql, StatementMapper mapper, ResultSetMapper<Integer> keyConsumer) {
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            mapper.map(ps);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        keyConsumer.map(keys);
                    }
                }
            }
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected <R> R executeQueryOne(String sql, StatementMapper mapper, ResultSetMapper<R> resultMapper) {
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            mapper.map(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? resultMapper.map(rs) : null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected <R> List<R> executeQueryList(String sql, StatementMapper mapper, ResultSetMapper<R> resultMapper) {
        List<R> resultados = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            mapper.map(ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultados.add(resultMapper.map(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultados;
    }

    protected boolean executeTransaction(SQLExecutor executor) {
        try {
            conexion.setAutoCommit(false);
            boolean ok = executor.execute();
            conexion.commit();
            return ok;
        } catch (SQLException e) {
            try {
                conexion.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException(e);
        } finally {
            try {
                conexion.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
