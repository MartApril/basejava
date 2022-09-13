package com.urise.serializer;

import com.urise.webapp.storage.FileStorage;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectFileStorageTest extends AbstractFileStorageTest {
    public ObjectFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}