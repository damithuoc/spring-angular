package com.java.rigor.constants;

/**
 * Created by work on 2/16/16.
 */
public interface Constants {

    String EMPTY_STRING = "";
    String SPACE_STRING = " ";
    String PLUS_MARK = "+";
    String HYPHEN = "-";
    Integer LENGTH_OF_ORDER_REF = 8;
    String ROLE_NAME = "ROLE_NAME";
    String ROLE_USER = "ROLE_USER";
    String ROLE_ADMIN = "ROLE_ADMIN";

    String SYSTEM_USER = "system user";
    String SYSTEM_ROLE = "ADMIN";

    Long DAY_TIME_IN_MILLIS = 60 * 60 * 60 * 60 * 24L;
    Long YEAR_TIME_IN_MILLIS = DAY_TIME_IN_MILLIS * 365;
}
