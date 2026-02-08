package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements com.urise.webapp.storage.Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public final void save(Resume resume) {
        SK searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    public Resume get(String uuid) {
        //LOG.info("Get " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    public void update(Resume resume) {
        //LOG.info("Update " + resume);
        SK searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    public void delete(String uuid) {
        //LOG.info("Delete " + uuid);
        SK searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
    }

    private SK getNotExistingSearchKey(String uuid) { //для save
        SK searchKey = getSearchKey(uuid);
        if (isExisting(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private SK getExistingSearchKey(String uuid) {
        SK searchKey = getSearchKey(uuid);
        if (!isExisting(searchKey)) {
            LOG.warning("Resume " + uuid + " does not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

//    public abstract int size();
//    public abstract void clear();s

    public List<Resume> getAllSorted() {
        List<Resume> resumes = getAll();
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    protected abstract List<Resume> getAll();

    protected abstract SK getSearchKey(String uuid);

    protected abstract boolean isExisting(SK searchKey);

    protected abstract void doSave(SK searchKey, Resume resume);

    protected abstract Resume doGet(SK searchKey);

    protected abstract void doUpdate(SK searchKey, Resume resume);

    protected abstract void doDelete(SK searchKey);

}
