package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int findSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveByIndex(Resume resume) {
        int searchIndex = Arrays.binarySearch(storage, 0, size, resume, Resume::compareTo);
        int indexInStorage = -1 - searchIndex;
        if (storage[indexInStorage] != null) {
            System.arraycopy(storage, indexInStorage, storage, indexInStorage + 1, size-indexInStorage);
        }
        storage[indexInStorage] = resume;
    }

    @Override
    protected void deleteByIndex(int index) {
        size--;
        System.arraycopy(storage, index + 1, storage, index, size - index);
    }
}