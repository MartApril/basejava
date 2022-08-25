package com.urise.webapp.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(String.valueOf(STORAGE_DIR)));
    }

}