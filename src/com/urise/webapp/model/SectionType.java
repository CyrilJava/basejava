package com.urise.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPIRIENCE("Опыт работы"),
    EDUCATION("Образование");

    public String getTitle() {
        return title;
    }

    private String title;

    SectionType(String title){
        this.title = title;
    }

}
