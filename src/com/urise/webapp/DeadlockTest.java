package com.urise.webapp;

public class DeadlockTest {
    private static final String MONEY = "money";
    private static final String ICE_CREAM = "ice cream";

    public static void main(String[] args) {
        Thread buyingIceCream = new Thread(() -> {
            Person person1 = new Person("Buyer");
            synchronized (MONEY) {
                person1.printWaiting(person1.getName(), MONEY, ICE_CREAM);
                synchronized (ICE_CREAM) {
                    person1.printGetting(person1.getName(), ICE_CREAM);
                }
            }
        });
        buyingIceCream.start();

        Thread sellingIceCream = new Thread(() -> {
            Person person2 = new Person("IceMan");
            synchronized (ICE_CREAM) {
                person2.printWaiting(person2.getName(), ICE_CREAM, MONEY);
                synchronized (MONEY) {
                    person2.printGetting(person2.getName(), MONEY);
                }
            }
        });
        sellingIceCream.start();
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

    void printWaiting(String name, String keepingThing, String waitingThing) {
        System.out.println(name + " is keeping " + keepingThing);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + " is waiting for " + waitingThing);
    }

    void printGetting(String name, String gettingThing) {
        System.out.println(name + " got " + gettingThing);
    }
}