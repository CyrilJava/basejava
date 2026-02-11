package com.urise.webapp.model;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CompanySection extends AbstractSection {
    private static final long serialVersionUID = 4270071768600374419L;
    protected List<Company> companies;

    public CompanySection(List<Company> companies) {
        this.companies = companies;
    }

    @Override
    public String toString() {
        return companies.stream().map(Object::toString).collect(Collectors.joining(""));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompanySection that = (CompanySection) o;
        return Objects.equals(companies, that.companies);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(companies);
    }
}
