package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private final Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int findIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Resume " + resume.getUuid() + " was not found.");
        } else {
            storage[index] = resume;
            System.out.println("Resume " + resume.getUuid() + " was updated.");
        }
    }

    public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (size == storage.length) {
            System.out.println("Storage overflow.");
        } else if (index > -1) {
            System.out.println("Resume " + resume.getUuid() + " already exists.");
        } else {
            storage[size] = resume;
            size++;
            System.out.println("Resume " + resume.getUuid() + " was saved.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " was not found.");
            return null;
        } else {
            return storage[index];
        }
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " was not found.");
        } else {
            if (size > 1) {
                storage[index] = storage[size - 1];
            }
            storage[size - 1] = null;
//          System.arraycopy(storage, index + 1, storage, index, size - index - 1);
            size--;
            System.out.println("Resume " + uuid + " was deleted.");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }
}
