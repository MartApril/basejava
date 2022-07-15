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

    public int size() {
        return size;
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
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

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " was not found.");
        } else {
            size--;
//            storage[index] = storage[size];
//            storage[size] = null;
            System.arraycopy(storage, index + 1, storage, index, size - index);
            System.out.println("Resume " + uuid + " was deleted.");
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " was not found.");
            return null;
        }
        return storage[index];
    }

    protected abstract int findIndex(String uuid);

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}
