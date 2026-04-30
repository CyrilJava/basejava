package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlQueryExecutor<T> {
    ResultSet execute(PreparedStatement ps) throws SQLException;

    default T map(ResultSet rs) throws SQLException {
        return null;
    }
}