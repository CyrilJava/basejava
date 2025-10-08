package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest {
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
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {
        storage.update(new Resume(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid4"));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] testStorage = storage.getAll();
        Assert.assertEquals(3, testStorage.length);
    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume("uuid4"));
        Assert.assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        storage.save(new Resume(null));
    }
    @Test
    public void arrayFull() throws Exception {
        for (int i=0; i<9997; i++){
            storage.save(new Resume(Integer.toString(i)));
        }
        fail("Overflow before 10000");
    }
    @Test(expected = StorageException.class)
    public void arrayOverflow() throws Exception {
        for (int i=0; i<9998; i++) {
            storage.save(new Resume(Integer.toString(i)));
        }
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete("uuid5");
    }

    @Test
    public void get() throws Exception {
        Assert.assertSame(new Resume(UUID_2).toString(), storage.get(UUID_2).getUuid());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

}