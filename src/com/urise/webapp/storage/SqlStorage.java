package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET uuid =?, full_name=? WHERE uuid=?")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.setString(3, resume.getUuid());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(resume.getUuid());
                        }
                    }
                    deleteContactsOrSections(conn, resume, "DELETE FROM contact WHERE resume_uuid=?");
                    addContacts(conn, resume);
                    deleteContactsOrSections(conn, resume, "DELETE FROM section WHERE resume_uuid=?");
                    addSections(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, resume.getUuid());
                        ps.setString(2, resume.getFullName());
                        ps.execute();
                    }
                    addContacts(conn, resume);
                    addSections(conn, resume);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT uuid, full_name, " +
                        "           c.type as contact_type, c.value as contact_value, " +
                        "           s.type as section_type, s.value as section_value " +
                        " FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid" +
                        " LEFT JOIN section s " +
                        "        ON r.uuid = s.resume_uuid" +
                        "     WHERE r.uuid =? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        getContacts(rs, resume);
                        getSections(rs, resume);
                    } while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        String sqlQuery = "DELETE FROM resume WHERE uuid = ?";
        sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT uuid, full_name, " +
                    "           c.type as contact_type, c.value as contact_value, " +
                    "           s.type as section_type, s.value as section_value " +
                    " FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid" +
                    " LEFT JOIN section s " +
                    "        ON r.uuid = s.resume_uuid" +
                    "     ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                Map<String, Resume> mapOfResumes = new LinkedHashMap<>();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = mapOfResumes.get(uuid);
                    if (resume == null) {
                        resume = new Resume(uuid, rs.getString("full_name"));
                        mapOfResumes.put(uuid, resume);
                    }
                    getContacts(rs, resume);
                    getSections(rs, resume);
                }
                return new ArrayList<>(mapOfResumes.values());
            }
        });
    }

    @Override
    public int size() {
        String sqlQuery = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(sqlQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addSections(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> mapEntry : resume.getSections().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, mapEntry.getKey().name());
                AbstractSection abstractSection = mapEntry.getValue();
                ps.setString(3, abstractSection.getContentAsString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContactsOrSections(Connection conn, Resume resume, String sqlQuery) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }

    private void addContacts(Connection conn, Resume resume) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(3, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void getContacts(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("contact_value");
        if (value != null) {
            ContactType type = ContactType.valueOf((rs.getString("contact_type")));
            resume.addContact(type, value);
        }
    }

    private void getSections(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("section_value");
        if (value != null) {
            SectionType type = SectionType.valueOf(rs.getString("section_type"));
            if (type == SectionType.OBJECTIVE || type == SectionType.PERSONAL) {
                resume.addSection(type, new TextSection(value));
            } else if (type == SectionType.ACHIEVEMENT || type == SectionType.QUALIFICATIONS) {
                List<String> list = Stream.of(value.split("\n")).collect(Collectors.toList());
                resume.addSection(type, new ListSection(list));
            }
        }
    }
}