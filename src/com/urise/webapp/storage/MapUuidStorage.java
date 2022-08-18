package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUuidStorage extends AbstractStorage<String> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(String searchKey) {
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getKey().equals(searchKey)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected void doUpdate(String searchKey, Resume resume) {
        storage.replace(searchKey, resume);
    }

    @Override
    protected void doSave(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(String uuid) {
        storage.remove(uuid);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage.values());
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
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }
}
