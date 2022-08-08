package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {
    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract boolean isExist(Object object);

    protected abstract Object findSearchKey(String uuid);

    protected abstract void doUpdate(Object object, Resume resume);

    protected abstract void doSave(Object object, Resume resume);

    protected abstract Resume doGet(Object uuid);

    protected abstract void doDelete(Object uuid);

    protected abstract List<Resume> doCopyAll();

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getNotExistingSearchKey(Resume resume) {
        Object searchKey = findSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    public void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
        System.out.println("Resume " + resume.getUuid() + " was updated.");
    }

    public void save(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume);
        doSave(searchKey, resume);
        System.out.println("Resume " + resume.getUuid() + " was saved.");
    }

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("Resume " + uuid + " was deleted.");
    }

    public List<Resume> getAllSorted() {
        List<Resume> resumes = doCopyAll();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }
}
