package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveByIndex(Integer Integer, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void doDelete(Integer uuid) {
        size--;
        storage[(int) uuid] = storage[size];
        storage[size] = null;
    }
}