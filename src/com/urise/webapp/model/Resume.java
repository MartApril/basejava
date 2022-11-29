package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private String uuid;
    private String fullName;

    public Resume() {
    }

    private Map<SectionType, AbstractSection> sections = new EnumMap<SectionType, AbstractSection>(SectionType.class);
    private Map<ContactType, String> contacts = new EnumMap<ContactType, String>(ContactType.class);

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }
    public void addContact(ContactType type, String value) {
        contacts.put(type, value);
    }
    public void addSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public void setSections(Map<SectionType, AbstractSection> sections) {
        this.sections = sections;
    }

    public void setContacts(Map<ContactType, String> contacts) {
        this.contacts = contacts;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullname must not be null");
    }

    public String getUuid() {
        return uuid;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(sections, resume.sections) &&
                Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections, contacts);
    }

    @Override
    public String toString() {
        return "Resume uuid: " + uuid + '\n' + fullName;
    }
}
