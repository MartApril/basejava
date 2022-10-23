package com.urise.serializer;

import com.urise.webapp.Config;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.storage.AbstractStorageTest;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.nio.file.FileSystems;

public abstract class AbstractFileStorageTest extends AbstractStorageTest {
    private static final String PROJECT_PATH= System.getProperty("user.dir");
    private static final String NAME_SEPARATOR = FileSystems.getDefault().getSeparator();
    private static final String FILE_STORAGE = "storage";

    protected static final File STORAGE_DIR = Config.get().getStorageDir();
//    protected static final File STORAGE_DIR= new File(PROJECT_PATH+NAME_SEPARATOR+FILE_STORAGE);

    static {
        if (!STORAGE_DIR.exists() && (!STORAGE_DIR.mkdir())) {
            throw new StorageException("Cannot create storage directory");
        }
    }

    public AbstractFileStorageTest(Storage storage) {
        super(storage);
    }
}
