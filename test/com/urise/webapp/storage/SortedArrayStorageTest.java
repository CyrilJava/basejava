package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private Storage storage = new SortedArrayStorage();
    private static final String UUID_1 = "uuid3";
    private static final String UUID_2 = "uuid7";
    private static final String UUID_3 = "uuid5";
    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        System.out.println("Test storage created: " + storage.size() + " resumes");
    }

    @Test
    public void findIndex() {
    }

    @Test
    public void addResume() {
    }

    @Test
    public void deleteResume() {
    }
}