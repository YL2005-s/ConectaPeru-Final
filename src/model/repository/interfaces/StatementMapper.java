package model.repository.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementMapper {
    void map(PreparedStatement stmt) throws SQLException;
}