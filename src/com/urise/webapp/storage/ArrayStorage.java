package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


/**
 * Array based storage for Resumes
 */

public class ArrayStorage {
    private final int storageSize = 10000;
    protected final Resume[] storage = new Resume[storageSize];
    private int resumeCount = 0;

    public void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }

    public void update(Resume r) {
        /*boolean resumeNotFound = true;
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].equals(r)) {
                storage[i].setUuid(r.getUuid() + "_upd");
                resumeNotFound = false;
                break;
            }
        }
        if (resumeNotFound) {
            System.out.println("Resume not found");
        }*/
        int index = findIndex(r.getUuid());
        if (index >=0) {
            storage[index].setUuid(r.getUuid() + "_upd");
        } else System.out.println("Resume not found");
    }

    public void save(Resume r) {
        //boolean resumeIsNew = true;
        if (r.getUuid() != null) {
            if (resumeCount == storageSize) {
                System.out.println("Storage is full");
            } else {
                /*for (int i = 0; i < resumeCount; i++) {
                    if (storage[i].getUuid().equals(r.getUuid())) {
                        System.out.println("Resume with uuid = " + r.getUuid() + " already exists");
                        resumeIsNew = false;
                    }
                }
                if (resumeIsNew) {
                    storage[resumeCount] = r;
                    resumeCount++;
                }*/
                int index = findIndex(r.getUuid());
                if (index == -1) {
                    storage[resumeCount] = r;
                    resumeCount++;
                } else System.out.println("Resume with uuid = " + r.getUuid() + " already exists");
            }
        } else {
            System.out.println("No uuid found");
        }
    }

    public Resume get(String uuid) {
        /*boolean resumeNotFound = true;
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                resumeNotFound = false;
                return storage[i];
            }
        }
        if (resumeNotFound) {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }*/
        int index = findIndex(uuid);
        if (index >=0) {
            return storage[index];
        } else System.out.println("Resume with uuid = " + uuid + " not found");
        return null;
    }

    public void delete(String uuid) {
        /*boolean resumeNotFound = true;
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                resumeNotFound = false;
                storage[i] = storage[resumeCount - 1];
                storage[resumeCount - 1] = null;
                resumeCount--;
                break;
            }
        }
        if (resumeNotFound) {
            System.out.println("Resume with uuid " + uuid + " not found");
        }*/
        int index = findIndex(uuid);
        if (index >=0) {
            storage[index] = storage[resumeCount - 1];
            storage[resumeCount - 1] = null;
            resumeCount--;
        } else System.out.println("Resume with uuid = " + uuid + " not found");
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] onlyResumes = new Resume[resumeCount];
        for (int i = 0; i < resumeCount; i++) {
            onlyResumes[i] = storage[i];
        }
        return onlyResumes;
    }

    public int size() {
        return resumeCount;
    }
    int findIndex(String uuid){
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1; //uuid not found
   }
}
