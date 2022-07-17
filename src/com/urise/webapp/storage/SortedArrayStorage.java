package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void saveByIndex(Resume resume) {
        int searchIndex = Arrays.binarySearch(storage, 0, size, resume, Resume::compareTo);
        int indexInStorage = -1 - searchIndex;
        if (storage[indexInStorage] != null) {
            System.arraycopy(storage, indexInStorage, storage, indexInStorage + 1, size);
        }
        storage[indexInStorage] = resume;
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}