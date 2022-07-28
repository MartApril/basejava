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
    protected void saveByIndex(Resume resume) {
        storage[size] = resume;
    }

    @Override
    protected void doDelete(String uuid) {
        int index = (int) findSearchKey(String.valueOf(uuid));
        size--;
        storage[index] = storage[size];
        storage[size] = null;
    }
}