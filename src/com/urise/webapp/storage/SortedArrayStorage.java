package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
        for (int i=0; i<resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) { //сортирует при добавлении элемента
        if (r.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        if (resumeCount == STORAGE_LIMIT) {
            System.out.println("Storage is full");
        } else if (findIndex(r.getUuid()) >= 0) {
            System.out.println("Resume with uuid = " + r.getUuid() + " already exists");
        } else {
            int binSearchRes = Arrays.binarySearch(storage, 0, resumeCount, r);
            if (resumeCount == 0 || binSearchRes * (-1)>resumeCount) {
                storage[resumeCount] = r;
            } else /*if (binSearchRes <=0) */{
                for (int i = resumeCount-1; i >= Math.abs(binSearchRes)-1; i--) {
                    storage[i+1] = storage[i];
                }
                storage[Math.abs(binSearchRes)-1] = r;
            }
            resumeCount++;
        }

    }

    @Override
    public void delete(String uuid) {
        if (findIndex(uuid) >=0) {
            for (int i=findIndex(uuid); i<resumeCount; i++) {
                storage[i] = storage[i+1];
            }
            resumeCount--;
        } else {
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
    }

    @Override
    public Resume[] getAll() {
        Resume[] onlyResume = new Resume[resumeCount];
        for (int i=0; i<resumeCount; i++) {
            onlyResume[i] = storage[i];
        }
        return onlyResume;
    }
    @Override //выясняем, что возвращает binarySearch
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        if (index >=0) {
            System.out.println("Binary search value [exists] = " + Arrays.binarySearch(storage, 0, resumeCount, searchKey));
            return storage[index];
        } else {
            System.out.println("Binary search value [not exists] = " + Arrays.binarySearch(storage, 0, resumeCount, searchKey));
            System.out.println("Resume with uuid = " + uuid + " not found");
        }
        return null;
    }
    @Override
    protected int findIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey);
        // массив, начало, конец, искомый элемент
    }
}

