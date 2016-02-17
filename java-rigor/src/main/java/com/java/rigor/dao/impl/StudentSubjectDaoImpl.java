package com.java.rigor.dao.impl;

import com.java.rigor.constants.DaoConstants;
import com.java.rigor.dao.StudentSubjectDao;
import com.java.rigor.entity.Student;
import com.java.rigor.entity.Subject;
import com.java.rigor.entity.SubjectCode;
import com.java.rigor.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sanandasena on 2/2/2016.
 */
@Repository
public class StudentSubjectDaoImpl extends JdbcDaoSupport implements StudentSubjectDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubjectDaoImpl.class);

    private static final String ADD_SUBJECTS_TO_STUDENT_ACCOUNT = "INSERT INTO STUDENT_SUBJECT (ID, STUDENT_ID, SUBJECT_ID) VALUES("
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.StudentDao.PARAM_STUDENT_ID + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.SubjectDao.PARAM_SUBJECT_ID + ")";


    private static final String GET_ALL_STUDENTS_WITH_SUBJECTS = " SELECT DISTINCT ST.ID, ST.NAME, SS.SUBJECT_ID, "
            + " ( SELECT SUBJECT_NAME FROM SUBJECT WHERE ID = SS.SUBJECT_ID ) AS SUBJECT_NAME, "
            + " ( SELECT SUBJECT_CODE FROM SUBJECT WHERE ID = SS.SUBJECT_ID ) AS SUBJECT_CODE FROM SUBJECT SJ, STUDENT ST "
            + " LEFT JOIN STUDENT_SUBJECT SS ON ( ST.ID = SS.STUDENT_ID ) WHERE ST.IS_ACTIVE = TRUE AND SJ.IS_ACTIVE = TRUE";

    private static final String GET_STUDENT_WITH_SUBJECTS = " SELECT DISTINCT ST.ID, ST.NAME, SS.SUBJECT_ID, "
            + " ( SELECT SUBJECT_NAME FROM SUBJECT WHERE ID = SS.SUBJECT_ID ) AS SUBJECT_NAME, "
            + " ( SELECT SUBJECT_CODE FROM SUBJECT WHERE ID = SS.SUBJECT_ID ) AS SUBJECT_CODE FROM SUBJECT SJ, STUDENT ST "
            + " LEFT JOIN STUDENT_SUBJECT SS ON ( ST.ID = SS.STUDENT_ID ) WHERE ST.IS_ACTIVE = TRUE AND SJ.IS_ACTIVE = TRUE "
            + " AND ST.ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    private static final String DELETE_STUDENT_SUBJECT = " DELETE FROM STUDENT_SUBJECT WHERE "
            + " STUDENT_ID = " + DaoConstants.General.COLON + DaoConstants.StudentDao.PARAM_STUDENT_ID
            + " AND SUBJECT_ID IN( " + DaoConstants.General.COLON + DaoConstants.SubjectDao.PARAM_SUBJECT_ID_LIST
            + " )";

    /**
     * add subjects to student.
     *
     * @param studentId
     * @param subjectIdList
     * @return
     */
    public Boolean addStudentSubjectsByUsingStudentId(final Long studentId, final List<Long> subjectIdList) {
        LOGGER.info("Entered addStudentSubjectsByUsingStudentId(" + studentId + "," + subjectIdList + ")");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        Integer runCount = subjectIdList.size();
        for (Long subjectId : subjectIdList) {
            Long id = IdGenerator.generateId();
            params.addValue(DaoConstants.General.PARAM_ID, id);
            params.addValue(DaoConstants.StudentDao.PARAM_STUDENT_ID, studentId);
            params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_ID, subjectId);

            namedParameterJdbcTemplate.update(ADD_SUBJECTS_TO_STUDENT_ACCOUNT, params);

            runCount--;
        }
        if (runCount.equals(0)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * get students with subjects.
     *
     * @return a student collection.
     */
    public Collection<Student> getAllStudentWithSubjects() {
        LOGGER.info("Entered getAllStudentWithSubjects()");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        Map<Long, Student> studentMap = new HashMap<>();

        namedParameterJdbcTemplate.query(GET_ALL_STUDENTS_WITH_SUBJECTS, getStudentWithSubjectRowMapper(studentMap));

        return studentMap.values();
    }

    // common row mapper for fetch students
    private RowMapper<Student> getStudentWithSubjectRowMapper(Map<Long, Student> studentMap) {
        return ((resultSet, i) -> {
            Long studentId = resultSet.getLong(DaoConstants.General.ID);
            Student student = studentMap.get(studentId);

            if (student == null) {
                student = new Student();
                student.setId(studentId);
                student.setStudentName(resultSet.getString(DaoConstants.General.NAME));
                student.setIsActive(Boolean.TRUE);
                studentMap.put(studentId, student);
            }

            String subjectCodeStr = resultSet.getString(DaoConstants.SubjectDao.SUBJECT_CODE);

            if (subjectCodeStr != null) {
                SubjectCode subjectCode = new SubjectCode();

                String[] subjectCodeStrArray = subjectCodeStr.split("-");

                String prefix = subjectCodeStrArray[0];
                Integer codeNumber = Integer.parseInt(subjectCodeStrArray[1]);
                subjectCode.setPrefix(prefix);
                subjectCode.setCodeNumber(codeNumber);
                subjectCode.setSubjectCodeStr(prefix, codeNumber);

                Subject subject = new Subject();
                subject.setSubjectCode(subjectCode);

                subject.setSubjectName(resultSet.getString(DaoConstants.SubjectDao.SUBJECT_NAME));
                subject.setId(resultSet.getLong(DaoConstants.SubjectDao.SUBJECT_ID));
                subject.setIsActive(Boolean.TRUE);

                student.addSubject(subject);

            }

            return student;
        });
    }

    /**
     * get student with subject list.
     *
     * @param studentId
     * @return student object
     */
    public Student getStudentWithSubjects(final Long studentId) {
        LOGGER.info("Entered getStudentWithSubjects(" + studentId + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, studentId);

        Map<Long, Student> studentMap = new HashMap<>();

        namedParameterJdbcTemplate.query(GET_STUDENT_WITH_SUBJECTS, params, getStudentWithSubjectRowMapper(studentMap));

        Student student = studentMap.get(studentId);
        if (student == null) {
            return null;
        }
        return student;
    }

    /**
     * delete student subject by using student ids and
     * subject id.
     *
     * @param studentId
     * @param subjectIdList
     * @return Boolean if execution success return TRUE
     */
    public Boolean deleteStudentSubject(final Long studentId, final List<Long> subjectIdList) {
        LOGGER.info("Entered deleteStudentSubject(" + studentId + ", " + subjectIdList + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.StudentDao.PARAM_STUDENT_ID, studentId);
        params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_ID_LIST, subjectIdList);

        try {
            namedParameterJdbcTemplate.update(DELETE_STUDENT_SUBJECT, params);
            return Boolean.TRUE;

        } catch (DataAccessException dae) {
            return Boolean.FALSE;
        }
    }
}
