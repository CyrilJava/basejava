package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    public MapResumeStorage() {    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected Resume getSearchKey(String uuid) { return storage.get(uuid); }

    @Override
    protected boolean isExisting(Resume key) {
        return key != null;
    }

    @Override
    protected void doSave(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume doGet(Resume key) {
        return key;
    }

    @Override //++
    protected void doUpdate(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override //++
    protected void doDelete(Resume key) {
        storage.remove(key.getUuid());
    }
}
