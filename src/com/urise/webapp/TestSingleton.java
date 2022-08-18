package com.urise.webapp;

import com.urise.webapp.model.SectionType;

public class TestSingleton {
    private static TestSingleton instance;

    public static TestSingleton getInstance() {
        if (instance==null) {
            instance=new TestSingleton();
        }
        return instance;
    }

    public TestSingleton() {
    }

    public static void main(String[] args) {
        TestSingleton.getInstance().toString();
        Singleton instance = Singleton.valueOf("INSTANCE");
        System.out.println(instance.name());

        for (SectionType type: SectionType.values()) {
            System.out.println(type.name());
        }
    }

    public enum Singleton{
        INSTANCE
    }
}
