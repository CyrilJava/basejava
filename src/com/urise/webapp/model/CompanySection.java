package com.urise.webapp.model;

import java.util.List;

public class CompanySection extends AbstractSection {
    protected List<Company> CompanyList;

    public CompanySection(List<Company> companyList) {
        CompanyList = companyList;
    }

    public void printSection() {
        for (int i=0; i<CompanyList.size(); i++) {
            Company company = CompanyList.get(i);
            for (int j=0; j<company.PeriodList.size(); j++) {
                CompanyPeriod companyPeriod = company.PeriodList.get(j);
                System.out.printf("%-30s %-40s %tb %tY %tb %tY %-30s %-30s\n",
                        company.Name, company.WebSite, companyPeriod.StartDate, companyPeriod.StartDate, companyPeriod.EndDate, companyPeriod.EndDate, companyPeriod.Title, companyPeriod.Description);
            }
            //CompanyList.get(i).Name
        }

    }

}
