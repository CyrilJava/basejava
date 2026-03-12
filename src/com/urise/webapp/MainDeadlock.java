package com.urise.webapp;

public class MainDeadlock {
    public static final Object OBJECT_1 = new Object();
    public static final Object OBJECT_2 = new Object();

    static void threadLock(Object object1, Object object2, Character character) {
        int a1;
        int a2;
        if (object2.toString().compareTo(object1.toString()) > 0) {
            a2 = 2;
            a1 = 1;
        } else {
            a1 = 2;
            a2 = 1;
        }
        synchronized (object1) {
            System.out.println("Поток " + character + " заблокировал объект " + a1);
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Ждём объект " + a2);
            synchronized (object2) {
                System.out.println("Поток " + character + " заблокировал оба объекта");
            }
        }
    }

    static void main() {
        Thread threadA = new Thread(() -> {
            threadLock(OBJECT_1, OBJECT_2, 'A');
        });
        Thread threadB = new Thread(() -> {
            threadLock(OBJECT_2, OBJECT_1, 'B');
        });
        threadA.start();
        threadB.start();
    }
}
