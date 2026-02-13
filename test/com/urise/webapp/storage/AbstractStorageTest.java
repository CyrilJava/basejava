package com.urise.webapp.storage;

import com.urise.webapp.NamesGenerator;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.urise.webapp.ResumeTestData.createTestResume;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected final Storage storage;
    protected static final File STORAGE_DIR = new File(".\\storage");
    private static final String UUID_1 = "uuid1";
    private static final Resume R1 = createTestResume(UUID_1, NamesGenerator.getName(1));
    private static final String UUID_2 = "uuid2";
    private static final Resume R2 = createTestResume(UUID_2, NamesGenerator.getName(2));
    private static final String UUID_3 = "uuid3";
    private static final Resume R3 = createTestResume(UUID_3, NamesGenerator.getName(3));
    private static final String UUID_4 = "uuid4";
    private static final Resume R4 = createTestResume(UUID_4, NamesGenerator.getName(4));

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume r3U = createTestResume(UUID_3, NamesGenerator.getName(3));
        storage.update(r3U);
        assertGet(r3U);
    }

    @Test
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () ->
                storage.update(new Resume(UUID_4, NamesGenerator.getName(0))));
    }

    @Test
    public void getAllSorted() {
        List<Resume> expectedList = Arrays.asList(R1, R2, R3);
        expectedList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        assertEquals(expectedList, storage.getAllSorted());
    }

    @Test
    public void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test
    public void saveExist() {
        assertThrows(ExistStorageException.class, () ->
            storage.save(new Resume(UUID_3, NamesGenerator.getName(3))));
    }

    @Test
    public void saveUuidNull() {
        assertThrows(NullPointerException.class, () -> storage.save(new Resume(null, "a")));
    }

    @Test
    public void saveFullNameNull() {
        assertThrows(NullPointerException.class, () -> storage.save(new Resume("uuid99", null)));
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_2));
    }

    @Test
    public void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_4));
    }
    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }
}