package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

/**
 * Array based storage for Resumes
 */
public interface StorageStrategy{
 Resume doRead(InputStream inputStream) throws IOException;
 void doWrite(Resume resume, OutputStream  outputStream) throws IOException;
}

