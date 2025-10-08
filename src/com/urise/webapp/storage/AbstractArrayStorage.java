package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;
    //protected String type;
    public AbstractArrayStorage (){};

    public AbstractArrayStorage (Storage storage){
    };


    public void clear() {
        Arrays.fill(storage, 0,resumeCount, null);
        resumeCount = 0;
    }
    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
            //System.out.println("Resume not found");
        } else {
            storage[index] = resume;
        }
    }

    public final void save(Resume resume) {
        if (resume.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        int index = findIndex(resume.getUuid());
        if (resumeCount == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
            //System.out.println("Storage is full");
        } else if (findIndex(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
            //System.out.println("Resume with uuid = " + resume.getUuid() + " already exists");
        } else {
            addResume(resume, index);
            resumeCount++;
        }
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
            //System.out.println("Resume with uuid = " + uuid + " not found");
        }
        return storage[index];
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
            //System.out.println("Resume with uuid = " + uuid + " not found");
        } else {
            deleteResume(index);
            resumeCount--;
        }
    }

    public int size() {
        return resumeCount;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    protected abstract int findIndex(String uuid);

    protected abstract void addResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

/*    public void checkNull(Resume r){
        if (r.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
    }*/


}
