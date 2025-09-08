package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }

    @Override //выясняем, что возвращает binarySearch
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        if (index >= 0) {
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

    @Override
    protected void addResume(Resume r) {
        int binSearchRes = Arrays.binarySearch(storage, 0, resumeCount, r);
        if (resumeCount == 0 || binSearchRes * (-1) > resumeCount) { //дописываем в конец
            storage[resumeCount] = r;
        } else { //вставка в середину
            /*for (int i = resumeCount - 1; i >= Math.abs(binSearchRes) - 1; i--) {
                storage[i + 1] = storage[i];
            }*/
            int newPos = Math.abs(binSearchRes) - 1;
            System.arraycopy(storage, newPos, storage, newPos + 1, resumeCount - newPos + 1);
            storage[Math.abs(binSearchRes) - 1] = r;
        }
    }

    @Override
    protected void delElement(int index) {
        for (int i = index; i < resumeCount; i++) {
            storage[i] = storage[i + 1];
        }
    }
}

