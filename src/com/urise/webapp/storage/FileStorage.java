package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.strategy.SerializeStrategy;
import com.urise.webapp.strategy.Strategy;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> { // Context
    private final File directory;
    SerializeStrategy serializeStrategy;

    public FileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a dir");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
        this.serializeStrategy = new Strategy();
    }

    @Override
    public int size() {
        return getListFiles().length;
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> resumes = new ArrayList<>();
        for (File file : getListFiles()) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    public void clear() {
        for (File file : getListFiles()) {
            doDelete(file);
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExisting(File searchKey) {
        return searchKey.exists();
    }

    @Override
    protected void doSave(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
            serializeStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("I/O error - cannot create file", searchKey.getName(), e);
        }
    }

    @Override
    protected Resume doGet(File searchKey) throws RuntimeException {
        try {
            return serializeStrategy.doRead(new BufferedInputStream(new FileInputStream(searchKey)));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read file", e);
        }
    }

    @Override
    protected void doUpdate(File searchKey, Resume resume) {
        try {
            serializeStrategy.doWrite(resume, new BufferedOutputStream(new FileOutputStream(searchKey)));
        } catch (IOException e) {
            throw new StorageException("I/O error - cannot update file", searchKey.getName(), e);
        }
    }

    @Override
    protected void doDelete(File searchKey) throws StorageException {
        if (!searchKey.delete()) {
            throw new StorageException("File can not be deleted", searchKey.getName(),
                    new IOException("File exists but cannot be deleted"));
        }
    }

    protected File[] getListFiles() {
        File[] list = directory.listFiles();
        if (list == null) {
            throw new RuntimeException();
        }
        return list;
    }
}
