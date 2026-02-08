package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    public ListStorage() {
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }

    /*@Override
    public Resume[] getAll() {
        Resume[] arr = new Resume[storage.size()];
        storage.toArray(arr);
        return arr;
    }*/

    @Override
    public List<Resume> getAll() {
        //storage.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return new ArrayList<>(storage);
    }

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }


    @Override
    protected boolean isExisting(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);
    }

}
