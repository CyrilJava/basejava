package com.urise.webapp.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;

    private final String fullName;
    /*private static final String[] names = {"Иванов Александр", "Смирнова Мария", "Кузнецов Дмитрий", "Попова Анна", "Васильев Михаил", "Петрова Екатерина", "Соколов Иван", "Михайлова Ольга", "Новиков Сергей", "Федорова Наталья", "Морозов Андрей", "Волкова Елена", "Алексеев Павел", "Лебедева Татьяна", "Семенов Алексей", "Егорова Ирина", "Павлов Николай", "Козлова Светлана", "Голубев Владимир", "Виноградова Юлия", "Кириллов Артем", "Орлова Анастасия", "Макаров Виктор", "Захарова Оксана", "Зайцев Константин", "Романова Ксения", "Тимофеев Роман", "Фомина Вероника", "Богданов Евгений", "Власова Ангелина", "Савельев Григорий", "Никитина Алёна", "Григорьев Станислав", "Цветкова Валерия", "Рыбаков Даниил", "Данилова Полина", "Маслов Никита", "Жукова Дарья", "Тарасов Игорь", "Белова Василиса", "Белов Тимур", "Кудрявцева Ульяна", "Баранов Святослав", "Архипова Виктория", "Филиппов Вадим", "Степанова Марина", "Комаров Юрий", "Борисова Людмила", "Дмитриев Антон", "Маркова Галина", "Ефимов Георгий", "Горбунова Злата", "Щербаков Фёдор", "Антипова Лидия", "Поляков Максим", "Титова Валентина", "Карпов Руслан", "Медведева Эльвира", "Шестаков Платон", "Калинина Инна", "Осипов Всеволод", "Назарова Раиса", "Матвеев Марк", "Силина Кира", "Вишняков Валерий", "Самсонова Регина", "Герасимов Семён", "Логинова Эмилия", "Трофимов Арсений", "Зимина Лилия", "Ушаков Денис", "Крылова Арина", "Денисов Лев", "Терентьева Алла", "Жуков Ян", "Комарова Нелли", "Воробьев Эдуард", "Носкова Римма", "Лазарев Илья", "Турова Клавдия", "Ершов Тимофей", "Клименко Софья", "Тихонов Петр", "Федосеева Снежана", "Афанасьев Мирон", "Молчанова Изольда", "Власов Матвей", "Игнатьева Вероника", "Давыдов Леонид", "Кузьмина Тамара", "Гусев Захар", "Королева Жанна", "Наумов Олег", "Евдокимова Нонна", "Ширяев Мирослав", "Родионова Эвелина", "Силин Гордей", "Суханова Диана", "Михеев Борис", "Зыкова Лариса"};

    public void setRandomName() {
        Random rnd = new Random();
        this.setFullName(Resume.names[rnd.nextInt(100)]);
    }*/

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "Uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    /*public void setFullName(String fullName) {
        this.fullName = fullName;
    }*/

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