package com.urise.webapp.model;

import java.io.Serializable;

public abstract class AbstractSection implements Serializable {
    public String getType() {
        return Type;
    }

    public void setType(String secType) {
        Type = secType;
    }

    private String Type;

}

