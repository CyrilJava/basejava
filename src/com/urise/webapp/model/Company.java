package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Company implements Serializable {
    private static final long serialVersionUID = 4270071768600374419L;
    protected String name;
    protected String webSite;
    protected List<CompanyPeriod> periodList;

    public Company(String name, String webSite, List<CompanyPeriod> periodList) {
        this.name = name;
        this.webSite = webSite;
        this.periodList = periodList;
    }

    public Company(String name, String webSite, CompanyPeriod... companyPeriods) {//конструктор с передачей множества периодов
        this.name = name;
        this.webSite = webSite;
        this.periodList = Arrays.asList(companyPeriods);
    }

    public String getName() {
        return name;
    }

    public String getWebSite() {
        return webSite;
    }

    public List<CompanyPeriod> getPeriodList() {
        return periodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) && Objects.equals(webSite, company.webSite) && Objects.equals(periodList, company.periodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, webSite, periodList);
    }

    @Override
    public String toString() {
        /*System.out.printf("%-30s %-40s %tm.%tY - %tm.%tY %-40s %-30s\n",
                company.name, company.webSite, companyPeriod.startDate, companyPeriod.startDate, companyPeriod.endDate, companyPeriod.endDate, companyPeriod.title, companyPeriod.description);*/
        //String result = ;
        return String.format("%-30s %-40s\n     %-40s\n", name, webSite, periodList.stream().map(Object::toString).collect(Collectors.joining("\n     ")));
    }

    public static class CompanyPeriod implements Serializable {
        private static final long serialVersionUID = 4270071768600374419L;
        private final String title;
        private final String description;
        private final LocalDate startDate;
        private final LocalDate endDate;

        public CompanyPeriod(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompanyPeriod that = (CompanyPeriod) o;
            return Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, description, startDate, endDate);
        }

        @Override
        public String toString() {
            return String.format("%tm.%tY - %tm.%tY %-40s %-30s", startDate,  startDate, endDate,endDate, title, description);
        }


    }
}
