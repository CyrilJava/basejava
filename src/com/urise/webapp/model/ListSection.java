package com.urise.webapp.model;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {
    @Serial
    private static final long serialVersionUID = 4270071768600374419L;
    protected List<String> textList;

    public ListSection() {
    }

    public ListSection(List<String> textList) {
        this.textList = textList;
    }

    public ListSection(String... listSectionItems) { // набираем из строк
        this.textList = Arrays.asList(listSectionItems);
    }

    @Override
    public String toString() {
        return "* " + String.join("\n* ", textList);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(textList, that.textList);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(textList);
    }
}
