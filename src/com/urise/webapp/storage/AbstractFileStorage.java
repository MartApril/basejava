package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>   {
    private final File directory;
    StorageStrategy storageStrategy;

    protected AbstractFileStorage(File directory, StorageStrategy storageStrategy) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "is not readable/writable");
        }
        this.directory = directory;
        this.storageStrategy=storageStrategy;
    }

//    protected abstract Resume doRead(InputStream inputStream) throws IOException;
//    protected abstract void doWrite(Resume resume, OutputStream  outputStream) throws IOException;

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            storageStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write error", resume.getUuid(), e);
        }
    }

    @Override
    public void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Couldn't create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory read error", null);
        }
        List<Resume> list = new ArrayList<>(files.length);
        for (File file : files) {
            list.add(doGet(file));
        }
        return list;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        isNull(files);
        for (File file : files) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        File[] files = directory.listFiles();
        isNull(files);
        return files.length;
    }

    private void isNull(File[] files) {
        if (files == null) {
            throw new StorageException("IO error", directory.getName());
        }
    }
}
