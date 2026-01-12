package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;

public class Company {
    protected String Name;
    protected String WebSite;
    protected List<CompanyPeriod> PeriodList;

    public Company(String name, String webSite, List<CompanyPeriod> periodList) {
        Name = name;
        WebSite = webSite;
        PeriodList = periodList;
    }

    public String getName() {
        return Name;
    }

    public String getWebSite() {
        return WebSite;
    }

    public List<CompanyPeriod> getPeriodList() {
        return PeriodList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(Name, company.Name) && Objects.equals(WebSite, company.WebSite) && Objects.equals(PeriodList, company.PeriodList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Name, WebSite, PeriodList);
    }
}
