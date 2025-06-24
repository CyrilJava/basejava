package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume();
        r1.setUuid("uuid1");
        Resume r2 = new Resume();
        r2.setUuid("uuid2");
        Resume r3 = new Resume();
        r3.setUuid("uuid3");
        Resume rd = new Resume();
        rd.setUuid("uuidZ");

        ARRAY_STORAGE.save(r1);

        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r2: " + ARRAY_STORAGE.get(r2.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        printAll("Get all");

        ARRAY_STORAGE.update(r3);
        printAll("Update 3");
        ARRAY_STORAGE.delete(r2.getUuid());
        printAll("Delete 2");
        System.out.println("Size: " + ARRAY_STORAGE.size());
        ARRAY_STORAGE.clear();
        printAll("Delete all");

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll(String name) {
        System.out.println(name);
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
        System.out.println();
    }
}
