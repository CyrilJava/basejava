package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;

    public AbstractArrayStorage() {
    }

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }

    public int size() {
        return resumeCount;
    }

    public List<Resume> getAll(){
        return Arrays.asList(Arrays.copyOf(storage, resumeCount));
    };

    protected boolean isExisting(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        if (this.size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", searchKey.toString());
        }
        insertResume((int) searchKey, resume);
        resumeCount++;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    protected abstract Object getSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);
}
