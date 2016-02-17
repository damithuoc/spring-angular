package com.java.rigor.database;

import com.java.rigor.exception.JavaRigorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by work on 1/24/16.
 */
@Component("dbDeploy")
public class DBDeploy {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBDeploy.class);

    @Autowired
    private DataBaseTableCreation dataBaseTableCreation;

    /**
     * create data tables if not exists in VRIGOR database.
     *
     * @throws JavaRigorException
     */
    public void deploy() throws JavaRigorException {
        LOGGER.info("Entered deploy()");

        LOGGER.info("Entered runDBQuery()");

        // create STUDENT table
        dataBaseTableCreation.createStudentTable();

        // create USER table
        dataBaseTableCreation.createUserTable();

        // create USER_ROLE table
        dataBaseTableCreation.createUserRoleTable();

        // create SUBJECT table
        dataBaseTableCreation.createSubjectTable();

        // create STUDENT_SUBJECT mapping table
        dataBaseTableCreation.createStudentSubjectMappingTable();

        LOGGER.info("Run query success!");

    }
}
