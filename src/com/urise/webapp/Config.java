package com.urise.webapp;

import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class Config {
    protected static final File PROPS = new File(getHomeDir(), "config\\resume.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;
    private final Storage storage;
    private Set<String> immutableUuids = new HashSet<String>() {{  // for JDK 9+: Set.of("111", "222");
        add("11111111-1111-1111-1111-111111111111");
        add("22222222-2222-2222-2222-222222222222");
    }};

    public static Config get() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties props = new Properties();
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorage(new SqlHelper(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password")));
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

    public boolean isImmutable(String uuids) {
        return immutableUuids.contains(uuids);
    }

    public void checkImmutable(String uuids) {
        if (immutableUuids.contains(uuids))
            throw new RuntimeException("Зарезервированные резюме нельзя менять");
    }

    private static File getHomeDir() {
        String prop = System.getProperty("homeDir");
        File homeDir = new File(prop == null ? "." : prop);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }
}
