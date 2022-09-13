package com.urise.serializer;

import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractFileStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}