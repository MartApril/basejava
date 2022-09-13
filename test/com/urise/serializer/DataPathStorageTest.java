package com.urise.serializer;

import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractFileStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}