package com.urise.webapp.storage;

import com.urise.webapp.NamesGenerator;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {
    public AbstractArrayStorageTest(AbstractArrayStorage storage) {
        super(storage);
    }

    @Test//(expected = StorageException.class)
    public void arrayOverflow() {
        assertThrows(StorageException.class, () -> {
            storage.clear();
            try {
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume(Integer.toString(i), NamesGenerator.getName(0)));
                }
            } catch (StorageException e) {
                fail("Overflow before 10000");
            }
            storage.save(new Resume("aaa")); //overflow
        });
    }


}