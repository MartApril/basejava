package com.urise.webapp.util;

public class LazySingleton {
    int i;
    volatile private static LazySingleton INSTANCE;
    double sin = Math.sin(12.);

    private LazySingleton() {
    }

    public static LazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (LazySingleton.class) {
                if (INSTANCE == null) {
                    int i = 12;
                    INSTANCE = new LazySingleton();
                }
            }

        }
        return INSTANCE;
    }
}
