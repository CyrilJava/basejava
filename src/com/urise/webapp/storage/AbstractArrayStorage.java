package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
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

    protected boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        if (this.size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        }
        insertResume(searchKey, resume);
        resumeCount++;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    protected abstract Integer getSearchKey(String uuid);

    protected abstract void insertResume(int index, Resume resume);
}
