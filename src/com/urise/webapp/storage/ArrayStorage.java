package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    private static final int RESUME_NOT_FOUND = -1;
    public ArrayStorage (){
        super();
    }
    /**
     * @return array, contains only Resumes in storage (without null)
     */
    protected int findIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return RESUME_NOT_FOUND;
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage[resumeCount] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[resumeCount - 1];
        storage[resumeCount - 1] = null;
    }
}
