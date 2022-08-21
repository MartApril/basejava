package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MainFile {
public static void readAllFiles (File fileOrDirectory) {
    File[] arrayOfFiles = fileOrDirectory.listFiles();
    for (File file : arrayOfFiles) {
        if (file.isFile()) {
            System.out.println(file.getName()+ " " + file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            readAllFiles(file);
        }
    }
}

    public static void main(String[] args) {
        String filePath = ".\\.gitignore";

        File file = new File(filePath);
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("C:\\Users\\Anna\\IdeaProjects\\basejava");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println(fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        readAllFiles(dir);
    }
}
