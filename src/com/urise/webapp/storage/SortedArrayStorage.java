package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage overflow.");
        } else if (Arrays.binarySearch(storage, 0, size, resume, Resume::compareTo) > -1) {
            System.out.println("Resume " + resume.getUuid() + " already exists.");
        } else if (size == 0) {
            storage[size] = resume;
        } else {
            storage[size] = storage[size - 1];
            storage[size - 1] = resume;
        }
        size++;
        System.out.println("Resume " + resume.getUuid() + " was saved.");
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
