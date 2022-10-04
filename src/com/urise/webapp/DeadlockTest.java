package com.urise.webapp;

public class DeadlockTest {
    public static final Object MONEY = new Object();
    public static final Object ICE_CREAM = new Object();

    public static void main(String[] args) {
        Thread thread0 = new Thread(() -> {
            Person person1 = new Person("Buyer");
            synchronized (MONEY) {
                System.out.println(person1.getName() + " is keeping his money");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(person1.getName() + " is waiting for ice cream");

                synchronized (ICE_CREAM) {
                    System.out.println(person1.getName() + " got ice cream");
                }
            }
        });
        thread0.start();

        Thread thread1 = new Thread(() -> {
            Person person2 = new Person("IceMan");
            synchronized (ICE_CREAM) {
                System.out.println(person2.getName() + " is keeping ice cream");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(person2.getName() + " is waiting for money");

                synchronized (MONEY) {
                    System.out.println(person2.getName() + " got money");
                }
            }
        });
        thread1.start();
    }
}

class Person {
    String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}