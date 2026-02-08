package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */

public interface Storage {


    void save(Resume r);

    Resume get(String uuid);

    void update(Resume r);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    //Resume[] getAll();
    int size();

    List<Resume> getAllSorted();

    void clear();
}
