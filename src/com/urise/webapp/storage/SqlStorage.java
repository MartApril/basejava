package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
                    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid=?")) {
                        ps.setString(1, resume.getUuid());
                        ps.execute();
                    }
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        addContacts(resume, ps);
                    }
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
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
                        addContacts(resume, ps);
                    }
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
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
            List<Resume> resumes = new ArrayList<>();
            try (PreparedStatement ps = conn.prepareStatement("" +
                    "    SELECT * FROM resume r " +
                    " LEFT JOIN contact c " +
                    "        ON r.uuid = c.resume_uuid " +
                    "     ORDER BY full_name, uuid")) {
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    throw new StorageException("Resume is empty");
                }
                while (rs.next()) {
                    Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                    resumes.add(resume);
                    getContacts(rs, resume);
                }
                return resumes;
            }
        });
//        return sqlHelper.transactionalExecute(conn -> {
//                    List<Resume> resumes = new ArrayList<>();
//                    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
//                        ResultSet rs = ps.executeQuery();
//                        while (rs.next()) {
//                            resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
//                        }
//                    }
//                    for (Resume resume : resumes) {
//                        try (PreparedStatement ps = conn.prepareStatement("SELECT * From contact WHERE resume_uuid=?")) {
//                            ps.setString(1, resume.getUuid());
//                            ResultSet rs = ps.executeQuery();
//                            while (rs.next()) {
//                                String value = rs.getString("value");
//                                ContactType type = ContactType.valueOf(rs.getString("type"));
//                                resume.addContact(type, value);
//                            }
//                        }
//                    }
//                    return resumes;
//                });
    }

    @Override
    public int size() {
        String sqlQuery = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(sqlQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void addContacts(Resume resume, PreparedStatement ps) throws SQLException {
        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            ps.setString(1, resume.getUuid());
            ps.setString(2, e.getKey().name());
            ps.setString(3, e.getValue());
            ps.addBatch();
        }
        ps.executeBatch();
    }

    private void getContacts(ResultSet rs, Resume resume) throws SQLException {
        String value = rs.getString("value");
        if (value != null) {
            ContactType type = ContactType.valueOf((rs.getString("type")));
            resume.addContact(type, value);
        } else {
            System.out.println("Resume " + resume.getFullName() + " doesn't have any contacts");
        }
    }
}