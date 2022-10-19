package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.util.ABlockOfCode;
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
        String string = "DELETE FROM resume";
        sqlHelper.execute((ABlockOfCode<Resume>) ps -> {
            ps.execute();
            return null;
        }, string);
    }

    @Override
    public void update(Resume resume) {
        String string = "UPDATE resume SET uuid =?, full_name=? WHERE uuid=?";

        sqlHelper.execute((ABlockOfCode<Resume>) ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.setString(3, resume.getUuid());
            ps.execute();
            return null;
        }, string);
    }

    @Override
    public void save(Resume resume) {
        String string = "INSERT INTO resume(uuid, full_name) VALUES(?,?)";

        sqlHelper.execute((ABlockOfCode<Resume>) ps -> {
            ps.setString(1, resume.getUuid());
            ps.setString(2, resume.getFullName());
            ps.execute();
            return null;
        }, string);
    }

    @Override
    public Resume get(String uuid) {
        String string = "SELECT * FROM resume WHERE uuid =?";

        return sqlHelper.execute(ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        }, string);
    }

    @Override
    public void delete(String uuid) {
        String string = "DELETE FROM resume WHERE uuid = ?";

        sqlHelper.execute((ABlockOfCode<Resume>) ps -> {
            ps.setString(1, uuid);
            ps.execute();
            return null;
        }, string);
    }

    @Override
    public List<Resume> getAllSorted() {
        String string = "SELECT * FROM resume ORDER BY full_name";

        return sqlHelper.execute(ps -> {
            ResultSet rs = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (rs.next()) {
                resumes.add(rs.getObject(1, Resume.class));
            }
            return resumes;
        }, string);
    }

    @Override
    public int size() {
        String string = "SELECT COUNT(*) FROM resume";

        return sqlHelper.execute(ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.getInt(1);
        }, string);
    }


//    @Override
//    public void clear() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
//
//    @Override
//    public void update(Resume resume) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET uuid =?, full_name=? WHERE uuid=?")) {
//            ps.setString(1, resume.getUuid());
//            ps.setString(2, resume.getFullName());
//            ps.setString(3, resume.getUuid());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
//
//    @Override
//    public void save(Resume resume) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES(?,?)")) {
//            ps.setString(1, resume.getUuid());
//            ps.setString(2, resume.getFullName());
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
//
//    @Override
//    public Resume get(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume WHERE uuid =?")) {
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(uuid);
//            }
//            return new Resume(uuid, rs.getString("full_name"));
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
//
//    @Override
//    public void delete(String uuid) {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
//            ps.setString(1, uuid);
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
//
//    @Override
//    public List<Resume> getAllSorted() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume ORDER BY full_name")) {
//            ResultSet rs = ps.executeQuery();
//            List<Resume> resumes = new ArrayList<>();
//            while (rs.next()) {
//                resumes.add(rs.getObject(1, Resume.class));
//            }
//            return resumes;
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
//
//    @Override
//    public int size() {
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM resume")) {
//            ResultSet rs = ps.executeQuery();
//            return rs.getInt(1);
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }
}
