package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class MainFile {
    protected static final Comparator<File> FILE_COMPARATOR = Comparator.comparing(File::isDirectory);

    public static void readAllFiles(File fileOrDirectory) {
        File[] arrayOfFiles = fileOrDirectory.listFiles();
        System.out.println("Directory: " + fileOrDirectory.getName());
        if (arrayOfFiles != null) {
            Arrays.sort(arrayOfFiles, FILE_COMPARATOR);
            for (File file : arrayOfFiles) {
                if (file.isFile()) {
                    System.out.println("File: " + file.getName());
                } else if (file.isDirectory()) {
                    readAllFiles(file);
                }
            }
    }

//        File[] arrayOfFiles = fileOrDirectory.listFiles();
////        System.out.println("Directory: " + fileOrDirectory.getName());
//        if (arrayOfFiles != null) {
//            Arrays.sort(arrayOfFiles, FILE_COMPARATOR);
//            for (File file : arrayOfFiles) {
//                int i = 0;
//                while (i < tab) {
//                    i++;
//                    System.out.print("\t");
//                }
//                if (file.isFile()) {
//                    System.out.println("File: " + file.getName());
//                }
//                else    if (file.isDirectory()) {
//                    System.out.println(parent+ file.getParent());
//                    if (parent.equals(file.getParent())){
//                        readAllFiles(file, tab);
//                    }
//                    else {
//                        tab++;
//                        readAllFiles(file, tab);
//                    }
//                }
//            }
//        }
}

    public static void main(String[] args) {
//        String filePath = ".\\.gitignore";
//
//        File file = new File(filePath);
//        try {
//            System.out.println(file.getCanonicalPath());
//        } catch (IOException e) {
//            throw new RuntimeException("Error", e);
//        }

        File dir = new File("C:\\Users\\Anna\\IdeaProjects\\basejava");
//        System.out.println(dir.isDirectory());
//        String[] list = dir.list();
//        if (list != null) {
//            for (String name : list) {
//                System.out.println(name);
//            }
//        }
//
//        try (FileInputStream fis = new FileInputStream(filePath)) {
//            System.out.println(fis.read());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

        readAllFiles(dir);
    }
}
