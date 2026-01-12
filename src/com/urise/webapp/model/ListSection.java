package com.urise.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection {
    protected List<String> TextList;

    public ListSection(List<String> textList) {
        TextList = textList;
    }

    public void printSection() {
        for (int i=0; i<TextList.size(); i++) {
            System.out.println("* " + TextList.get(i));
        }
    }
}
