package com.app.core.vo;

import java.io.Serializable;

public class Option  implements Serializable {

    private static final long serialVersionUID = -4781681291137795640L;

    private String id;

    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
