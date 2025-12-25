package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume R1 = new Resume(UUID_1, NamesGenerator.getName(1));
    private static final String UUID_2 = "uuid2";
    private static final Resume R2 = new Resume(UUID_2, NamesGenerator.getName(2));
    private static final String UUID_3 = "uuid3";
    private static final Resume R3 = new Resume(UUID_3, NamesGenerator.getName(3));
    private static final String UUID_4 = "uuid4";
    private static final Resume R4 = new Resume(UUID_4, NamesGenerator.getName(0));

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
        Resume R3U = new Resume(UUID_3, NamesGenerator.getName(3));
        storage.update(R3U);
        Assert.assertSame(storage.get(UUID_3), R3U);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("uuid4", NamesGenerator.getName(0)));
    }

/*    @Test
    public void getAll() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(R1, R2, R3));
    }*/

    @Test
    public void getAllSorted() throws Exception {
        //старая реализация
        //Resume[] expectedStorage = {R1, R2, R3};
        //assertEquals(storage.getAllSorted(), Arrays.asList(expectedStorage));
        List<Resume> expectedList = new ArrayList<>();
        expectedList.add(R1);
        expectedList.add(R2);
        expectedList.add(R3);
        expectedList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        assertEquals(storage.getAllSorted(), expectedList);
    }

    @Test
    public void save() throws Exception {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_3, NamesGenerator.getName(3)));
    }

    @Test(expected = NullPointerException.class)
    public void saveUuidNull() throws Exception {
        storage.save(new Resume(null, "a"));
    }

    @Test(expected = NullPointerException.class)
    public void saveFullNameNull() throws Exception {
        storage.save(new Resume("uuid99", null));
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
