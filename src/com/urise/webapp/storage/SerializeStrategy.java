package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializeStrategy {
    public void doWrite(Resume resume, OutputStream os) throws IOException;

    public Resume doRead(InputStream is) throws IOException;
}
