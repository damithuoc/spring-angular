package com.java.rigor.database.impl;

import com.java.rigor.constants.DaoConstants;
import com.java.rigor.database.DataBaseTableCreation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Created by sanandasena on 1/22/2016.
 */
public class DataBaseTableCreationImpl extends JdbcDaoSupport implements DataBaseTableCreation {
    private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseTableCreationImpl.class);

    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS STUDENT("
            + " ID BIGINT(20) NOT NULL" + DaoConstants.General.COMMA
            + " NAME VARCHAR(100) NOT NULL" + DaoConstants.General.COMMA
            + " REG_NUMBER VARCHAR (14) NOT NULL " + DaoConstants.General.COMMA
            + " EMAIL VARCHAR (100) NULL " + DaoConstants.General.COMMA
            + " ADDRESS VARCHAR (100) NULL " + DaoConstants.General.COMMA
            + " IS_ACTIVE BOOLEAN NOT NULL " + DaoConstants.General.COMMA
            + " CREATED_BY VARCHAR(100) NOT NULL " + DaoConstants.General.COMMA
            + " CREATED_DATE BIGINT (20) NOT NULL " + DaoConstants.General.COMMA
            + " UPDATED_BY VARCHAR (100) NULL " + DaoConstants.General.COMMA
            + " UPDATED_DATE BIGINT (20) NULL " + DaoConstants.General.COMMA
            + " CONSTRAINT STUDENT_REG_NUMBER UNIQUE(REG_NUMBER)" + DaoConstants.General.COMMA
            + " PRIMARY KEY (ID)) ";

    private static final String CREATE_USER_TABLE = " CREATE TABLE IF NOT EXISTS USER("
            + " ID BIGINT(20) NOT NULL" + DaoConstants.General.COMMA
            + " USERNAME VARCHAR(100) NOT NULL" + DaoConstants.General.COMMA
            + " IS_ACTIVE BOOLEAN NOT NULL " + DaoConstants.General.COMMA
            + " CREATED_BY VARCHAR(100) NOT NULL " + DaoConstants.General.COMMA
            + " CREATED_DATE BIGINT (20) NOT NULL " + DaoConstants.General.COMMA
            + " UPDATED_BY VARCHAR (100) NULL " + DaoConstants.General.COMMA
            + " UPDATED_DATE BIGINT (20) NULL " + DaoConstants.General.COMMA
            + " CONSTRAINT USER_USERNAME UNIQUE(USERNAME)" + DaoConstants.General.COMMA
            + " PRIMARY KEY (ID)) ";


    private static final String CREATE_USER_ROLE_TABLE = " CREATE TABLE IF NOT EXISTS USER_ROLE("
            + " ID BIGINT(20) NOT NULL" + DaoConstants.General.COMMA
            + " USERNAME VARCHAR(100) NOT NULL" + DaoConstants.General.COMMA
            + " IS_ACTIVE BOOLEAN NOT NULL " + DaoConstants.General.COMMA
            + " ROLE VARCHAR(45)" + DaoConstants.General.COMMA
            + " CREATED_BY VARCHAR(100) NOT NULL " + DaoConstants.General.COMMA
            + " CREATED_DATE BIGINT (20) NOT NULL " + DaoConstants.General.COMMA
            + " UPDATED_BY VARCHAR (100) NULL " + DaoConstants.General.COMMA
            + " UPDATED_DATE BIGINT (20) NULL " + DaoConstants.General.COMMA
            + " CONSTRAINT USER_ROLE_USERNAME FOREIGN KEY (USERNAME)  REFERENCES USER (USERNAME)" + DaoConstants.General.COMMA
            + " PRIMARY KEY (ID)) ";


    private static final String CREATE_SUBJECT_TABLE = "CREATE TABLE IF NOT EXISTS SUBJECT ("
            + " ID BIGINT(20) NOT NULL" + DaoConstants.General.COMMA
            + " SUBJECT_CODE VARCHAR(7) NOT NULL " + DaoConstants.General.COMMA
            + " SUBJECT_NAME VARCHAR(100) NOT NULL " + DaoConstants.General.COMMA
            + " IS_ACTIVE BOOLEAN NOT NULL " + DaoConstants.General.COMMA
            + " CREATED_BY VARCHAR(100) NOT NULL " + DaoConstants.General.COMMA
            + " CREATED_DATE BIGINT (20) NOT NULL " + DaoConstants.General.COMMA
            + " UPDATED_BY VARCHAR (100) NULL " + DaoConstants.General.COMMA
            + " UPDATED_DATE BIGINT (20) NULL " + DaoConstants.General.COMMA
            + " CONSTRAINT SUBJECT_SUBJECT_CODE UNIQUE(SUBJECT_CODE)" + DaoConstants.General.COMMA
            + " PRIMARY KEY (ID))";

    private static final String CREATE_TABLE_STUDENT_SUBJECT_MAPPING_TABLE = "CREATE TABLE IF NOT EXISTS STUDENT_SUBJECT("
            + " ID BIGINT(20) NOT NULL" + DaoConstants.General.COMMA
            + " SUBJECT_ID BIGINT(20) NOT NULL " + DaoConstants.General.COMMA
            + " STUDENT_ID BIGINT(20) NOT NULL " + DaoConstants.General.COMMA
            + " CONSTRAINT STUDENT_SUBJECT_SUBJECT_ID FOREIGN KEY (SUBJECT_ID)  REFERENCES SUBJECT (ID)" + DaoConstants.General.COMMA
            + " CONSTRAINT STUDENT_SUBJECT_STUDENT_ID FOREIGN KEY (STUDENT_ID)  REFERENCES STUDENT (ID)" + DaoConstants.General.COMMA
            + " CONSTRAINT STUDENT_SUBJECT_STUDENT_ID_SUBJECT_ID_UNIQUE UNIQUE  (STUDENT_ID, SUBJECT_ID)" + DaoConstants.General.COMMA
            + " PRIMARY KEY (ID))";

    /**
     * create STUDENT table in database.
     */
    public void createStudentTable() {
        LOGGER.info("Entered createStudentTable()");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getJdbcTemplate().getDataSource());

        jdbcTemplate.execute(CREATE_STUDENT_TABLE);
        LOGGER.info("Success!");
    }


    /**
     * create USER table in database.
     */
    public void createUserTable() {
        LOGGER.info("Entered createUserTable()");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getJdbcTemplate().getDataSource());

        jdbcTemplate.execute(CREATE_USER_TABLE);
        LOGGER.info("Success!");
    }


    /**
     * create USER_ROLE table in database.
     */
    public void createUserRoleTable() {
        LOGGER.info("Entered createUserRoleTable()");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getJdbcTemplate().getDataSource());

        jdbcTemplate.execute(CREATE_USER_ROLE_TABLE);
        LOGGER.info("Success!");
    }

    /**
     * create SUBJECT table in database
     */
    public void createSubjectTable() {
        LOGGER.info("Entered createSubjectTable()");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getJdbcTemplate().getDataSource());
        jdbcTemplate.execute(CREATE_SUBJECT_TABLE);
        LOGGER.info("Success!");
    }

    public void createStudentSubjectMappingTable() {
        LOGGER.info("Entered createStudentSubjectMappingTable()");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(getJdbcTemplate().getDataSource());
        jdbcTemplate.execute(CREATE_TABLE_STUDENT_SUBJECT_MAPPING_TABLE);
        LOGGER.info("Success!");
    }
}