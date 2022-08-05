package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage {
    protected final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isExist(Object object) {
        if (object != null) {
            for (Map.Entry<String, Resume> entry : storage.entrySet()) {
                if (entry.getKey().equals(object.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Object object, Resume resume) {
        storage.replace(object.toString(), resume);
    }

    @Override
    protected void doSave(Object object, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Object object) {
        return (Resume) object;
    }

    @Override
    protected void doDelete(Object object) {
        storage.remove(object.toString());
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
