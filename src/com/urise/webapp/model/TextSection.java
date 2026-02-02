package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 4270071768600374419L;
    protected String text;

    public TextSection(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
