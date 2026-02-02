package com.urise.webapp.model;

import java.util.Arrays;
import java.util.List;

public class ListSection extends AbstractSection {
    private static final long serialVersionUID = 4270071768600374419L;
    protected List<String> textList;

    public ListSection(List<String> textList) {
        this.textList = textList;
    }

    public ListSection(String... listSectionItems) { //набираем из строк
        this.textList = Arrays.asList(listSectionItems);
    }

    @Override
    public String toString() {
        return "* " + String.join("\n* ", textList);
    }

}
