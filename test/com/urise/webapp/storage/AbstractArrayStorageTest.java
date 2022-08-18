package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static com.urise.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.Assert.assertThrows;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Override
    public void getAllStorage() {
        super.getAllStorage();
    }

    @Test
    public void testOverflow() {
        storage.clear();
        for (int i = 0; i < STORAGE_LIMIT; i++) {
            storage.save(new Resume("Name "+i));
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("Overflow")));
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
        super.delete();
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