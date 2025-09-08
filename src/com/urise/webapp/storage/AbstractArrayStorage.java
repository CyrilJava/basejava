package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;

    public void clear() {
        Arrays.fill(storage, null);
        resumeCount = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        } else {
            System.out.println("Resume not found");
        }
    }

    public void save(Resume r) {
        if (r.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        //int index = findIndex(r.getUuid());
        if (resumeCount == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (findIndex(r.getUuid()) >= 0) {
            System.out.println("Resume with uuid = " + r.getUuid() + " already exists");
        } else {
            addResume(r);
            resumeCount++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            delElement(index);
            resumeCount--;
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
    }

    public int size() {
        return resumeCount;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    protected abstract int findIndex(String uuid);

    protected abstract void addResume(Resume r);

    protected abstract void delElement(int index);

/*    public void checkNull(Resume r){
        if (r.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
    }*/


}
