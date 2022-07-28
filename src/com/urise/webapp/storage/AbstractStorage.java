package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected Storage storage;

    protected abstract int findSearchKey(String uuid);

    protected abstract void updateByRealization(Resume resume, int index);

    protected abstract void saveByRealization(Resume resume);

    protected abstract Resume getByRealization(int index);

    protected abstract void deleteByIndex(int index);

    public void update(Resume resume) {
        int index = findSearchKey(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            updateByRealization(resume, index);
            System.out.println("Resume " + resume.getUuid() + " was updated.");
        }
    }

    public void save(Resume resume) {
        if (findSearchKey(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            saveByRealization(resume);
            System.out.println("Resume " + resume.getUuid() + " was saved.");
        }
    }

    public Resume get(String uuid) {
        int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getByRealization(index);
    }

    public void delete(String uuid) {
        int index = findSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteByIndex(index);
            System.out.println("Resume " + uuid + " was deleted.");
        }
    }
}
