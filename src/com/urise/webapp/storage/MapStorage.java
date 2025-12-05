package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    public MapStorage() {
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
/*        Resume resume = new Resume(uuid);
        Object searchKey = new Object();
        for (Map.Entry<String, Resume> entry : storage.entrySet()) {
            if (entry.getValue().equals(resume)) {
                searchKey = entry.getKey();
                break;
            }
        }
        return searchKey;*/
        return uuid;
    }

    @Override
    protected boolean isExisting(Object searchKey) { return storage.containsKey(searchKey);  }

    @Override
    protected void doSave(Object searchKey, Resume resume) { storage.put(searchKey.toString(), resume); }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((String) searchKey);
    }

    @Override //++
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.put((String) searchKey, resume);
    }

    @Override //++
    protected void doDelete(Object searchKey) {
        storage.remove((String) searchKey);
    }
}
