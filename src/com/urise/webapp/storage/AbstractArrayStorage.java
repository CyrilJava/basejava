package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */

public abstract class AbstractArrayStorage implements Storage{
    public void clear() {
        Arrays.fill(storage, null);
        resumeCount = 0;
    }

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;

    public int size() {
        return resumeCount;
    }

    //public abstract void clear();

    public abstract Resume[] getAll();

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >=0) {
            return storage[index];
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
        return null;
    }

    protected abstract int findIndex(String uuid);
/*    public void checkNull(Resume r){
        if (r.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
    }*/


}
