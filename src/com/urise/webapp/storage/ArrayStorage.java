package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

import static java.util.Arrays.copyOf;


/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    private final int STORAGE_LIMIT = 10000;
    protected final Resume[] storage = new Resume[STORAGE_LIMIT];
    private int resumeCount = 0;

    public void clear() {
        /*for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }*/
        Arrays.fill(storage, null);
        resumeCount = 0;
    }

    public void update(Resume r) {
        int index = findIndex(r.getUuid());
        if (index >=0) {
            storage[index].setUuid(r.getUuid() + "_upd");
        } else {
            System.out.println("Resume not found");
        }
    }

    //if (overflow) else if (exist) else (save logic)
    public void save(Resume r) {
        if (r.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        int index = findIndex(r.getUuid());
        if (resumeCount == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (index >= 0) {
            System.out.println("Resume with uuid = " + r.getUuid() + " already exists");
        } else {
            storage[resumeCount] = r;
            resumeCount++;
        }
    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >=0) {
            return storage[index];
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
        return null;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >=0) {
            storage[index] = storage[resumeCount - 1];
            storage[resumeCount - 1] = null;
            resumeCount--;
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        /*Resume[] onlyResumes = new Resume[resumeCount];
        for (int i = 0; i < resumeCount; i++) {
            onlyResumes[i] = storage[i];
        }*/
        Resume[] onlyResumes = copyOf(storage, resumeCount);
        return onlyResumes;
    }

    public int size() {
        return resumeCount;
    }
    protected int findIndex(String uuid){
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1; //uuid not found
   }
}
