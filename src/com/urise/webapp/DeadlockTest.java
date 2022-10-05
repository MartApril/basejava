package com.urise.webapp;

public class DeadlockTest {
    private static final String MONEY = "money";
    private static final String ICE_CREAM = "ice cream";
    private static final String BUYER = "Buyer";
    private static final String ICEMAN = "Ice Man";

    public static void main(String[] args) {
        iceCreamDeal(BUYER, MONEY, ICE_CREAM);
        iceCreamDeal(ICEMAN, ICE_CREAM, MONEY);
    }

    static void iceCreamDeal(String name, String keepingThing, String waitingThing) {
        new Thread(() -> {
            synchronized (keepingThing) {
                System.out.println(name + " is keeping " + keepingThing);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " is waiting for " + waitingThing);
                synchronized (waitingThing) {
                    System.out.println(name + " has got " + waitingThing);
                }
            }
        }).start();
    }
}