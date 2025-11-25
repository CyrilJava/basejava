package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class AbstractStorageTest {
    public final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume R1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume R2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume R3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume R4 = new Resume(UUID_4);

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assert.assertEquals(resume, storage.get(resume.getUuid()));
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume R3U = new Resume(UUID_3);
        storage.update(R3U);
        Assert.assertSame(storage.get(UUID_3), R3U);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid4"));
    }

    @Test
    public void getAll() throws Exception {
        Resume[] expectedStorage = {R1, R2, R3};
        Assert.assertArrayEquals(storage.getAll(), expectedStorage);
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNull() throws Exception {
        storage.save(new Resume(null));
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

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        try {
            storage.delete(UUID_2);
            assertSize(2);
        } catch (NotExistStorageException e) {
            fail("Not found");
        }
        storage.delete(UUID_2);
    }

    @Test
    public void get() throws Exception {
        //Assert.assertEquals(storage.get(UUID_2), new Resume(UUID_2));
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

}
