package com.urise.webapp;

import com.urise.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static Resume createNewResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        Map<SectionType, AbstractSection> mapSections = new EnumMap<SectionType, AbstractSection>(SectionType.class);
        Map<ContactType, String> mapContacts = new EnumMap<ContactType, String>(ContactType.class);

        List<String> achievements = new ArrayList<>();
        List<String> qualifications = new ArrayList<>();

        List<Organization> organizations = new ArrayList<>();
        List<Organization.Period> periods = new ArrayList<>();

        List<Organization> organizationsOfEducation = new ArrayList<>();
        List<Organization.Period> periodsOfEducation = new ArrayList<>();

        //list of achievements
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, \n" +
                "система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. \n XML (JAXB/StAX). Веб сервисы " +
                "(JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        //list of qualifications
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, \n MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink)," +
                " Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet," +
                " HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        //list of organizations
        periods.add(new Organization.Period("Автор проекта", 2013, Month.of(10), "Создание, организация и проведение Java онлайн проектов и стажировок."));
        organizations.add(new Organization("Java Online Projects", "javaops.ru", periods));
        //list of organizationsOfEducation
        periodsOfEducation.add(new Organization.Period("Закончил с отличием",1984, Month.of(9), 1987, Month.of(6), ""));
        organizationsOfEducation.add(new Organization("Заочная физико-техническая школа при МФТИ", "school.mipt.ru", periodsOfEducation));
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
        mapSections.put(SectionType.EDUCATION, new OrganizationSection(organizationsOfEducation));

        resume.setContacts(mapContacts);
        resume.setSections(mapSections);
        // Print
        System.out.println(resume);
        for (Map.Entry<ContactType, String> entry : mapContacts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
//        for (Map.Entry<SectionType, AbstractSection> entry : mapSections.entrySet()) {
//            System.out.println(entry.getKey() + ": " + entry.getValue());
//        }

        return resume;
    }

    public static void main(String[] args) {
        createNewResume("1s", "Kislin");
    }
}
