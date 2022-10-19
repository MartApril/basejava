package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.util.SqlHelper;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword())));
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