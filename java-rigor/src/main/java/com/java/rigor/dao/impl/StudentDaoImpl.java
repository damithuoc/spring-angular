package com.java.rigor.dao.impl;

import com.java.rigor.constants.DaoConstants;
import com.java.rigor.dao.StudentDao;
import com.java.rigor.entity.Student;
import com.java.rigor.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sanandasena on 1/11/2016.
 */

@Repository
public class StudentDaoImpl extends JdbcDaoSupport implements StudentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentDaoImpl.class);

    private static final String SAVE_STUDENT = "INSERT INTO STUDENT (ID, REG_NUMBER, NAME, EMAIL, ADDRESS, CREATED_BY, CREATED_DATE, IS_ACTIVE ) VALUES("
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.StudentDao.PARAM_REG_NUMBER + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_NAME + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_EMAIL + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_ADDRESS + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_BY + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_DATE + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_IS_ACTIVE + ")";

    private static final String GET_ALL_ACTIVE_STUDENTS = "SELECT * FROM STUDENT WHERE IS_ACTIVE = TRUE";

    private static final String UPDATE_STUDENT_BY_REG_NUMBER = " UPDATE STUDENT SET "
            + " NAME = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_NAME + DaoConstants.General.COMMA
            + " EMAIL = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_EMAIL + DaoConstants.General.COMMA
            + " ADDRESS = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ADDRESS + DaoConstants.General.COMMA
            + " UPDATED_BY = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_BY + DaoConstants.General.COMMA
            + " UPDATED_DATE = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_DATE
            + " WHERE ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    private static final String GET_STUDENT_BY_USING_REG_NUMBER = " SELECT *  FROM  STUDENT WHERE "
            + " REG_NUMBER = " + DaoConstants.General.COLON + DaoConstants.StudentDao.PARAM_REG_NUMBER
            + " AND IS_ACTIVE = TRUE";

    private static final String DISABLE_STUDENT_ACCOUNT_USING_ID = "UPDATE STUDENT SET  IS_ACTIVE = FALSE " + DaoConstants.General.COMMA
            + " UPDATED_DATE = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_DATE + DaoConstants.General.COMMA
            + " UPDATED_BY = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_BY
            + " WHERE ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    private static final String GET_ALL_STUDENT_IDS = "SELECT ID FROM STUDENT ";

    private static final String GET_STUDENT_BY_ID = "SELECT * FROM STUDENT WHERE IS_ACTIVE = TRUE AND "
            + " ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    private static final String GET_ALL_ACTIVE_STUDENT_IDS = "SELECT ID  FROM STUDENT WHERE IS_ACTIVE = TRUE";

    private static final String DELETE_STUDENT_OLD_DATA = " DELETE FROM STUDENT WHERE IS_ACTIVE = FALSE AND "
            + " UPDATED_DATE <  " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_DATE;

    private static final String GET_STUDENT_IDS_BY_USING_UPDATED_DATE = " SELECT ID FROM STUDENT WHERE IS_ACTIVE = FALSE AND"
            + " UPDATED_DATE < " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_DATE;

    /**
     * for save a student
     *
     * @param student
     */
    public void saveStudent(Student student) {
        LOGGER.info("Entered saveStudent(" + student + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        // Auto generated random id for student
        Long id = IdGenerator.generateId();

        params.addValue(DaoConstants.General.PARAM_ID, id);
        params.addValue(DaoConstants.StudentDao.PARAM_REG_NUMBER, student.getRegistrationNumber());
        params.addValue(DaoConstants.General.PARAM_NAME, student.getStudentName());
        params.addValue(DaoConstants.General.PARAM_EMAIL, student.getEmail());
        params.addValue(DaoConstants.General.PARAM_ADDRESS, student.getAddress());
        params.addValue(DaoConstants.General.PARAM_CREATED_BY, student.getCreatedBy());
        params.addValue(DaoConstants.General.PARAM_CREATED_DATE, student.getCreatedDate());
        params.addValue(DaoConstants.General.PARAM_IS_ACTIVE, Boolean.TRUE);

        namedParameterJdbcTemplate.update(SAVE_STUDENT, params);
    }

    /**
     * get all students
     *
     * @return
     */
    public List<Student> getAllActiveStudents() {
        LOGGER.info("Entered getAllActiveStudents()");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        return namedParameterJdbcTemplate.query(GET_ALL_ACTIVE_STUDENTS, getAllStudentsRowMapper());
    }

    // row mapper for get all students
    private RowMapper<Student> getAllStudentsRowMapper() {
        return (resultSet, i) -> {
            Student student = new Student();
            student.setId(resultSet.getLong(DaoConstants.General.ID));
            student.setRegistrationNumber(resultSet.getString(DaoConstants.StudentDao.REG_NUMBER));
            student.setStudentName(resultSet.getString(DaoConstants.General.NAME));
            student.setEmail(resultSet.getString(DaoConstants.General.EMAIL));
            student.setAddress(resultSet.getString(DaoConstants.General.ADDRESS));

            return student;
        };
    }

    /**
     * update student by using student registration number.
     *
     * @param student
     */
    public void updateStudent(Student student) {
        LOGGER.info("Entered updateStudent(" + student + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue(DaoConstants.General.PARAM_ID, student.getId());
        params.addValue(DaoConstants.General.PARAM_NAME, student.getStudentName());
        params.addValue(DaoConstants.General.PARAM_EMAIL, student.getEmail());
        params.addValue(DaoConstants.General.PARAM_ADDRESS, student.getAddress());
        params.addValue(DaoConstants.General.PARAM_UPDATED_BY, student.getUpdatedBy());
        params.addValue(DaoConstants.General.PARAM_UPDATED_DATE, student.getUpdatedDate());

        namedParameterJdbcTemplate.update(UPDATE_STUDENT_BY_REG_NUMBER, params);

    }

    /**
     * Get a student using registration number.
     *
     * @param regNumber
     * @return
     */
    public Student getStudentByUsingRegNumber(String regNumber) {
        LOGGER.info("Entered getStudentByUsingRegNumber(" + regNumber + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.StudentDao.PARAM_REG_NUMBER, regNumber);

        List<Student> studentList = namedParameterJdbcTemplate.query(GET_STUDENT_BY_USING_REG_NUMBER, params, getStudentRowMapper());

        if (studentList == null || studentList.isEmpty()) {
            return null;
        }

        return studentList.get(0);

    }

    private RowMapper<Student> getStudentRowMapper() {
        return (resultSet, i) -> {
            Student student = new Student();
            student.setRegistrationNumber(resultSet.getString(DaoConstants.StudentDao.REG_NUMBER));
            student.setStudentName(resultSet.getString(DaoConstants.General.NAME));
            student.setEmail(resultSet.getString(DaoConstants.General.EMAIL));
            student.setAddress(resultSet.getString(DaoConstants.General.ADDRESS));

            return student;
        };
    }


    /**
     * disable student account by using student id.
     *
     * @param student
     * @return
     */
    public Boolean deleteStudent(Student student) {
        LOGGER.info("Entered deleteStudent(" + student + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, student.getId());
        params.addValue(DaoConstants.General.PARAM_UPDATED_DATE, student.getUpdatedDate());
        params.addValue(DaoConstants.General.PARAM_UPDATED_BY, student.getUpdatedBy());

        namedParameterJdbcTemplate.update(DISABLE_STUDENT_ACCOUNT_USING_ID, params);

        List<Long> studentIds = getAllActiveStudentIds();

        if (studentIds != null && !studentIds.isEmpty()) {
            if (!studentIds.contains(student.getId())) {
                return Boolean.FALSE;
            } else {
                Student fetStudent = getStudentById(student.getId());
                if (fetStudent == null) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;

    }

    /**
     * get all active student id list.
     *
     * @return
     */
    public List<Long> getAllActiveStudentIds() {

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        return namedParameterJdbcTemplate.query(GET_ALL_ACTIVE_STUDENT_IDS, (resultSet, i) -> {
            return resultSet.getLong(DaoConstants.General.ID);
        });
    }

    /**
     * get all student ids.
     *
     * @return
     */
    public List<Long> getAllStudentIds() {
        LOGGER.info("Entered getAllStudentIds()");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        return namedParameterJdbcTemplate.query(GET_ALL_STUDENT_IDS, getAllStudentIdsRowMapper());
    }

    private RowMapper<Long> getAllStudentIdsRowMapper() {
        return (resultSet, i) -> resultSet.getLong(DaoConstants.General.ID);
    }

    /**
     * get student by student id.
     *
     * @param studentId
     * @return
     */
    public Student getStudentById(final Long studentId) {
        LOGGER.info("Entered getStudentById(" + studentId + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, studentId);

        List<Student> studentList = namedParameterJdbcTemplate.query(GET_STUDENT_BY_ID, params, (resultSet, i) -> {
            Student student = new Student();
            student.setId(resultSet.getLong(DaoConstants.General.ID));
            student.setRegistrationNumber(resultSet.getString(DaoConstants.StudentDao.REG_NUMBER));
            student.setStudentName(resultSet.getString(DaoConstants.General.NAME));

            return student;
        });
        if (studentList == null || studentList.isEmpty()) {
            return null;
        }
        return studentList.get(0);
    }

    /**
     * delete old students data.
     *
     * @param lastUpdatedDate
     * @return return delete status.
     */
    public Boolean deleteOldStudentData(final Long lastUpdatedDate) {
        LOGGER.info("Entered  deleteOldStudentData(" + lastUpdatedDate + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_UPDATED_DATE, lastUpdatedDate);

        namedParameterJdbcTemplate.update(DELETE_STUDENT_OLD_DATA, params);

        List<Long> idList = namedParameterJdbcTemplate.query(GET_STUDENT_IDS_BY_USING_UPDATED_DATE, params, (resultSet, i) -> {
            return resultSet.getLong(DaoConstants.General.ID);
        });

        if (idList == null || idList.isEmpty()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
