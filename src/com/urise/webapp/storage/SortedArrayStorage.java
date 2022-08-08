package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Object findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, UUID.randomUUID().toString());
        return Arrays.binarySearch(storage, 0, size, searchKey, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void saveByIndex(Object object, Resume resume) {
        int indexInStorage = -1 - (int) object;
        if (storage[indexInStorage] != null) {
            System.arraycopy(storage, indexInStorage, storage, indexInStorage + 1, size - indexInStorage);
        }
        storage[indexInStorage] = resume;
    }

    @Override
    protected void doDelete(Object uuid) {
        int index = (int) uuid;
        size--;
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }
}