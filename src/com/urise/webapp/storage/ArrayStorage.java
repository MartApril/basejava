package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public int check(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void update(Resume resume) {
        int a = check(resume.getUuid());
        if (a != -1) {
            storage[a] = resume;
            System.out.println("Resume " + resume.getUuid() + " was updated.");
        } else System.out.println("Resume " + resume.getUuid() + " was not updated.");
    }

    public void save(Resume resume) {
        int a = check(resume.getUuid());
        if (a == -1 && size < storage.length) {
            storage[size] = resume;
            size++;
            System.out.println("Resume " + resume.getUuid() + " was saved.");
        } else System.out.println("Resume " + resume.getUuid() + " was not saved.");
    }

    public Resume get(String uuid) {
        int a = check(uuid);
        if (a != -1) {
            System.out.println("Resume " + uuid + " was got.");
            return storage[a];
        } else {
            System.out.println("Resume " + uuid + " was not got.");
            return null;
        }
    }

    public void delete(String uuid) {
        int a = check(uuid);
        if (a != -1) {
            System.arraycopy(storage, a + 1, storage, a, size - a - 1);
            size--;
            System.out.println("Resume " + uuid + " was deleted.");
        } else System.out.println("Resume " + uuid + " was not deleted.");
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
