package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    // insert/update/delete
    protected int execute(String sql, SqlExecutor<Void> executor) {
        try (Connection conn = connectionFactory.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            executor.accept(ps);
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new ExistStorageException(null); // работает для save
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

    @Override // работает
    public void save(Resume r) {
        execute("INSERT INTO resume VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
        });
    }

    @Override // работает
    public Resume get(String uuid) {
        return executeQuery("SELECT * FROM resume WHERE uuid = ?", new SqlQueryExecutor<>() {
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

    @Override // работает
    public void update(Resume r) {
        int sqlResult = execute("UPDATE resume \n SET full_name = ? where uuid = ?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
        });
        if (sqlResult == 0) {
            throw new NotExistStorageException(null);
        }
    }

    @Override // работает
    public void delete(String uuid) {
        int sqlResult = execute("DELETE FROM resume \n where uuid = ?",
                ps -> ps.setString(1, uuid));
        if (sqlResult == 0) {
            throw new NotExistStorageException(null);
        }
    }

    @Override // работает
    public int size() {
        return executeQuery("SELECT count(*) FROM resume\n", new SqlQueryExecutor<>() {
            @Override
            public ResultSet execute(PreparedStatement ps) throws SQLException {
                return ps.executeQuery();
            }

            @Override
            public Integer map(ResultSet rs) throws SQLException {
                if (!rs.next()) throw new NotExistStorageException(null);
                return rs.getInt(1);
            }
        });
    }

    @Override // работает
    public List<Resume> getAllSorted() {
        return executeQuery("SELECT * FROM resume \n order by full_name asc", new SqlQueryExecutor<>() {
            @Override
            public ResultSet execute(PreparedStatement ps) throws SQLException {
                return ps.executeQuery();
            }

            @Override
            public List<Resume> map(ResultSet rs) throws SQLException {
                List<com.urise.webapp.model.Resume> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(new com.urise.webapp.model.Resume(rs.getString("uuid").trim(),
                            rs.getString("full_name")));
                }
                return result;
            }
        });
    }

    @Override // работает
    public void clear() {
        execute("DELETE FROM resume", PreparedStatement::execute);
    }
}
