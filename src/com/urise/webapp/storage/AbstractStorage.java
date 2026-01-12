package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage implements Storage {
    //protected final Logger log = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    //public AbstractStorage() {    }
    public final void save(Resume resume) {
        //LOG.info("Save " + resume);
        if (resume.getUuid() == null) {
            throw new IllegalArgumentException("No uuid found");
        }
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private Object getNotExistingSearchKey(String uuid) { //для save
        Object searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            LOG.warning("Resume " + uuid + " does not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public abstract int size();

    //public abstract Resume[] getAll();

    protected abstract List<Resume> getAll();

    public List<Resume> getAllSorted() {
        List<Resume> getAllList = getAll();
        getAllList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        //Collections.sort(getAllList, Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return getAllList;
    }

    ;

    public abstract void clear();

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExisting(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

}
