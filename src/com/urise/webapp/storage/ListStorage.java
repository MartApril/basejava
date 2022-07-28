package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    protected int findSearchKey(String uuid) {
        for (Resume r : storage) {
            if (r.getUuid().equals(uuid)) {
                return storage.indexOf(r);
            }
        }
        return -1;
    }

    @Override
    protected void updateByRealization(Resume resume, int index) {
        storage.remove(index);
        storage.add(index, resume);
    }

    @Override
    protected void saveByRealization(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume getByRealization(int index) {
        return storage.get(index);
    }

    @Override
    protected void deleteByIndex(int index) {
        storage.remove(index);
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
    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }
}
