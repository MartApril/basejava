package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import com.urise.webapp.model.Organization.Period;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dis = new DataInputStream(inputStream)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(dis, () -> {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            });
            //read sections
            readWithException(dis, () -> {
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
                        readWithException(dis, () -> {
                            stringList.add(dis.readUTF());
                        });
                        abstractSection = new ListSection(stringList);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        List<Organization> organizationList = new ArrayList<>();
                        readWithException(dis, () -> {
                            Organization organization = new Organization();
                            organization.setTitle(dis.readUTF());
                            boolean isWebsiteNull = dis.readBoolean();
                            if (!isWebsiteNull) {
                                organization.setWebsite(dis.readUTF());
                            }
                            organization.setPeriods(readListWithException(dis, () -> {
                                        Period period = new Period();
                                        period.setTitle(dis.readUTF());
                                        period.setStartDate(LocalDate.parse(dis.readUTF()));
                                        period.setEndDate(LocalDate.parse(dis.readUTF()));
                                        boolean isDescriptionNull = dis.readBoolean();
                                        if (!isDescriptionNull) {
                                            period.setDescription(dis.readUTF());
                                        }
                                        return period;
                                    }
                            ));
                            organizationList.add(organization);
                        });
                        abstractSection = new OrganizationSection(organizationList);
                        break;
                }
                resume.addSection(sectionType, abstractSection);
            });
            return resume;
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(outputStream)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeWithException(dos, resume.getContacts().entrySet(), writer -> {
                dos.writeUTF(writer.getKey().name());
                dos.writeUTF(writer.getValue());
            });
            //write sections
            writeWithException(dos, resume.getSections().entrySet(), writer -> {
                SectionType type = writer.getKey();
                AbstractSection section = writer.getValue();
                dos.writeUTF(type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(dos, ((ListSection) section).getStrings(), dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection organizationSection = (OrganizationSection) section;
                        writeWithException(dos, organizationSection.getOrganizations(), organization -> {
                            dos.writeUTF(organization.getTitle());
                            if (organization.getWebsite() == null) {
                                dos.writeBoolean(true);
                            } else {
                                dos.writeBoolean(false);
                                dos.writeUTF(organization.getWebsite());
                            }
                            writeWithException(dos, organization.getPeriods(), period -> {
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(String.valueOf(period.getStartDate()));
                                dos.writeUTF(String.valueOf(period.getEndDate()));
                                if (period.getDescription() == null) {
                                    dos.writeBoolean(true);
                                } else {
                                    dos.writeBoolean(false);
                                    dos.writeUTF(period.getDescription());
                                }
                            });
                        });
                        break;
                }
            });
        }
    }

    private <T> void writeWithException(DataOutputStream dos, Collection<T> collection, WriteCollection<T> writer) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writer.write(t);
        }
    }

    private void readWithException(DataInputStream dis, ReadCollection reader) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            reader.read();
        }
    }

    private <T> List<T> readListWithException(DataInputStream dis, ListReader<T> list) throws IOException {
        List<T> readerList = new ArrayList<>();
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            readerList.add(list.readList());
        }
        return readerList;
    }

    @FunctionalInterface
    interface WriteCollection<T> {
        void write(T t) throws IOException;
    }

    @FunctionalInterface
    interface ReadCollection {
        void read() throws IOException;
    }
}
