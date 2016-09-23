package com.abive.dao;

/**
 * Created by ranjiangchuan on 15/3/29.
 */
public enum Option {

    INSERT("insert"),

    UPDATE("update"),

    DELETE("delete"),

    REAL_DELETE("realDelete");

    private String id;

    private Option(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
