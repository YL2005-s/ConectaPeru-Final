package model.repository.interfaces;

import java.sql.SQLException;

@FunctionalInterface
public interface SQLExecutor {
    boolean execute() throws SQLException;
}