package com.java.rigor.entity;

/**
 * Created by sanandasena on 1/27/2016.
 */
public enum SubjectCodePrefix {
    CS("CS"),//Ex: CS3006
    AM("AM"),//Ex: AM3004
    PM("PM");//Ex: PM3006

    private String code;

    SubjectCodePrefix(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
