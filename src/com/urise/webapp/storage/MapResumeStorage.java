package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {
    private final Map<String, Resume> storage = new HashMap<>();

    public MapResumeStorage() {
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAllSorted() {
        Resume[] arr = storage.values().toArray(new Resume[0]);
        Arrays.sort(arr, Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return Arrays.asList(arr);
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected String getSearchKey(String uuid) {
        Resume resume = new Resume(uuid);
        return new StringBuilder().append(resume.getFullName()).append(uuid).toString();
    }

    @Override
    protected boolean isExisting(Object searchKey) {
        return storage.containsKey(searchKey);
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.put(searchKey.toString(), resume);
    }

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
