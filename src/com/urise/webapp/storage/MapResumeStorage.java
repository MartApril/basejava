package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        storage.put(searchKey.getUuid(), resume);
    }

    @Override
    protected void doSave(Resume searchKey, Resume resume) {
        storage.put(resume.getUuid(),resume );
    }

    @Override
    protected Resume doGet(Resume resume) {
        return resume;
    }

    @Override
    protected void doDelete(Resume resume) {
        storage.remove(resume.getUuid());
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
}
