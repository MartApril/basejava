package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR= new File("C:/Users/Anna/IdeaProjects/basejava/storage");
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME_1 = "fullName_1";
    private static final String FULL_NAME_2 = "fullName_2";
    private static final String FULL_NAME_3 = "fullName_3";
    private static final String FULL_NAME_4 = "fullName_4";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;

    static {
        RESUME_1 = ResumeTestData.createNewResume(UUID_1, FULL_NAME_1);
        RESUME_2 = ResumeTestData.createNewResume(UUID_2, FULL_NAME_2);
        RESUME_3 = ResumeTestData.createNewResume(UUID_3, FULL_NAME_3);
        RESUME_4 = ResumeTestData.createNewResume(UUID_4, FULL_NAME_4);
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
        Resume resume = new Resume(UUID_1, "New name");
        storage.update(resume);
        assertGet(resume);
        assertTrue(resume.equals(storage.get(UUID_1)));
//        assertSame(resume, storage.get(UUID_1));
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
    public void getAllStorage() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
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