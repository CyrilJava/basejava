package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    public AbstractStorage() {
    }

    public abstract void clear();

/*    public void save(Resume resume) {
        if (resume.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        int index = findIndex(resume.getUuid());
        if (this.size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());

        } else if (findIndex(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());

        } else {
            addResume(resume, index);
            //resumeCount++; //только для массивов - нужно переделать addResume
        }
    }*/

    public final void save(Resume resume) {
        /*if (resume.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        Object searchKey = getNotExistingSearchKey(resume.getUuid());*/
        String id = resume.getUuid();
        if (id == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        Object searchKey = getNotExistingSearchKey(id);
        doSave(searchKey, resume);
    }

    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private Object getNotExistingSearchKey(String uuid) { //для save
        Object searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public abstract Resume[] getAll();

    public abstract int size();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExisting(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

}
