package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class SortedArrayStorage extends AbstractArrayStorage {
    /*private static class ResumeComparator implements Comparator<Resume> {
            @Override
            public int compare(Resume o1, Resume o2) {
                return o1.getUuid().compareTo(o2.getUuid());
            }
        }*/
    //private static final Comparator<Resume> RESUME_COMPARATOR = (o1, o2) -> o1.getUuid().compareTo(o2.getUuid());

    @Override
    public List<Resume> getAllSorted() {
        Resume[] arr = Arrays.copyOf(storage, resumeCount);
        List<Resume> tempList = Arrays.asList(arr);
        tempList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        //return Arrays.asList(arr);
        return tempList;
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
/*      Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        Function<Resume, String> resumeFunction = new Function<Resume, String>() {
            @Override
            public String apply(Resume resume) {
                return resume.getUuid();
            }
        };
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey, Comparator.comparing(resumeFunction));
*/
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey, Comparator.comparing(Resume::getUuid));
        //return Arrays.binarySearch(storage, 0, resumeCount, searchKey, RESUME_COMPARATOR);
    }

    @Override
    protected void insertResume(int index, Resume resume) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, resumeCount - insertionIndex);
        storage[insertionIndex] = resume;
    }

    @Override
    protected void doDelete(Object searchKey) {
        int index = (int) searchKey;
        System.arraycopy(storage, index + 1, storage, index, resumeCount - index - 1);
        resumeCount--;
    }
}

