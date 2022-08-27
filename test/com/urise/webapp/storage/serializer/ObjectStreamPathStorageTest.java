package com.urise.webapp.storage.serializer;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.FileStorage;
import com.urise.webapp.storage.PathStorage;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ObjectStreamPathStorageTest {
    protected static final File STORAGE_DIR = new File("C:/Users/Anna/IdeaProjects/basejava/storage");
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String FULL_NAME_1 = "fullName_1";
    private static final String FULL_NAME_2 = "fullName_2";
    private static final String FULL_NAME_3 = "fullName_3";
    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;

    static {
        RESUME_1 = ResumeTestData.createNewResume(UUID_1, FULL_NAME_1);
        RESUME_2 = ResumeTestData.createNewResume(UUID_2, FULL_NAME_2);
        RESUME_3 = ResumeTestData.createNewResume(UUID_3, FULL_NAME_3);
    }

    protected final PathStorage storage = new PathStorage(STORAGE_DIR, new ObjectStreamStorage());

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }
    @Test
    public void testDoRead() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
    }
    @Test
    public void testDoWrite() {
        Resume resume = new Resume(UUID_1, "New name");
        storage.update(resume);
        assertEquals(resume, storage.get(resume.getUuid()));
        assertTrue(resume.equals(storage.get(UUID_1)));
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}