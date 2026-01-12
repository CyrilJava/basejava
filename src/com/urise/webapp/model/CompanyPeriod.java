package com.urise.webapp.model;

import java.util.Date;
import java.util.Objects;

public class CompanyPeriod {
    protected String Title;
    protected String Description;
    protected Date StartDate;
    protected Date EndDate;

    public CompanyPeriod(String title, String description, Date startDate, Date endDate) {
        Title = title;
        Description = description;
        StartDate = startDate;
        EndDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyPeriod that = (CompanyPeriod) o;
        return Objects.equals(Title, that.Title) && Objects.equals(Description, that.Description) && Objects.equals(StartDate, that.StartDate) && Objects.equals(EndDate, that.EndDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Title, Description, StartDate, EndDate);
    }

}
