package com.urise.webapp.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.urise.webapp.util.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 4270071768600374419L;
    protected String name;
    protected String webSite;
    protected List<CompanyPeriod> periodList;

    public Company() {
    }

    // конструктор с передачей множества периодов
    public Company(String name, String webSite, CompanyPeriod... companyPeriods) {
        new Company(name, webSite, Arrays.asList(companyPeriods));
    }

    public Company(String name, String webSite, List<CompanyPeriod> periodList) {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(webSite, "webSite must not be null");
        Objects.requireNonNull(periodList, "periodList must not be null");
        this.name = name;
        this.webSite = webSite;
        this.periodList = periodList;
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
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) &&
                Objects.equals(webSite, company.webSite) &&
                Objects.equals(periodList, company.periodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, webSite, periodList);
    }

    @Override
    public String toString() {
        return String.format("%-30s %-40s\n     %-40s\n", name, webSite,
                periodList.stream().map(Object::toString).collect(Collectors.joining("\n     ")));
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class CompanyPeriod implements Serializable {
        @Serial
        private static final long serialVersionUID = 4270071768600374419L;
        private String title;
        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;

        public CompanyPeriod() {
        }
        public CompanyPeriod(String title, String description, LocalDate startDate, LocalDate endDate) {
            Objects.requireNonNull(title, "title must not be null");
            Objects.requireNonNull(startDate, "startDate must not be null");
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public String toString() {
            return String.format("%tm.%tY - %tm.%tY %-40s %-30s",
                    startDate, startDate, endDate, endDate, title, description);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            CompanyPeriod that = (CompanyPeriod) o;
            return Objects.equals(title, that.title) && Objects.equals(description, that.description) &&
                    Objects.equals(startDate, that.startDate) && Objects.equals(endDate, that.endDate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, description, startDate, endDate);
        }
    }
}
