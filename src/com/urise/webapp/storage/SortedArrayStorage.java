package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        //Resume searchKey = new Resume();
        //searchKey.setUuid(uuid);
//        Function<Resume, String> resumeFunction = new Function<Resume, String>() {
//            @Override
//            public String apply(Resume resume) {
//                return resume.getUuid();
//            }
//        };
//        return Arrays.binarySearch(storage, 0, resumeCount, searchKey, Comparator.comparing(resumeFunction));

        return Arrays.binarySearch(storage, 0, resumeCount, searchKey, Comparator.comparing(Resume::getUuid));
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int insertionIndex = - index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, resumeCount - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (int)searchKey;
        System.arraycopy(storage, index + 1, storage, index, resumeCount - index - 1);
        resumeCount--;
    }
}

