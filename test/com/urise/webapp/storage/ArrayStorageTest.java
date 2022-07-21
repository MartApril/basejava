package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Before;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    @Override
    public void setUp() throws Exception {
        setStorage(new ArrayStorage());
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
        super.save();
    }

    @Override
    public void delete() throws Exception {
        super.delete();
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
    public void getStorageOverflow() throws Exception {
        super.getStorageOverflow();
    }
}