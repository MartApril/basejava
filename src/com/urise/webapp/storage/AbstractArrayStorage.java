package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10000;

    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void saveByIndex(Integer index, Resume resume);


    @Override
    protected boolean isExist(Integer searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow.", resume.getUuid());
        } else {
            saveByIndex(searchKey, resume);
            size++;
        }
    }

    @Override
    protected Resume doGet(Integer uuid) {
        return storage[uuid];
    }

    @Override
    protected List<Resume> doCopyAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }
}