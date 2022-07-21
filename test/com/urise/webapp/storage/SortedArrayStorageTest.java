package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    @Override
    public void setUp() throws Exception {
        setStorage(new SortedArrayStorage());
        super.setUp();
    }

    @Override
    public void size() throws Exception {
        super.size();
    }

    @Override
    public void clear() throws Exception {
        super.clear();
    }

    @Override
    public void update() throws Exception {
        super.update();
    }

    @Override
    public void getAll() throws Exception {
        super.getAll();
    }

    @Override
    public void save() throws Exception {
        Resume resume = new Resume("uuid0");
        getStorage().save(resume);
        Resume[] arraysOfResume = {resume, new Resume("uuid1"), new Resume("uuid2"), new Resume("uuid3")};
        Assert.assertArrayEquals(arraysOfResume, getStorage().getAll());
    }

    @Override
    public void delete() throws Exception {
        getStorage().delete("uuid1");
        Resume[] arraysOfResume = {new Resume("uuid2"), new Resume("uuid3")};
        Assert.assertArrayEquals(arraysOfResume, getStorage().getAll());
    }

    @Override
    public void get() throws Exception {
        super.get();
    }

    @Override
    public void getExist() throws Exception {
        super.getExist();
    }

    @Override
    public void getNotExist() throws Exception {
        super.getNotExist();
    }

    @Override
    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getStorageOverflow() throws Exception {
        for (int i = getStorage().size(); i < 10000; i++) {
            getStorage().save(new Resume());
        }
    }
}