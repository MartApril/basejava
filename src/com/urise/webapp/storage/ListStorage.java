package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer>{
    protected final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey != null;
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage.remove(resume);
        storage.add(searchKey, resume);
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Integer uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(Integer uuid) {
        Resume resume = storage.get(uuid);
        storage.remove(resume);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return new ArrayList<>(storage);
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
