package com.urise.webapp.storage.serialization;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.AbstractStorage;
import com.urise.webapp.storage.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private StreamSerializer streamSerializer;

    public PathStorage(File dir) {
        this.directory = dir.toPath();
    }

    public PathStorage(File dir, StreamSerializer streamSerializer) {
        this.directory = dir.toPath();
        this.streamSerializer = streamSerializer;
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

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
            streamSerializer.doWrite(resume, new BufferedOutputStream(new FileOutputStream(String.valueOf(path))));
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
            return streamSerializer.doRead(new BufferedInputStream(new FileInputStream(String.valueOf(path))));
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
        List<Path> pathList = createList(directory);
        List<Resume> list = new ArrayList<>();
        for (Path path : pathList) {
            list.add(doGet(path));
        }
        return list;
    }

    @Override
    public void clear() {
        createList(directory).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return createList(directory).size();
    }

    private List<Path> createList(Path directory) {
        try {
            return Files.list(directory).collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Directory read error", null);
        }
    }
}
