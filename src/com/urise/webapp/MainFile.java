package com.urise.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * gkislin
 * 21.07.2016
 */
public class MainFile {
    public static void main(String[] args) throws IOException {
        String filePath = ".\\.gitignore";
        String path = "../basejava/src/com/urise/webapp";
        File file = new File(filePath);
        try {
            System.out.println("1. " + file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }

        File dir = new File("./src/com/urise/webapp");
        System.out.println("2. " + dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
                System.out.println(name);
            }
        }

        try (FileInputStream fis = new FileInputStream(filePath)) {
            System.out.println("4. " + fis.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println();
        System.out.println("--- 5. Walk - вывод всех файлов проекта ---");
        try (Stream<Path> stream = Files.walk(Paths.get(path))) {
            stream.filter(Files::isRegularFile)
                    .forEach(System.out::println);// полное имя
                    //.forEach(x -> System.out.println(x.getFileName()));// только имя файла
        }
        /*
        System.out.println("--- 6. ---");
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file2 : files) {
                System.out.println(file2.getName());
            }
        }*/
        System.out.println();
        System.out.println("--- 6. Tree - рекурсивный обход и вывод имени файлов в каталогах и подкаталогах---");
        listDirectory(path, 0);
    }

    //выводим дерево
    public static void listDirectory(String path, int level) {
        File dir = new File(path);
        File[] firstLevelFiles = dir.listFiles();
        if ((firstLevelFiles != null) && (firstLevelFiles.length > 0)) {
            for (File file : firstLevelFiles) {
                for (int i = 0; i < level; i++) {
                    System.out.print("\t");
                }
                if (file.isDirectory()) {
                    System.out.println("╚ " + file.getName());
                    listDirectory(file.getAbsolutePath(), level + 1);
                } else {
                    System.out.println("└ " + file.getName());
                }
            }
        }
    }
}