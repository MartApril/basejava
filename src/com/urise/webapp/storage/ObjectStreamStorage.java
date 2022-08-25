package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage implements StorageStrategy {

    public ObjectStreamStorage(File directory) {
        super(directory, new ObjectStreamStorage(directory));
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (ObjectInputStream ois = new ObjectInputStream(inputStream)) {
            System.out.println("work objectStream");
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }

    @Override
     public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(resume);
        }
    }
}
