package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // insert/update/delete
    public int execute(String sql, SqlExecutor<Void> executor) {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.execute(ps);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExistStorageException(null); // работает для save
        }
    }

    // select
    public <T> T executeQuery(String sql, SqlQueryExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = executor.execute(ps);
            return executor.map(rs);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public <T> T transactionalExecute(String sql, SqlTransaction<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            try {
                conn.setAutoCommit(false);
                T res = executor.execute(ps);
                conn.commit();
                return res;
            } catch (SQLException e) {
                conn.rollback();
                throw ExceptionUtil.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}