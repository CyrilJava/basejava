package com.urise.webapp.model;

public enum ContactType {
    PHONE("Телефон"),
    EMAIL("Электронная почта") {
        @Override
        public String toHtml0(String value) {
            return "<a href='mailto:" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    WEBSITE("Домашняя страница"),
    GITHUB("Профиль GitHub");

    public String getTitle() {
        return title;
    }

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }
}

