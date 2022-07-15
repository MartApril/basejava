package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage overflow.");
        } else if (findIndex(resume.getUuid()) > -1) {
            System.out.println("Resume " + resume.getUuid() + " already exists.");
        } else {
            storage[size] = resume;
            size++;
            System.out.println("Resume " + resume.getUuid() + " was saved.");
        }
    }

    protected int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
