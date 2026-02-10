package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getSearchKey(String uuid) {
        Resume resume = new Resume(uuid, "dummy");
        return Arrays.binarySearch(storage, 0, resumeCount, resume, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage,
                insertionIndex + 1, resumeCount - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected void deleteResume(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCount - index - 1);
    }
}
