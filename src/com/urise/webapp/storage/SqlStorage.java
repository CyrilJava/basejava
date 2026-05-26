package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, r.getUuid());
                            ps.setString(2, e.getKey().name());
                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("    SELECT * FROM resume r " +
                        " LEFT JOIN contact c  ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        String value = rs.getString("value");
                        if (!value.isEmpty()) {
                            r.addContact(ContactType.valueOf(rs.getString("type")), value);
                        }
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "UPDATE resume \n " +
                            "SET full_name = ? \n" +
                            "where uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        ps.execute();
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact \n" +
                            "where resume_uuid = ?")) {
                        ps.setString(1, r.getUuid());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("" +
                            "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                            ps.setString(1, r.getUuid());
                            ps.setString(2, e.getKey().name());
                            ps.setString(3, e.getValue());
                            ps.addBatch();
                        }
                        ps.executeBatch();
                    }
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume where uuid = ?;\n",
                ps -> {
                    ps.setString(1, uuid);
                    if (ps.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) FROM resume\n", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute("" +
                        "   select r.uuid, r.full_name, c.type, c.value \n" +
                        "     from resume r \n" +
                        "left join contact c on c.resume_uuid = r.uuid \n" +
                        " order by r.full_name asc", ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> resumes = new HashMap<>();
                    while (rs.next()) {
                        String cursorId = rs.getString("uuid").trim();
                        Resume r;
                        if (resumes.get(cursorId) == null) {
                            r = new Resume(cursorId, rs.getString("full_name"));
                        } else {
                            r = resumes.get(cursorId);
                        }
                        r.addContact(ContactType.valueOf(rs.getString("type")),
                                rs.getString("value"));
                        resumes.put(cursorId, r);
                    }
                    List<Resume> result = new ArrayList<>(resumes.values());
                    result.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
                    return result;
                }
        );
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }
}
