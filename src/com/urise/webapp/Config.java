package com.urise.webapp;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.util.SqlHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Config INSTANCE = new Config();
    protected static final File PROPS = new File("./config/resume.properties");
    private final Properties props = new Properties();
    private final File storageDir;
    private final Storage storage;

    public static Config get() {
        return INSTANCE;
    }

    public Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(new SqlHelper(props.getProperty("jdbc:postgresql://localhost:5432/resumes)"), props.getProperty("postgres"), props.getProperty("postgres")));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public Storage getStorage() {
        return storage;
    }
}
