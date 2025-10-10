package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int findIndex(String uuid) {
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
    protected void addResume(Resume resume, int index) {
        int insertionIndex = -index - 1;
        System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, resumeCount - insertionIndex);
        storage[insertionIndex] = resume;

//        if (resumeCount == 0 || insertionIndex * (-1) > resumeCount) { //дописываем в конец
//            storage[resumeCount] = resume;
//        } else { //вставка в середину
//            /*for (int i = resumeCount - 1; i >= Math.abs(insertionIndex) - 1; i--) {
//                storage[i + 1] = storage[i];
//            }*/
//            int newPos = Math.abs(insertionIndex) - 1;
//            System.arraycopy(storage, newPos, storage, newPos + 1, resumeCount - newPos + 1);
//            storage[Math.abs(insertionIndex) - 1] = resume;
//        }
    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, resumeCount - index - 1);
    }
}

