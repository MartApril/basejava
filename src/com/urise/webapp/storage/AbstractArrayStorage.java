package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;


    protected abstract void saveByIndex(Object object, Resume resume);

    @Override
    protected boolean isExist(Object object) {
        return (int) object >= 0;
    }

    @Override
    protected void doUpdate(Object object, Resume resume) {
        storage[(int) object] = resume;
    }

    @Override
    protected void doSave(Object object, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        } else {
            saveByIndex(object, resume);
            size++;
        }
    }

    @Override
    protected Resume doGet(Object uuid) {
        return storage[(int) uuid];
    }

    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
}