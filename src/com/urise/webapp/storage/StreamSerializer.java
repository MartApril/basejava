package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Array based storage for Resumes
 */
public interface StreamSerializer {
 Resume doRead(InputStream inputStream) throws IOException;
 void doWrite(Resume resume, OutputStream  outputStream) throws IOException;
}

