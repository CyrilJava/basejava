package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.sql.SqlQueryExecutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void save(Resume r) {
        sqlHelper.execute("INSERT INTO resume VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.executeQuery("SELECT * FROM resume WHERE uuid = ?", new SqlQueryExecutor<>() {
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
        int sqlResult = sqlHelper.execute("UPDATE resume \n SET full_name = ? where uuid = ?",
                ps -> {
                ps.setString(1, r.getFullName());
                ps.setString(2, r.getUuid());
            });
        if (sqlResult == 0) {
            throw new NotExistStorageException(null);
        }
    }

    @Override
    public void delete(String uuid) {
        int sqlResult = sqlHelper.execute("DELETE FROM resume \n where uuid = ?",
                ps -> ps.setString(1, uuid));
        if (sqlResult == 0) {
            throw new NotExistStorageException(null);
        }
    }

    @Override
    public int size() {
        return sqlHelper.executeQuery("SELECT count(*) FROM resume\n", new SqlQueryExecutor<>() {
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

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.executeQuery("SELECT * FROM resume \n order by full_name asc",
                new SqlQueryExecutor<>() {
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

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }
}
