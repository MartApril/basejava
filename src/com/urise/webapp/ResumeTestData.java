package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    private static final Resume RESUME = new Resume("Kislin");

    public static void main(String[] args) {
        Map<SectionType, AbstractSection> mapSections = new HashMap<>();
        Map<ContactType, String> mapContacts = new HashMap<>();
        //list of achievements
        List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, \n" +
                "система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. \n XML (JAXB/StAX). Веб сервисы " +
                "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        //list of qualifications
        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, \n MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        //list of organizations
        List<Organization> organizations = new ArrayList<>();
        organizations.add(new Organization("Java Online Projects", "javaops.ru",
                new Organization.Period("Автор проекта", LocalDate.of(2013, 10, 1),
                        LocalDate.now(), "Создание, организация и проведение Java онлайн проектов и стажировок.")));
        //fill map of contacts
        mapContacts.put(ContactType.PHONE, "+7(921) 855-0482");
        mapContacts.put(ContactType.SKYPE, "grigory.kislin");
        mapContacts.put(ContactType.MAIL, "gkislin@yandex.ru");
        mapContacts.put(ContactType.LINKEDIN, "gkislin");
        mapContacts.put(ContactType.GITHUB, "gkislin");
        mapContacts.put(ContactType.STACKOVERFLOW, "grigory-kislin");
        mapContacts.put(ContactType.HOME, "gkislin.ru");
        //fill map of sections
        mapSections.put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        mapSections.put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        mapSections.put(SectionType.ACHIEVEMENT, new ListSection(achievements));
        mapSections.put(SectionType.QUALIFICATIONS, new ListSection(qualifications));
        mapSections.put(SectionType.EXPERIENCE, new OrganizationSection(organizations));

        RESUME.setContacts(mapContacts);
        RESUME.setSections(mapSections);
        // Print
        System.out.println(RESUME);
        for (Map.Entry<ContactType, String> entry : mapContacts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        for (Map.Entry<SectionType, AbstractSection> entry : mapSections.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
