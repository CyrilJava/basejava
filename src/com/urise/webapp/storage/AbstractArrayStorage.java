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
        Arrays.fill(storage, 0,resumeCount, null);
        resumeCount = 0;
    }

    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("Resume not found");
        }
    }

    public final void save(Resume resume) {
        if (resume.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        int index = findIndex(resume.getUuid());
        if (resumeCount == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (findIndex(resume.getUuid()) >= 0) {
            System.out.println("Resume with uuid = " + resume.getUuid() + " already exists");
        } else {
            addResume(resume, index);
            resumeCount++;
        }
    }

    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
        return null;
    }

    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
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

    protected abstract void addResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

/*    public void checkNull(Resume r){
        if (r.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
    }*/


}
