package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractPathStorage extends AbstractStorage<Path>{
    private final Path directory;
    StorageStrategy storageStrategy;
    protected AbstractPathStorage(String dir,  StorageStrategy storageStrategy) {
        this.directory = Paths.get(dir);
        this.storageStrategy=storageStrategy;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

//    protected abstract Resume doRead(InputStream inputStream) throws IOException;
//
//    public abstract void doWrite(Resume resume, OutputStream outputStream) throws IOException;

    @Override
    protected boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    protected Path findSearchKey(String uuid) {
        File file = new File(String.valueOf(directory), uuid);
        return file.toPath();
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            storageStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("Path write error", resume.getUuid(), e);
        }
    }

    @Override
    public void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create path " + path.toAbsolutePath(), path.toString(), e);
        }
        doUpdate(path, resume);
        System.out.println("save path");
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return storageStrategy.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(path))));
        } catch (IOException e) {
            throw new StorageException("Path read error", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error", path.toString());
        }
    }

    @Override
    protected List<Resume> doCopyAll() {
        try {
            List<Path> pathList = Files.list(directory).collect(Collectors.toList());
            List<Resume> list = new ArrayList<>();
            for (Path path : pathList) {
                list.add(doGet(path));
            }
            return list;
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        try {
            List<Path> list = Files.list(directory).collect(Collectors.toList());
            return list.size();
        } catch (IOException e) {
            throw new StorageException("Path size error", null);
        }
    }
}
