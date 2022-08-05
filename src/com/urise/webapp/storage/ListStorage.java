package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(Object object) {
        return object != null;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Object object, Resume resume) {
        storage.remove(resume);
        storage.add((int) object, resume);
    }

    @Override
    protected void doSave(Object object, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object uuid) {
        return storage.get((Integer) uuid);
    }

    @Override
    protected void doDelete(Object uuid) {
        Resume resume = storage.get((Integer) uuid);
        storage.remove(resume);
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
        List<Resume> resumes = new ArrayList<>(storage);
        resumes.sort(RESUME_COMPARATOR_FULLNAME.thenComparing(RESUME_COMPARATOR_UUID));
        return resumes;
    }
}
