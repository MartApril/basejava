package com.urise.webapp.storage;

import com.urise.serializer.JsonPathStorageTest;
import com.urise.serializer.ObjectFileStorageTest;
import com.urise.serializer.ObjectPathStorageTest;
import com.urise.serializer.XmlPathStorageTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class, SortedArrayStorageTest.class, ListStorageTest.class,
        MapResumeStorageTest.class, MapUuidStorageTest.class, ObjectFileStorageTest.class,
        ObjectPathStorageTest.class, XmlPathStorageTest.class, JsonPathStorageTest.class
})
public class AllStorageTest {

}