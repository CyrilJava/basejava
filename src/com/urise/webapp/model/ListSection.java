package com.urise.webapp.model;

import java.util.List;

public class ListSection extends AbstractSection {
    protected List<String> textList;

    public ListSection(List<String> textList) {
        this.textList = textList;
    }

    @Override
    public String toString() {
        return "* " + String.join("\n* ", textList);
    }

}
