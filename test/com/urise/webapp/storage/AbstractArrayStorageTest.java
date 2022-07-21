package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {
    //    private final Storage storage = new ArrayStorage();
    private Storage storage;

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getAll() throws Exception {
        Resume[] arraysOfResume = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3)};
        Assert.assertArrayEquals(arraysOfResume, storage.getAll());
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume();
        storage.save(resume);
        Resume[] arraysOfResume = {new Resume(UUID_1), new Resume(UUID_2), new Resume(UUID_3), resume};
        Assert.assertArrayEquals(arraysOfResume, storage.getAll());
    }

    @Test
    public void delete() throws Exception {
        storage.delete("uuid1");
        Resume[] arraysOfResume = {new Resume(UUID_3), new Resume(UUID_2)};
        Assert.assertArrayEquals(arraysOfResume, storage.getAll());
    }

    @Test
    public void get() throws Exception {
        Resume resume = new Resume("uuid1");
        Assert.assertEquals(resume, storage.get("uuid1"));
    }

    @Test(expected = ExistStorageException.class)
    public void getExist() throws Exception {
        storage.save(new Resume(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    @Test(expected = StorageException.class)
    public void getStorageOverflow() throws Exception {
        for (int i = 3; i < 10001; i++) {
            storage.save(new Resume());
        }
    }
}