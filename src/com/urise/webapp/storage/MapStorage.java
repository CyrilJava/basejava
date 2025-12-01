package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage{

    HashMap<Integer, Resume> storage = new HashMap<>();

    public MapStorage() {
    }

    @Override
    public void clear() {

    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected boolean isExisting(Object searchKey) {
        return false;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {

    }

    @Override
    protected Resume doGet(Object searchKey) {
        return null;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {

    }

    @Override
    protected void doDelete(Object searchKey) {

    }
}
