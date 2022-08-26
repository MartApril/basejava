package com.urise.webapp.storage;

import com.urise.webapp.storage.serialization.ObjectStreamStorage;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }
}