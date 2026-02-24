package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.urise.webapp.model.SectionType.*;

public class DataStreamSerializer implements SerializeStrategy {
    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            dos.writeInt(resume.getContacts().size());
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            dos.writeInt(resume.getSections().size());
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                dos.writeUTF(entry.getKey().name()); // пишем название секции
                switch (entry.getKey()) {
                    case PERSONAL:
                    case OBJECTIVE: {
                        dos.writeUTF(entry.getValue().toString()); // строка
                        break;
                    }
                    case ACHIEVEMENT:
                    case QUALIFICATIONS: {
                        ListSection listSection = (ListSection) entry.getValue();
                        dos.writeInt(listSection.getTextList().size());
                        for (String item : listSection.getTextList())
                            dos.writeUTF(item);
                        break;
                    }
                    case EXPERIENCE:
                    case EDUCATION: {
                        CompanySection companySection = (CompanySection) entry.getValue();
                        dos.writeInt(companySection.getCompanies().size());
                        for (Company company : companySection.getCompanies()) {
                            dos.writeUTF(company.getName());
                            dos.writeUTF(company.getWebSite());
                            dos.writeInt(company.getPeriods().size());
                            for (Company.CompanyPeriod period : company.getPeriods()) {
                                dos.writeUTF(period.getTitle());
                                dos.writeUTF(period.getDescription());
                                dos.writeUTF(period.getStartDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                                dos.writeUTF(period.getEndDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactCount = dis.readInt();
            for (int i = 0; i < contactCount; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            int sectionCount = dis.readInt();
            for (int i = 0; i < sectionCount; i++) {
                SectionType sectionType = valueOf(dis.readUTF());
                switch (sectionType) {
                    case PERSONAL: // TextSection
                    case OBJECTIVE: {
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    }
                    case ACHIEVEMENT: // ListSection
                    case QUALIFICATIONS: {
                        int listSectionCount = dis.readInt();
                        List<String> listSection = new ArrayList<>();
                        for (int j = 0; j < listSectionCount; j++) {
                            listSection.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(listSection));
                        break;
                    }
                    case EXPERIENCE: // CompanySection
                    case EDUCATION: {
                        int companyCount = dis.readInt();
                        List<Company> companyList = new ArrayList<>();
                        for (int j = 0; j < companyCount; j++) {
                            Company company = new Company();
                            company.setName(dis.readUTF());
                            company.setWebSite(dis.readUTF());
                            int periodCount = dis.readInt();
                            List<Company.CompanyPeriod> periodList = new ArrayList<>();
                            for (int k = 0; k < periodCount; k++) {
                                periodList.add(new Company.CompanyPeriod(dis.readUTF(), dis.readUTF(),
                                        LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF())));
                            }
                            company.setPeriods(periodList);
                            companyList.add(company);
                        }
                        resume.addSection(sectionType, new CompanySection(companyList));
                        break;
                    }
                    default:
                        throw new IllegalStateException("Unexpected value: " + sectionType);
                }
            }
            return resume;
        }
    }
}
