package com.urise.webapp.model;

import java.util.List;
import java.util.stream.Collectors;

public class CompanySection extends AbstractSection {
    protected List<Company> companyList;

    public CompanySection(List<Company> companyList) {
        this.companyList = companyList;
    }

    @Override
    public String toString() {
        return companyList.stream().map(Object::toString).collect(Collectors.joining(""));
    }



}
