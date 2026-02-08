package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    public MapUuidStorage() {    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAll() { return new ArrayList<>(storage.values()); }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) { return uuid; }

    @Override
    protected boolean isExisting(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected void doSave(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected Resume doGet(String key) {
        return storage.get(key);
    }

    @Override
    protected void doUpdate(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }
}
