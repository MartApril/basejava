package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String fullName_1 = "fullName_1";
    protected static final String fullName_2 = "fullName_2";
    protected static final String fullName_3 = "fullName_3";
    protected static final String fullName_4 = "fullName_4";
    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;

    static {
        RESUME_1 = new Resume(UUID_1, fullName_1);
        RESUME_2 = new Resume(UUID_2, fullName_2);
        RESUME_3 = new Resume(UUID_3, fullName_3);
        RESUME_4 = new Resume(UUID_4, fullName_4);
    }

    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
        assertArray(new Resume[0]);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, fullName_1);
        storage.update(resume);
        assertGet(resume);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
//        Resume[] expected = {RESUME_1, RESUME_2, RESUME_3, RESUME_4};
//        assertArray(expected);
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Resume[] expected = {RESUME_2, RESUME_3};
        assertSize(2);
        assertArray(expected);
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_1));
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.update(RESUME_4));
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    private void assertSize(int expected) {
        assertEquals(expected, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private void assertArray(Resume[] resumes) {
        assertArrayEquals(resumes, storage.getAllSorted().toArray(new Resume[0]));
    }
}