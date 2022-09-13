package com.urise.serializer;

import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.ObjectStreamSerializer;

public class ObjectPathStorageTest extends AbstractFileStorageTest {
    public ObjectPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}