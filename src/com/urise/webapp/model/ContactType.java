package com.urise.webapp.model;

public enum ContactType {
    PHONE("Номер телефона"),
    SKYPE("Скайп"),
    MAIL("Почта"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль Stackoverflow"),
    HOME("Домашняя страница");

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
