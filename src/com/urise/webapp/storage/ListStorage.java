package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.LinkedList;

public class ListStorage extends AbstractStorage {
    LinkedList<Resume> storage = new LinkedList<>();

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

    @Override
    public Resume[] getAll() {
        Resume[] arr = new Resume[storage.size()];
        storage.toArray(arr);
        return arr;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        Resume r = new Resume(uuid);
        return storage.indexOf(r);
    }

    @Override
    protected boolean isExisting(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((Integer) searchKey);
    } //getResume

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);
    }

}
