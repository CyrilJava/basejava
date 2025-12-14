package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

/**
 * Array based storage for Resumes
 */

public interface Storage {

    void clear();

    void save(Resume r);
    //void save(Resume r, String fullName); //HW06 для добавления конкретного имени

    Resume get(String uuid);

    void update(Resume r);

    void delete(String uuid);

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    //Resume[] getAll();
    List<Resume> getAllSorted();
    int size();
}
