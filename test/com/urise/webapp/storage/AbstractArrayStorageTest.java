package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void arrayOverflow() throws Exception {
        storage.clear();
        try {
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(Integer.toString(i)));
            }
        } catch (StorageException e) {
            fail("Overflow before 10000");
        }
        storage.save(new Resume("aaa")); //overflow
    }


}