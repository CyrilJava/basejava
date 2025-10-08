package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

public class ArrayStorageTest extends AbstractArrayStorageTest {
    private Storage storage = new ArrayStorage();

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
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