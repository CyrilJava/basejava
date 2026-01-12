package com.urise.webapp.model;

public class TextSection extends AbstractSection {
    protected String text;

    public TextSection(String text) {
        this.text = text;
    }

    public void printSection(){
        System.out.println(text);
    };
}
