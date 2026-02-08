package com.urise.webapp;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapResumeStorage;
import com.urise.webapp.storage.Storage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    //private final static Storage ARRAY_STORAGE = new ArrayStorage();
    //private final static Storage ARRAY_STORAGE = new SortedArrayStorage();
    //private final static Storage ARRAY_STORAGE = new ListStorage();
    //private final static Storage ARRAY_STORAGE = new MapUuidStorage();
    private final static Storage ARRAY_STORAGE = new MapResumeStorage();

    public static void main(String[] args) {
        Resume r1 = new Resume("uuid1", NamesGenerator.getName(1));
        Resume r2 = new Resume("uuid2", NamesGenerator.getName(2));
        Resume r3 = new Resume("uuid3", NamesGenerator.getName(3));

        ARRAY_STORAGE.save(r1);
        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);

        System.out.println("Get r2: " + ARRAY_STORAGE.get(r2.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
        //System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll("Get all");

        //r3.setUuid("uuid3_upd");
        //ARRAY_STORAGE.update(rd);
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
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
        System.out.println();
    }
}
