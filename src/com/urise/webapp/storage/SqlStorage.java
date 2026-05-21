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
                Resume r = new Resume(uuid, rs.getString("full_name"));
                do {
                    String value = rs.getString("value");
                    ContactType type = ContactType.valueOf(rs.getString("type"));
                    r.addContact(type, value);
                } while (rs.next());
                return r;
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
        int sqlResult = sqlHelper.execute("DELETE FROM resume where uuid = ?;\n",
                ps -> ps.setString(1, uuid)
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
        return sqlHelper.executeQuery("" +
                        "select r.uuid, r.full_name, p.value as PHONE, e.value as EMAIL," +
                        "       l.value as LINKEDIN, w.value as WEBSITE, g.value as GITHUB \n" +
                        "  from resume r \n" +
                        "  join public.contact p on r.uuid = p.resume_uuid and p.type = 'PHONE' \n" +
                        "  join public.contact e on r.uuid = e.resume_uuid and e.type = 'EMAIL' \n" +
                        "  join public.contact l on r.uuid = l.resume_uuid and l.type = 'LINKEDIN' \n" +
                        "  join public.contact w on r.uuid = w.resume_uuid and w.type = 'WEBSITE' \n" +
                        "  join public.contact g on r.uuid = g.resume_uuid and g.type = 'GITHUB'" +
                        "order by r.full_name asc",
                new SqlQueryExecutor<>() {
                    @Override
                    public ResultSet execute(PreparedStatement ps) throws SQLException {
                        return ps.executeQuery();
                    }

                    @Override
                    public List<Resume> map(ResultSet rs) throws SQLException {
                        List<Resume> result = new ArrayList<>();
                        while (rs.next()) {
                            Resume r = new Resume(rs.getString("uuid").trim(),
                                    rs.getString("full_name"));
                            for (ContactType ct : ContactType.values()) {
                                r.addContact(ct, rs.getString(ct.toString()));
                            }
                            result.add(r);
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
