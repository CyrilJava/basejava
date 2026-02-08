package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume index = new Resume(uuid, "dummy");
/*      index.setUuid(uuid);
        Function<Resume, String> resumeFunction = new Function<Resume, String>() {
            @Override
            public String apply(Resume resume) {
                return resume.getUuid();
            }
        };
        return Arrays.binarySearch(storage, 0, resumeCount, index, Comparator.comparing(resumeFunction));
*/
        return Arrays.binarySearch(storage, 0, resumeCount, index, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, resumeCount - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected void doDelete(Integer index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCount - index - 1);
        resumeCount--;
    }
}

