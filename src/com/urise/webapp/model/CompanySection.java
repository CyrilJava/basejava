package com.urise.webapp.model;

import java.util.List;
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



}
