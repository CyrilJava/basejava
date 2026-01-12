package com.urise.webapp.model;

import java.util.Date;
import java.util.Objects;

public class CompanyPeriod {
    protected String title;
    protected String description;
    protected Date startDate;
    protected Date endDate;

    public CompanyPeriod(String title, String description, Date startDate, Date endDate) {
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
