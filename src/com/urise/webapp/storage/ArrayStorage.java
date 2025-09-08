package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    protected int findIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1; //uuid not found
    }

    @Override
    protected void addResume(Resume r) {
        storage[resumeCount] = r;
    }

    @Override
    protected void delElement(int index) {
        storage[index] = storage[resumeCount - 1];
        storage[resumeCount - 1] = null;
    }
}
