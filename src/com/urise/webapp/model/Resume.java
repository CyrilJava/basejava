package com.urise.webapp.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 4270071768600374419L;
    // Unique identifier
    private final String uuid;

    private final String fullName;

    private final Map<ContactType, String> contacts = new EnumMap<ContactType, String>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<SectionType, AbstractSection>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }
    /*for (SectionType type : SectionType.values()) {
        System.out.println(type.name() + " " + type.getTitle());
    }*/

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void addContact(ContactType contactType, String value) {
        contacts.put(contactType, value);
    }

    public void addSection(SectionType sectionType, AbstractSection abstractSection) {
        sections.put(sectionType, abstractSection);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
        /*if (!uuid.equals(resume.uuid)) return false;
        return fullName.equals(resume.fullName);*/
    }

/*    @Override
    public int hashCode() {
        return uuid.hashCode();
    }*/

    @Override
    public String toString() {
        return uuid + " " + fullName;
    }

    @Override
    public int compareTo(Resume o) {
        int cmp = fullName.compareTo(o.fullName);
        return cmp != 0 ? cmp : uuid.compareTo(o.uuid);
    }
}