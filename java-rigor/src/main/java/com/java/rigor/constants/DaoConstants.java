package com.java.rigor.constants;

/**
 * Created by sanandasena on 1/11/2016.
 */
public interface DaoConstants {

    interface General {

        String COMMA = ",";
        String COLON = ":";
        String PERCENTAGE = "%";
        String ID = "ID";
        String PARAM_ID = "id";
        String ADDRESS = "ADDRESS";
        String PARAM_ADDRESS = "address";
        String EMAIL = "EMAIL";
        String PARAM_EMAIL = "email";
        String NAME = "NAME";
        String PARAM_NAME = "name";
        String CREATED_BY = "CREATED_BY";
        String PARAM_CREATED_BY = "createdBy";
        String UPDATED_BY = "UPDATED_BY";
        String PARAM_UPDATED_BY = "updatedBy";
        String CREATED_DATE = "CREATED_DATE";
        String PARAM_CREATED_DATE = "createdDate";
        String UPDATED_DATE = "UPDATED_DATE";
        String PARAM_UPDATED_DATE = "updatedDate";
        String IS_ACTIVE = "IS_ACTIVE";
        String PARAM_IS_ACTIVE = "isActive";
    }

    interface UserDao {
        String USERNAME = "USERNAME";
        String PARAM_USERNAME = "username";

        String PASSWORD = "PASSWORD";
        String PARAM_PASSWORD = "password";

        String ROLE = "ROLE";
        String PARAM_ROLE = "role";
    }

    interface StudentDao {
        String REG_NUMBER = "REG_NUMBER";
        String PARAM_REG_NUMBER = "regNumber";
        String STUDENT_ID = "STUDENT_ID";
        String PARAM_STUDENT_ID = "studentId";

    }

    interface SubjectDao {
        String SUBJECT_CODE = "SUBJECT_CODE";
        String PARAM_SUBJECT_CODE = "subjectCode";
        String SUBJECT_ID = "SUBJECT_ID";
        String PARAM_SUBJECT_ID = "subjectId";
        String PARAM_SUBJECT_ID_LIST = "subjectIdList";
        String SUBJECT_NAME = "SUBJECT_NAME";
        String PARAM_SUBJECT_NAME = "subjectName";
    }
}
