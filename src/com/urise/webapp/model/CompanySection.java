package com.urise.webapp.model;

import java.util.List;

public class CompanySection extends AbstractSection {
    protected List<Company> CompanyList;

    public CompanySection(List<Company> companyList) {
        CompanyList = companyList;
    }

    public void printSection() {
        for (int i=0; i<CompanyList.size(); i++) {
            System.out.printf("%-30s %-30s\n",CompanyList.get(i).Name, CompanyList.get(i).WebSite);
            //CompanyList.get(i).Name
        }

    }

}
