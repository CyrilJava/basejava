package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {

    private final Map<String, Resume> storage = new HashMap<>();

    public MapUuidStorage() {
    }

    @Override
    public void clear() {
        storage.clear();
    }

    /*@Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }*/

    @Override
    public List<Resume> getAll() {
        //Resume[] arr = storage.values().toArray(new Resume[0]);
        //return Arrays.asList(arr);
        return new ArrayList(storage.values());
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
    protected boolean isExisting(String searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doSave(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override
    protected Resume doGet(String searchKey) {
        return storage.get(searchKey);
    }

    @Override //++
    protected void doUpdate(String searchKey, Resume resume) {
        storage.put(searchKey, resume);
    }

    @Override //++
    protected void doDelete(String searchKey) {
        storage.remove(searchKey);
    }
}
