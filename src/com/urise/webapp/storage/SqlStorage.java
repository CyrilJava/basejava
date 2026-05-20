package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.sql.SqlQueryExecutor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            sqlHelper.execute("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
            });
        }
    }

    @Override
    public Resume get(String uuid) {
        // return sqlHelper.executeQuery("SELECT * FROM resume WHERE uuid = ?", new SqlQueryExecutor<>() {
        return sqlHelper.executeQuery("SELECT * FROM resume\n" +
                "LEFT JOIN contact ON contact.resume_uuid = resume.uuid\n" +
                "WHERE resume.uuid =?", new SqlQueryExecutor<>() {
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
        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
            sqlHelper.execute("UPDATE contact SET value = ? WHERE resume_uuid = ? and type = ?", ps -> {
                ps.setString(1, e.getValue());
                ps.setString(2, r.getUuid());
                ps.setString(3, e.getKey().name());
            });
        }
        if (sqlResult == 0) {
            throw new NotExistStorageException(null);
        }
    }

    @Override
    public void delete(String uuid) {
        int sqlResult = sqlHelper.execute("DELETE FROM resume where uuid = ?;\n" +
                        "delete from contact where resume_uuid = ?", ps -> {
                    ps.setString(1, uuid);
                    ps.setString(2, uuid);
                }
        );
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
        sqlHelper.execute("DELETE FROM resume; delete from contact", PreparedStatement::execute);
    }
}
