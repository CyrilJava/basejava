package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    public ArrayStorage() {
        super();
    }

    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1; // RESUME_NOT_FOUND
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        storage[resumeCount] = resume;
    }

    @Override
    protected void reduceArray(Integer index) {
        storage[index] = storage[resumeCount - 1];
    }
}
