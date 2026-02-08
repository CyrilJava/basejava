package com.urise.webapp.storage;

import com.urise.webapp.NamesGenerator;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.urise.webapp.ResumeTestData.createTestResume;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {
    protected final com.urise.webapp.storage.Storage storage;
    protected static final File STORAGE_DIR = new File("D:\\Study\\Java2025\\basejava\\storage");
    private static final String UUID_1 = "uuid1";
    //private static final Resume R1 = new Resume(UUID_1, NamesGenerator.getName(1));
    private static final Resume R1 = createTestResume(UUID_1, NamesGenerator.getName(1));
    private static final String UUID_2 = "uuid2";
    private static final Resume R2 = createTestResume(UUID_2, NamesGenerator.getName(2));
    private static final String UUID_3 = "uuid3";
    private static final Resume R3 = createTestResume(UUID_3, NamesGenerator.getName(3));
    private static final String UUID_4 = "uuid4";
    private static final Resume R4 = createTestResume(UUID_4, NamesGenerator.getName(4));

    public AbstractStorageTest(com.urise.webapp.storage.Storage storage) {
        this.storage = storage;
    }

    private void assertSize(int size) {
        Assertions.assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        Assertions.assertEquals(resume, storage.get(resume.getUuid()));
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
        Resume R3U = createTestResume(UUID_3, NamesGenerator.getName(3));
        storage.update(R3U);
        assertEquals(createTestResume(UUID_3, NamesGenerator.getName(3)), storage.get(UUID_3));
    }

    @Test//(expected = NotExistStorageException.class)
    public void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume("uuid4", NamesGenerator.getName(0)));
        });
    }

    @Test
    public void getAllSorted() {
        //старая реализация
        //Resume[] expectedStorage = {R1, R2, R3};
        //Assertions.assertEquals(storage.getAllSorted(), Arrays.asList(expectedStorage));
        List<Resume> expectedList = new ArrayList<>();
        expectedList.add(R1);
        expectedList.add(R2);
        expectedList.add(R3);
        expectedList.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        Assertions.assertEquals(storage.getAllSorted(), expectedList);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertSize(4);
        assertGet(R4);
    }

    @Test//(expected = ExistStorageException.class)
    public void saveExist() {
        assertThrows(ExistStorageException.class, () -> {
            storage.save(new Resume(UUID_3, NamesGenerator.getName(3)));
        });
    }

    @Test//(expected = NullPointerException.class)
    public void saveUuidNull() {
        assertThrows(NullPointerException.class, () -> {
            storage.save(new Resume(null, "a"));
        });
    }

    @Test//(expected = NullPointerException.class)
    public void saveFullNameNull() {
        assertThrows(NullPointerException.class, () -> {
            storage.save(new Resume("uuid99", null));
        });
    }

    @Test//(expected = NotExistStorageException.class)
    public void delete() {
        assertThrows(NotExistStorageException.class, () -> {
            try {
                storage.delete(UUID_2);
                assertSize(2);
            } catch (NotExistStorageException e) {
                fail("Not found");
            }
            storage.delete(UUID_2);
        });
    }

    @Test
    public void get() {
        assertGet(R1);
        assertGet(R2);
        assertGet(R3);
    }

    @Test//(expected = NotExistStorageException.class)
    public void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> {
            storage.get("dummy");
        });
    }

}
