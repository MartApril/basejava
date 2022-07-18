package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract int findIndex(String uuid);

    protected abstract void saveByIndex(Resume resume);

    protected abstract void deleteByIndex(String uuid);

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Resume " + resume.getUuid() + " was not found.");
        } else {
            storage[index] = resume;
            System.out.println("Resume " + resume.getUuid() + " was updated.");
        }
    }

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Storage overflow.");
        } else if (findIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume " + resume.getUuid() + " already exists.");
        } else {
            saveByIndex(resume);
            size++;
            System.out.println("Resume " + resume.getUuid() + " was saved.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            System.out.println("Resume " + uuid + " was not found.");
            return null;
        }
        return storage[index];
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void delete(String uuid) {
        if (findIndex(uuid) < 0) {
            System.out.println("Resume " + uuid + " was not found.");
        } else {
            size--;
            deleteByIndex(uuid);
            System.out.println("Resume " + uuid + " was deleted.");
        }
    }
}