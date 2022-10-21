package com.urise.webapp.storage;

import com.urise.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {

    public SqlStorageTest() {
        super(Config.get().getStorage());
    }

    @Override
    public void getAllStorage() {
        super.getAllStorage();
    }

    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    public void size() {
        super.size();
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void update() {
        super.update();
    }

    @Override
    public void save() {
        super.save();
    }

    @Override
    public void delete() {
    }

    @Override
    public void get() {
        super.get();
    }

    @Override
    public void saveExist() {
        super.saveExist();
    }

    @Override
    public void getNotExist() {
        super.getNotExist();
    }

    @Override
    public void updateNotExist() {
        super.updateNotExist();
    }

    @Override
    public void deleteNotExist() {
        super.deleteNotExist();
    }

}