package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.LinkedList;

public class ListStorage extends AbstractStorage {
//public class ListStorage implements Storage {

    LinkedList<Resume> storage = new LinkedList<>();

    public ListStorage() {};

    public ListStorage(Storage storage) {};

    @Override
    public void clear() {
        storage.clear();
        //resumeCount = 0;
    }

/*    @Override
    public final void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage.set(index, resume);
        }
    }*/

    /*@Override
    public final void save(Resume resume) {
        if (resume.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        int index = findIndex(resume.getUuid());
        if (this.size() == STORAGE_LIMIT) {
            throw new StorageException("Storage is full", resume.getUuid());
        } else if (findIndex(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else {
            //storage.add(resume);
            addResume(resume,0);
        }
    }*/

    /*@Override
    public final Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage.get(index);
    }*/

    /*@Override
    public final void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        } else {
            deleteResume(index);
        }
    }*/

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
    protected int findIndex(String uuid) {
        Resume r = new Resume(uuid);
        return storage.indexOf(r);
    }

    @Override
    protected Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected void updateResume(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    protected void addResume(Resume resume, int index){
        storage.add(resume);
    };

    @Override
    protected void deleteResume(int index) {
        storage.remove(index);
    }
}
