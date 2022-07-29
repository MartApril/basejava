package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract boolean isExist(Object object);

    protected abstract Object findSearchKey(String uuid);

    protected abstract void doUpdate(Resume resume);

    protected abstract void doSave(Resume resume);

    protected abstract Resume doGet(String uuid);

    protected abstract void doDelete(String uuid);

    private void getExistingSearchKey(String uuid) {
        if (!isExist(findSearchKey(uuid))) {
            throw new NotExistStorageException(uuid);
        }
    }

    private void getNotExistingSearchKey(Resume resume) {
        if (isExist(findSearchKey(resume.getUuid()))) {
            throw new ExistStorageException(resume.getUuid());
        }
    }

    public void update(Resume resume) {
        getExistingSearchKey(resume.getUuid());
        doUpdate(resume);
        System.out.println("Resume " + resume.getUuid() + " was updated.");
    }

    public void save(Resume resume) {
        getNotExistingSearchKey(resume);
        doSave(resume);
        System.out.println("Resume " + resume.getUuid() + " was saved.");

//        if (isExist(resume)) {
//            throw new ExistStorageException(resume.getUuid());
//        } else {
//            doSave(resume);
//            System.out.println("Resume " + resume.getUuid() + " was saved.");
//        }
    }

    public Resume get(String uuid) {
        getExistingSearchKey(uuid);
        return doGet(uuid);

//        if (!isExist(uuid)) {
//            throw new NotExistStorageException(uuid);
//        }
//        return doGet(uuid);
    }

    public void delete(String uuid) {
        getExistingSearchKey(uuid);
        doDelete(uuid);
        System.out.println("Resume " + uuid + " was deleted.");

//        if (!isExist(uuid)) {
//            throw new NotExistStorageException(uuid);
//        } else {
//            doDelete(uuid);
//            System.out.println("Resume " + uuid + " was deleted.");
//        }
    }
}
