package com.urise.webapp;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.MapStorage;
import com.urise.webapp.storage.Storage;

/**
 * Test for your com.urise.webapp.storage.ArrayStorage implementation
 */
public class MainTestArrayStorage {
    //                private static final Storage ARRAY_STORAGE = new ArrayStorage();
//    private static final Storage ARRAY_STORAGE = new SortedArrayStorage();
//    private static final Storage ARRAY_STORAGE = new ListStorage();
    private static final Storage ARRAY_STORAGE = new MapStorage();

    public static void main(String[] args) {

        Resume r3 = new Resume("uuid3");
        Resume r1 = new Resume("uuid1");
        Resume r2 = new Resume("uuid2");

        ARRAY_STORAGE.save(r2);
        ARRAY_STORAGE.save(r3);
        ARRAY_STORAGE.save(r1);
        try {
            ARRAY_STORAGE.save(r1);
        } catch (ExistStorageException e) {
            System.out.println("Резюме уже существует");
        }
        try {
            System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));
        } catch (NotExistStorageException e) {
            System.out.println("Резюме не существует");
        }

        printAll();
        System.out.println("Get r1: " + ARRAY_STORAGE.get(r1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());
//        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        ARRAY_STORAGE.update(r2);
        ARRAY_STORAGE.update(r1);
        ARRAY_STORAGE.update(r3);

        printAll();
        ARRAY_STORAGE.delete(r2.getUuid());
        printAll();

        ARRAY_STORAGE.clear();
        printAll();
        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
