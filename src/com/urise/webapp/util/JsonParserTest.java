package com.urise.webapp.util;

import com.urise.webapp.NamesGenerator;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;

import java.util.Map;
import java.util.UUID;

import static com.urise.webapp.ResumeTestData.createTestResume;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;


public class JsonParserTest {
    public static void main() throws Exception {
        String json = JsonParser.write(createTestResume(UUID.randomUUID().toString(), NamesGenerator.getName(0)));
        // System.out.println(json);
        // Resume r = JsonParser.read(json, Resume.class);
        Resume r = createTestResume(UUID.randomUUID().toString(), NamesGenerator.getName(0));
        Map<SectionType, AbstractSection> secMap = r.getSections();
        AbstractSection eduSec = secMap.get(SectionType.EDUCATION);
        String eduJson = JsonParser.write(eduSec);
        System.out.println(eduJson);
        AbstractSection eduSec2 = JsonParser.read(eduJson, AbstractSection.class);
        //String edu = JsonParser.write(secMap.get(SectionType.EXPERIENCE));
        //String pers = JsonParser.write(secMap.get(SectionType.PERSONAL));
        //System.out.println(pers);
        // System.out.println(edu.replace("Ф","Ш"));
        //JsonParser.read(pers, AbstractSection.class);
        // r.addSection(SectionType.EXPERIENCE, JsonParser.read(edu, AbstractSection.class));

    }
}
