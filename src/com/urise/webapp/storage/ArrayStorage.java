package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */

public class ArrayStorage extends AbstractArrayStorage {

    public ArrayStorage() {
        super();
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;//RESUME_NOT_FOUND
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        storage[resumeCount] = resume;
    }

    @Override
    protected void doDelete(Integer index) {
        storage[index] = storage[resumeCount - 1];
        storage[resumeCount - 1] = null;
        resumeCount--;
    }
}
