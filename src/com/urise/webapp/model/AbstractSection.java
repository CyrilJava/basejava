package com.urise.webapp.model;

public abstract class AbstractSection {
    public String getType() {
        return Type;
    }

    public void setType(String secType) {
        Type = secType;
    }

    private String Type;

    public abstract void printSection();

}

