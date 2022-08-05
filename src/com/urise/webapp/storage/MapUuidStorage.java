package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Object object) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(object.toString())) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(Object object, Resume resume) {
        storage.replace((String) object, resume);
    }

    @Override
    protected void doSave(Object object, Resume resume) {
        storage.put((String) object, resume);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return storage.get((String) uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        storage.remove((String) uuid);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>(storage.values());
        resumes.sort(RESUME_COMPARATOR_FULLNAME.thenComparing(RESUME_COMPARATOR_UUID));
        return resumes;
    }
}
