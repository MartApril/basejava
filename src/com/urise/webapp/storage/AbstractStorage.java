package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());
//    protected final Logger log = Logger.getLogger(getClass().getName());

    protected static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    protected abstract boolean isExist(SK searchKey);

    protected abstract SK findSearchKey(String uuid);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK uuid);

    protected abstract void doDelete(SK uuid);

    protected abstract List<Resume> doCopyAll();

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getNotExistingSearchKey(Resume resume) {
        SK searchKey = findSearchKey(resume.getUuid());
        if (isExist(searchKey)) {
            LOG.warning("Resume " + resume + " already exist");
            throw new ExistStorageException(resume.getUuid());
        }
        return searchKey;
    }

    public void update(Resume resume) {
        LOG.info("Update "+ resume);
        SK searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
        System.out.println("Resume " + resume.getUuid() + " was updated.");
    }

    public void save(Resume resume) {
        LOG.info("Save "+ resume);
        SK searchKey = getNotExistingSearchKey(resume);
        doSave(searchKey, resume);
        System.out.println("Resume " + resume.getUuid() + " was saved.");
    }

    public Resume get(String uuid) {
        LOG.info("Get "+ uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void delete(String uuid) {
        LOG.info("Delete "+ uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.println("Resume " + uuid + " was deleted.");
    }

    public List<Resume> getAllSorted() {
        LOG.info("GetAllSorted");
        List<Resume> resumes = doCopyAll();
        resumes.sort(RESUME_COMPARATOR);
        return resumes;
    }
}
