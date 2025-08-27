package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {

    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) { //отсутствует сортировка
        if (resumeCount == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (Arrays.binarySearch(storage, 0, resumeCount, r) >= 0) {
            System.out.println("Resume with uuid = " + r.getUuid() + " already exists");
        } else {
            storage[resumeCount] = r;
            resumeCount++;
        }

    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public Resume[] getAll() {
        //return new Resume[0];
        return Arrays.copyOf(storage, resumeCount);
    }

    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey);
        // массив, начало, конец, искомый элемент
    }
}

