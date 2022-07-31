package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void saveByIndex(Object object, Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void doDelete(Object uuid) {
        size--;
        storage[(int) uuid] = storage[size];
        storage[size] = null;
    }
}