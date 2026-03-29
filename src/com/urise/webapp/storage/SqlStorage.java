package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // insert/update/delete
    protected void execute(String sql, SqlExecutor<Void> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.accept(ps);
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    // select
    protected <T> T executeQuery(String sql, SqlQueryExecutor<T> executor) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = executor.execute(ps);
            return executor.map(rs);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @FunctionalInterface
    protected interface SqlExecutor<T> {
        void accept(PreparedStatement ps) throws SQLException;
    }

    @FunctionalInterface
    protected interface SqlQueryExecutor<T> {
        ResultSet execute(PreparedStatement ps) throws SQLException;

        default T map(ResultSet rs) throws SQLException {
            return null;
        }
    }

    /*@Override
    public void save(Resume r) {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("INSERT INTO resume VALUES (?,?)")) {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r\n where r.uuid = ?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) throw new NotExistStorageException(uuid);
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }*/
    // Упрощенные методы
    @Override
    public void save(Resume r) {
        execute("INSERT INTO resume VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
        });
    }

    @Override
    public Resume get(String uuid) {
        return executeQuery("SELECT * FROM resume r WHERE r.uuid = ?", new SqlQueryExecutor<Resume>() {
            @Override
            public ResultSet execute(PreparedStatement ps) throws SQLException {
                ps.setString(1, uuid);
                return ps.executeQuery();
            }

            @Override
            public Resume map(ResultSet rs) throws SQLException {
                if (!rs.next()) throw new NotExistStorageException(uuid);
                return new Resume(uuid, rs.getString("full_name"));
            }
        });
    }

    @Override
    public void update(Resume r) {
        execute("UPDATE resume r\n  SET r.full_name =? where r.uuid=?", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
        });
    }

    @Override
    public void delete(String uuid) {
        execute("DELETE FROM resume r\n where r.uuid=?", ps -> {
            ps.setString(1, uuid);
        });
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public List<Resume> getAllSorted() {
        return List.of();
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
