package com.urise.webapp;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;
import com.urise.webapp.util.TransliterateUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume r = new Resume("1", "Кислин Григорий");

        r.addContact(ContactType.PHONE, "+7(921) 855-0482");
        r.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        r.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r.addContact(ContactType.WEBSITE, "http://gkislin.ru/");
        r.addContact(ContactType.GITHUB, "https://github.com/gkislin");

        r.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievements = new ArrayList<>();
        achievements.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        ListSection achievement = new ListSection(achievements);
        r.addSection(SectionType.ACHIEVEMENT, achievement);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        ListSection qualification = new ListSection(qualifications);
        r.addSection(SectionType.QUALIFICATIONS, qualification);

        List<Company> companies = new ArrayList<>();
        companies.add(createCompany("Java Online Projects", "http://javaops.ru/", "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                2014, 10, LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        companies.add(createCompany("Wrike", "https://www.wrike.com/", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                2013, 10, 2016, 1));
        r.addSection(SectionType.EXPERIENCE, new CompanySection(companies));

        List<Company> educations = new ArrayList<>();
        educations.add(createCompany("Coursera", "https://www.coursera.org/course/progfun", "Functional Programming Principles in Scala' by Martin Odersky", "",
                2013, 3, 2013, 5));
        r.addSection(SectionType.EDUCATION, new CompanySection(educations));

        educations.add(new Company("ИТМО", "http://www.ifmo.ru/", new ArrayList<Company.CompanyPeriod>(){{
            add(new Company.CompanyPeriod("Преподаватель", "", DateUtil.of(1996, 9), DateUtil.of(1997, 6)));
            add(new Company.CompanyPeriod("Аспирантура (программист С, С++)", "", DateUtil.of(1993, 9), DateUtil.of(1996, 7)));
            add(new Company.CompanyPeriod("Инженер (программист Fortran, C)", "", DateUtil.of(1987, 9), DateUtil.of(1993, 7)));
        }}));
        educations.add(createCompany("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", "",
                2011, 3, 2011, 4));


        System.out.printf("Идентификатор: %s\n\n", r.getUuid()); //выводим UUiD
        System.out.printf("\u001B[33m%s\n\n\u001B[0m", r.getFullName()); //выводим имя
        for (Map.Entry<ContactType, String> entry : r.getContacts().entrySet()) {//выводим контакты
            System.out.print("\u001B[32m" + entry.getKey().getTitle() + ": \u001B[0m");
            System.out.println(entry.getValue());
        }
        System.out.println();
        for (Map.Entry<SectionType, AbstractSection> entry : r.getSections().entrySet()) {
            System.out.println("\u001B[34m" + entry.getKey().getTitle() + ":\u001B[0m");//выводим наименование секции
            //entry.getValue().printSection();//выводим данные секции
            System.out.println(entry.getValue().toString());
            System.out.println();
        }
    }

    private static Company createCompany(String companyName, String webSite, String periodTitle, String periodDescription, int yearSt, int monthSt, int yearEnd, int monthEnd) {
        return new Company(companyName, webSite, new ArrayList<Company.CompanyPeriod>() {{
            add(new Company.CompanyPeriod(periodTitle, periodDescription, DateUtil.of(yearSt, monthSt), DateUtil.of(yearEnd, monthEnd)));
        }});
    }

    public static Resume createTestResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.PHONE, "+7(123)456-7890");
        resume.addContact(ContactType.EMAIL, TransliterateUtil.makeEmail(fullName) + "@basejava.ru");
        resume.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/" + TransliterateUtil.makeEmail(fullName));
        resume.addContact(ContactType.WEBSITE, "http://" + TransliterateUtil.makeEmail(fullName) + ".basejava.ru/");
        resume.addContact(ContactType.GITHUB, "https://github.com/" + TransliterateUtil.makeEmail(fullName));

        resume.addSection(SectionType.OBJECTIVE, new TextSection("Наименование позиции соискателя " + uuid));
        resume.addSection(SectionType.PERSONAL, new TextSection("Список личных качеств соискателя " + uuid));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection(new ArrayList<String>() {{
            add("Достижение 1 соискателя " + uuid);
            add("Достижение 2 соискателя " + uuid);
            add("Достижение 3 соискателя " + uuid);
        }}));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection(new ArrayList<String>() {{
            add("Квалификация 1 соискателя " + uuid);
            add("Квалификация 2 соискателя " + uuid);
            add("Квалификация 3 соискателя " + uuid);
        }}));

        List<Company> companyList = new ArrayList<>();
        companyList.add(createCompany("Фирма 2", "https://www.company2.com/", "Разработчик", "Разработка java",
                2021, 8, LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        companyList.add(createCompany("Фирма 1", "https://www.company1.com/", "Аналитик", "Составление документации",
                2020, 7, 2021, 7));
        CompanySection experience = new CompanySection(companyList);
        resume.addSection(SectionType.EXPERIENCE, experience);

        List<Company> eduList = new ArrayList<>();
        eduList.add(createCompany("IT Курс", "https://www.it_course.com/", "Java Development", "",
                2019, 8, 2020, 5));
        eduList.add(createCompany("МИИИТ", "http://www.mosinstenginftech.ru", "Вычислительные машины, комплексы, системы и сети", "",
                2013, 9, 2019, 6));
        CompanySection education = new CompanySection(eduList);
        resume.addSection(SectionType.EDUCATION, education);

        return resume;
    }
}
