package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;

    public AbstractStorage() {
    };

    public AbstractStorage(Storage storage) {};

    public abstract void clear();

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(r.getUuid());
        } else {
            updateResume(r, index); //реализовать в дочерних
        }
    }

    public void save(Resume resume) {
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
    }

    @Override
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);

        }
        return getResume(index);
    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
            //resumeCount--; //только для массивов - нужно переделать deleteResume
        }
    }

    //@Override
    public abstract Resume[] getAll();

    public abstract int size();

    protected abstract int findIndex(String uuid);

    protected abstract void addResume(Resume resume, int index);

    protected abstract Resume getResume(int index);

    protected abstract void updateResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

}
