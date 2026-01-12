package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Электронная почта"),
    LINKEDIN("Профиль LinkedIn"),
    WEBSITE("Домашняя страница"),
    GITHUB("Профиль GitHub");

    public String getTitle() {
        return title;
    }

    private String title;

    ContactType(String title){
        this.title = title;
    }
}

