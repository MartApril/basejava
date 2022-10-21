package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.SqlHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        String sqlQuery = "DELETE FROM resume";
        sqlHelper.execute(sqlQuery, ps -> {
            ps.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        String sqlQuery = "UPDATE resume SET uuid =?, full_name=? WHERE uuid=?";
        sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.setString(3, resume.getUuid());
            int count = ps.executeUpdate();
            if (count==0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        String sqlQuery = "INSERT INTO resume(uuid, full_name) VALUES(?,?)";
        sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        String sqlQuery = "SELECT * FROM resume WHERE uuid =?";
        return sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        String sqlQuery = "DELETE FROM resume WHERE uuid = ?";
        sqlHelper.execute(sqlQuery, ps -> {
            ps.setString(1, uuid);
            int count = ps.executeUpdate();
            if (count==0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String sqlQuery = "SELECT * FROM resume ORDER BY full_name";
        return sqlHelper.execute(sqlQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(rs.getObject(1, Resume.class));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        String sqlQuery = "SELECT COUNT(*) FROM resume";
        return sqlHelper.execute(sqlQuery, ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.getInt(1);
        });
    }
}
