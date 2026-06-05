package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import com.urise.webapp.sql.SqlHelper;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try { // это необходимо для работы драйвера postgresql с Tomcat
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    insertContact(r, conn);
                    insertSection(r, conn);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "SELECT r.uuid, r.full_name, c.type, c.value as contact_value" +
                        ", s.section_type, s.value as section_value \n" +
                        "  FROM resume r \n" +
                        "  LEFT JOIN contact c  ON c.resume_uuid = r.uuid \n" +
                        "  LEFT JOIN section s  ON s.resume_uuid = r.uuid \n" +
                        " WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    do {
                        addContact(rs, r);
                        addSection(rs, r);
                    } while (rs.next());
                    return r;
                });
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement(
                            "UPDATE resume \n " +
                                    "SET full_name = ? \n" +
                                    "where uuid = ?")) {
                        ps.setString(1, r.getFullName());
                        ps.setString(2, r.getUuid());
                        // ps.execute();
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    try (PreparedStatement ps = conn.prepareStatement(
                            "DELETE FROM contact \n " +
                                    "where resume_uuid = ?")) {
                        ps.setString(1, r.getUuid());
                        ps.execute();
                    }
                    insertContact(r, conn);
                    try (PreparedStatement ps = conn.prepareStatement(
                            "DELETE FROM section \n" +
                                    "where resume_uuid = ?")) {
                        ps.setString(1, r.getUuid());
                        ps.execute();
                    }
                    insertSection(r, conn);
                    return null;
                }
        );
    }

    @Override
    public void delete(String uuid) { // другие таблицы не указываем, работает каскадное удаление
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

    /* @Override
    public List<Resume> getAllSorted() { // через join
        return sqlHelper.execute(
                "SELECT r.uuid, r.full_name, c.type, c.value as contact_value" +
                        ", s.section_type, s.value as section_value \n" +
                        "     from resume r \n" +
                        "left join contact c on c.resume_uuid = r.uuid \n" +
                        "left join section s on s.resume_uuid = r.uuid \n" +
                        " order by r.full_name asc", ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> resumes = new LinkedHashMap<>();
                    while (rs.next()) {
                        String cursorId = rs.getString("uuid").trim();
                        Resume r = resumes.get(cursorId);
                        if (r == null) {
                            r = new Resume(cursorId, rs.getString("full_name"));
                            resumes.put(cursorId, r);
                        }
                        addContact(rs, r);
                        addSection(rs, r);
                    }
                    return new ArrayList<>(resumes.values());
                }
        );
    }*/

    @Override // все таблицы отдельно
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = getAllResumes();
        if (resumes.isEmpty()) {
            return new ArrayList<>();
        }
        getAllContacts(resumes);
        getAllSections(resumes);
        return resumes.values().stream().sorted(Comparator.comparing(Resume::getFullName)
                .thenComparing(Resume::getUuid)).collect(Collectors.toList());
    }

    // Получение всех резюме
    private Map<String, Resume> getAllResumes() {
        return sqlHelper.execute(
                "SELECT uuid, full_name FROM resume ORDER BY full_name ASC",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    Map<String, Resume> resumes = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid").trim();
                        String fullName = rs.getString("full_name");
                        resumes.put(uuid, new Resume(uuid, fullName));
                    }
                    return resumes;
                }
        );
    }

    // Загрузка всех контактов
    private void getAllContacts(Map<String, Resume> resumes) {
        if (resumes.isEmpty()) {
            return;
        }
        sqlHelper.execute("SELECT resume_uuid, type, value FROM contact ORDER BY resume_uuid, type ASC",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("resume_uuid").trim();
                        Resume r = resumes.get(uuid);
                        if (r != null) {
                            String typeStr = rs.getString("type");
                            String value = rs.getString("value");
                            if (typeStr != null && value != null) {
                                ContactType type = ContactType.valueOf(typeStr);
                                r.addContact(type, value);
                            }
                        }
                    }
                    return null; // Void
                }
        );
    }

    private void getAllSections(Map<String, Resume> resumes) {
        if (resumes.isEmpty()) {
            return;
        }
        sqlHelper.execute(
                "SELECT resume_uuid, section_type, value as section_value \n" +
                        "FROM section ORDER BY resume_uuid, section_type",
                ps -> {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        String uuid = rs.getString("resume_uuid").trim();
                        Resume r = resumes.get(uuid);
                        if (r != null) {
                            String sectionType = rs.getString("section_type");
                            String value = rs.getString("section_value");
                            if (sectionType != null && value != null) {
                                addSection(rs, r);
                            }
                        }
                    }
                    return null;
                }
        );
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    private void addContact(ResultSet rs, Resume r) throws SQLException {
        String value = rs.getString("contact_value");
        if (value != null) {
            r.addContact(ContactType.valueOf(rs.getString("type")), value);
        }
    }

    private void addSection(ResultSet rs, Resume r) throws SQLException {
        SectionType sectionType = SectionType.valueOf(rs.getString("section_type"));
        String value = rs.getString("section_value");
        if (value != null) {
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE: {
                    r.addSection(sectionType, new TextSection(value));
                    break;
                }
                case ACHIEVEMENT:
                case QUALIFICATIONS: {
                    ListSection listSection =
                            new ListSection(new ArrayList<>(Arrays.asList(value.split("\n"))));
                    r.addSection(SectionType.valueOf(rs.getString("section_type")), listSection);
                    break;
                }
                default:
                    break;
            }
        }
    }

    private void insertContact(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Resume r, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO section (resume_uuid, section_type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
                ps.setString(1, r.getUuid());
                ps.setString(2, entry.getKey().name());
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        ps.setString(3, entry.getValue().toString());
                        ps.addBatch();
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        StringBuilder resultString = new StringBuilder();
                        ListSection listSection = (ListSection) entry.getValue();
                        for (String item : listSection.getTextList()) {
                            resultString.append(item).append("\n");
                        }
                        ps.setString(3, resultString.toString());
                        ps.addBatch();
                        break;
                    }
                    default:
                        break;
                }
            }
            ps.executeBatch();
        }
    }
}
