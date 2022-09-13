package com.urise.serializer;

import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractFileStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}