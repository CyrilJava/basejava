package com.urise.webapp;

public class MainDeadlock {
    public static final Object OBJECT_1 = new Object();
    public static final Object OBJECT_2 = new Object();

    static void main() {
        Thread threadA = new Thread(() -> {
            synchronized (OBJECT_1) {
                System.out.println("Поток A заблокировал объект 1");
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Ждём объект 2");
                synchronized (OBJECT_2) {
                    System.out.println("Поток A заблокировал оба объекта");
                }
            }
        });
        Thread threadB = new Thread(() -> {
            synchronized (OBJECT_2) {
                System.out.println("Поток B заблокировал объект 2");
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Ждём объект 1");
                synchronized (OBJECT_1) {
                    System.out.println("Поток B заблокировал оба объекта");
                }
            }
        });
        threadA.start();
        threadB.start();
    }
}
