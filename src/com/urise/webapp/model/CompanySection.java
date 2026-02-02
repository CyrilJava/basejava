package com.urise.webapp.model;

import java.util.List;
import java.util.stream.Collectors;

public class CompanySection extends AbstractSection {
    private static final long serialVersionUID = 4270071768600374419L;
    protected List<Company> companyList;

    public CompanySection(List<Company> companyList) {
        this.companyList = companyList;
    }

    @Override
    public String toString() {
        return companyList.stream().map(Object::toString).collect(Collectors.joining(""));
    }



}
