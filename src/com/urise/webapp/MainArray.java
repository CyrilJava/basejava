package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.Storage;
import com.urise.webapp.storage.serializer.DataStreamSerializer;
import com.urise.webapp.storage.serializer.JsonStreamSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import static com.urise.webapp.ResumeTestData.createTestResume;
/**
 * Interactive test for ArrayStorage implementation.
 * (just run, no need to understand)
 */
public class MainArray {
    private static final String dir = ".\\mainstorage";
    // private static final Storage ARRAY_STORAGE = new ListStorage();
    // private static final Storage ARRAY_STORAGE = new MapResumeStorage();
    // private static final Storage ARRAY_STORAGE = new MapUuidStorage();
    // private static final Storage ARRAY_STORAGE = new ArrayStorage();
    // private static final Storage ARRAY_STORAGE = new SortedArrayStorage();
    // private static final Storage ARRAY_STORAGE = new FileStorage(new File(".\\mainstorage"));
    // private static final Storage ARRAY_STORAGE = new PathStorage(dir, new Strategy());
    // private static final Storage ARRAY_STORAGE = new PathStorage(dir, new XmlStreamSerializer());
    private static final Storage ARRAY_STORAGE = new PathStorage(dir, new JsonStreamSerializer());
    // private static final Storage ARRAY_STORAGE = new PathStorage(dir, new DataStreamSerializer());

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume r;
        while (true) {
            System.out.print("Введите одну из команд - " +
                    "(list | size | save uuid | delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length > 2) {
                System.out.println("Неверная команда.");
                continue;
            }
            String uuid = null;
            if (params.length == 2) {
                uuid = params[1].intern();
            }
            switch (params[0]) {
                case "l":
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "s":
                case "save":
                    // r = new Resume(uuid,NamesGenerator.getName(0));
                    r = createTestResume(uuid, NamesGenerator.getName(0));
                    ARRAY_STORAGE.save(r);
                    printAll();
                    break;
                case "u":
                case "update":
                    r = new Resume(uuid, NamesGenerator.getName(0));
                    ARRAY_STORAGE.update(r);
                    printAll();
                    break;
                case "d":
                case "delete":
                    ARRAY_STORAGE.delete(uuid);
                    printAll();
                    break;
                case "g":
                case "get":
                    System.out.println(ARRAY_STORAGE.get(uuid));
                    break;
                case "c":
                case "clear":
                    ARRAY_STORAGE.clear();
                    printAll();
                    break;
                case "":
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> all = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        System.out.println(" uuid Name                  ");
        if (all.isEmpty()) {
            System.out.println("Empty");
        } else {
            for (Resume r : all) {
                System.out.printf("%5s %-20s %30s\n",
                        r.getUuid(), r.getFullName(), r.getContacts().get(ContactType.EMAIL));
            }
        }
        System.out.println("----------------------------");
    }
}
