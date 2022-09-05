package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static com.urise.webapp.model.Organization.Period;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            //read sections
            int sizeOfSections = dis.readInt();
            for (int i = 0; i < sizeOfSections; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                AbstractSection abstractSection = null;
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        abstractSection = new TextSection(dis.readUTF());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        List<String> stringList = new ArrayList<>();
                        int sizeOfStrings = dis.readInt();
                        for (int k = 0; k < sizeOfStrings; k++) {
                            stringList.add(dis.readUTF());
                        }
                        abstractSection = new ListSection(stringList);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = new ArrayList<>();
                        int sizeOfOrganizations = dis.readInt();
                        for (int j = 0; j < sizeOfOrganizations; j++) {
                            Organization organization = new Organization();
                            organization.setTitle(dis.readUTF());
                            organization.setWebsite(dis.readUTF());
                            int sizeOfPeriods = dis.readInt();
                            List<Period> periodList = new ArrayList<>();
                            for (int l = 0; l < sizeOfPeriods; l++) {
                                Period period = new Period();
                                period.setTitle(dis.readUTF());
                                period.setStart(LocalDate.parse(dis.readUTF()));
                                period.setEnd(LocalDate.parse(dis.readUTF()));
                                period.setDescription(dis.readUTF());
                                periodList.add(period);
                            }
                            organization.setPeriods(periodList);
                            organizationList.add(organization);
                        }
                        abstractSection = new OrganizationSection(organizationList);
                        break;
                }
                resume.addSection(sectionType, abstractSection);
            }
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            //write sections
            Map<SectionType, AbstractSection> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                switch (entry.getKey()) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(String.valueOf(entry.getValue()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        ListSection ls = (ListSection) entry.getValue();
                        List<String> stringList = ls.getStrings();
                        dos.writeInt(stringList.size());
                        writeWithException(stringList, dos, collection -> {
                            for (String s : stringList) {
                                dos.writeUTF(s);
                            }
                        });
//                        ListSection ls = (ListSection) entry.getValue();
//                        List<String> stringList = ls.getStrings();
//                        dos.writeInt(stringList.size());
//                        for (String string : stringList) {
//                            dos.writeUTF(string);
//                        }
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection os = (OrganizationSection) entry.getValue();
                        List<Organization> organizationList = os.getOrganizations();
                        dos.writeInt(organizationList.size());
                        writeWithException(organizationList, dos, collection -> {
                            for (Organization organization : organizationList) {
                                dos.writeUTF(organization.getTitle());
                                dos.writeUTF(organization.getWebsite());
                                List<Period> periodList = organization.getPeriods();
                                dos.writeInt(periodList.size());
                                for (Period period : periodList) {
                                    dos.writeUTF(period.getTitle());
                                    dos.writeUTF(String.valueOf(period.getStart()));
                                    dos.writeUTF(String.valueOf(period.getEnd()));
                                    dos.writeUTF(period.getDescription());
                                }
                            }
                        } );

//                        for (Organization organization : organizationList) {
//                            dos.writeUTF(organization.getTitle());
//                            dos.writeUTF(organization.getWebsite());
//                            List<Period> periodList = organization.getPeriods();
//                            dos.writeInt(periodList.size());
//                            for (Period period : periodList) {
//                                dos.writeUTF(period.getTitle());
//                                dos.writeUTF(String.valueOf(period.getStart()));
//                                dos.writeUTF(String.valueOf(period.getEnd()));
//                                dos.writeUTF(period.getDescription());
//                            }
//                        }
                        break;
                }
            }
        }
    }

    private void writeWithException(Collection collection, DataOutputStream dos, Consumer<Collection> collectionConsumer) throws IOException {
        collectionConsumer.writeCollection(collection);
    }

    @FunctionalInterface
    interface Consumer<Collection> {
        void writeCollection(Collection collection) throws IOException;
    }
}
