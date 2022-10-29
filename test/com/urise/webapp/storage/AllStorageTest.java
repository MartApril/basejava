package com.urise.webapp.storage;

import com.urise.serializer.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class,
        MapResumeStorageTest.class, MapUuidStorageTest.class, ObjectFileStorageTest.class,
        ObjectPathStorageTest.class, XmlPathStorageTest.class, JsonPathStorageTest.class,
        DataPathStorageTest.class, SqlStorageTest.class
})
public class AllStorageTest {

}