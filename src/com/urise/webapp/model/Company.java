package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Company {
    protected String name;
    protected String webSite;
    protected List<CompanyPeriod> periodList;

    public Company(String name, String webSite, List<CompanyPeriod> periodList) {
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

}
