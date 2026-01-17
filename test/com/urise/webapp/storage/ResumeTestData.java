package com.urise.webapp.storage;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
    PERSONAL("Личные"),
    OBJECTIVE("Позиция"),
    ACHIEVEMENT("Достижения"),
    QUALIFICATIONS("Квалификация"),
    EXPIRIENCE("Опыт работы"),
    EDUCATION("Образование");
 */

public class ResumeTestData {

    public static void main(String[] args) throws ParseException {
        Resume r = new Resume("1", "Кислин Григорий");

        r.addContact(ContactType.PHONE, "+7(921) 855-0482");
        r.addContact(ContactType.EMAIL, "gkislin@yandex.ru");
        r.addContact(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        r.addContact(ContactType.WEBSITE, "http://gkislin.ru/");
        r.addContact(ContactType.GITHUB, "https://github.com/gkislin");

        r.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));
        r.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        List<String> achievList = new ArrayList<>();
        achievList.add("Организация команды и успешная реализация Java проектов для сторонних заказчиков: приложения автопарк на стеке Spring Cloud/микросервисы, система мониторинга показателей спортсменов на Spring Boot, участие в проекте МЭШ на Play-2, многомодульный Spring Boot + Vaadin проект для комплексных DIY смет");
        achievList.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 3500 выпускников.");
        achievList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievList.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievList.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievList.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievList.add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        ListSection achievement = new ListSection(achievList);
        r.addSection(SectionType.ACHIEVEMENT, achievement);

        List<String> qualList = new ArrayList<>();
        qualList.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualList.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualList.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle, MySQL, SQLite, MS SQL, HSQLDB");
        qualList.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        qualList.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        qualList.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualList.add("Python: Django.");
        qualList.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualList.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualList.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualList.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        qualList.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer");
        qualList.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных шаблонов, UML, функционального программирования");
        qualList.add("Родной русский, английский \"upper intermediate\"");
        ListSection qualifications = new ListSection(qualList);
        r.addSection(SectionType.QUALIFICATIONS, qualifications);

        List<Company> companyList = new ArrayList<>();
        companyList.add(createCompany("Java Online Projects", "http://javaops.ru/", "Автор проекта", "Создание, организация и проведение Java онлайн проектов и стажировок.",
                2014, 10, LocalDate.now().getYear(), LocalDate.now().getMonthValue()));
        companyList.add(createCompany("Wrike", "https://www.wrike.com/", "Старший разработчик (backend)", "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.",
                2013, 10, 2016, 1));
        CompanySection experience = new CompanySection(companyList);
        r.addSection(SectionType.EXPIRIENCE, experience);

        List<Company> eduList = new ArrayList<>();
        eduList.add(createCompany("Coursera", "https://www.coursera.org/course/progfun", "Functional Programming Principles in Scala' by Martin Odersky", "",
                2013, 3, 2013, 5));
        eduList.add(createCompany("Luxoft", "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366", "Курс 'Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.'", "",
                2011, 3, 2011, 4));
        CompanySection education = new CompanySection(eduList);
        r.addSection(SectionType.EDUCATION, education);
/*
        CompanyPeriod сourseraPeriod = new CompanyPeriod("Functional Programming Principles in Scala' by Martin Odersky", "", new SimpleDateFormat( "dd.MM.yyyy" ).parse( "01.03.2013" ), new SimpleDateFormat( "dd.MM.yyyy" ).parse( "01.05.2013" ));
        List<CompanyPeriod> сourseraPeriodList = new ArrayList<>();
        сourseraPeriodList.add(сourseraPeriod);
        Company сoursera = new Company("Coursera", "https://www.coursera.org/course/progfun", сourseraPeriodList);
        List<Company> eduList = new ArrayList<>();
        eduList.add(сoursera);
        CompanySection education = new CompanySection(eduList);
        r.addSection(SectionType.EDUCATION, education);
*/

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
        return new Company(companyName, webSite, new ArrayList<CompanyPeriod>() {{
            add(new CompanyPeriod(periodTitle, periodDescription, DateUtil.of(yearSt, monthSt), DateUtil.of(yearEnd, monthEnd)));
        }});
    }
}
