package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a dir");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override //+
    public int size() {
        File[] list = directory.listFiles();
        int i = 0;
        for (File file : list) {
            if (!file.isDirectory()) {
                i++;
            }
        }
        return i; //сколько файлов
    }

    @Override //+
    protected List<Resume> getAll() {
        String[] list = directory.list();
        List<Resume> result = new ArrayList<>();
        if (list != null && list.length > 0) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                if (!file.isDirectory()){
                    try {
                        result.add(doRead(file));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return result; // читает резюме из всех файлов каталога
    }

    @Override //+
    public void clear() {
        //удалить все файлы
        String[] list = directory.list();
        if (list != null && list.length > 0) {
            for (File file : Objects.requireNonNull(directory.listFiles())) {
                file.delete();
            }
        }

    }

    @Override //ok
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override //ok
    protected boolean isExisting(File searchKey) {
        return searchKey.exists();
    }

    @Override //ok
    protected void doSave(File searchKey, Resume resume) {
        try {
            searchKey.createNewFile();
            doWrite(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("I/O error", searchKey.getName(), e);
        }
    }

    @Override //+
    protected Resume doGet(File searchKey) {
        try {
            return doRead(searchKey);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override //+
    protected void doUpdate(File searchKey, Resume resume) {
        try {
            doModify(resume, searchKey);
        } catch (IOException e) {
            throw new StorageException("I/O error", searchKey.getName(), e);
        }
    }

    @Override //+
    protected void doDelete(File searchKey) {
        //удаляет файл
        searchKey.delete();
    }

    protected abstract void doWrite(Resume resume, File searchKey) throws IOException;

    protected abstract void doModify(Resume resume, File searchKey) throws IOException;

    protected abstract Resume doRead(File searchKey) throws IOException;

}
