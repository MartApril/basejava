package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Object object) {
        return storage.containsKey(object.toString());
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
        return storage.get((String)uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        storage.remove((String)uuid);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
