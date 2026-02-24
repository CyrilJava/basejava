package com.urise.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"), // TextSection
    OBJECTIVE("Позиция"), // TextSection
    ACHIEVEMENT("Достижения"), // ListSection
    QUALIFICATIONS("Квалификация"), // ListSection
    EXPERIENCE("Опыт работы"),  // CompanySection
    EDUCATION("Образование"); // CompanySection

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
