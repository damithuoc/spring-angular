package com.java.rigor.dao.impl;

import com.java.rigor.constants.DaoConstants;
import com.java.rigor.dao.SubjectDao;
import com.java.rigor.entity.Subject;
import com.java.rigor.entity.SubjectCode;
import com.java.rigor.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sanandasena on 1/29/2016.
 */
@Repository
public class SubjectDaoImpl extends JdbcDaoSupport implements SubjectDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectDaoImpl.class);

    private static final String ADD_SUBJECT = " INSERT INTO SUBJECT (ID, SUBJECT_CODE, SUBJECT_NAME, CREATED_BY, CREATED_DATE, IS_ACTIVE) VALUE("
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.SubjectDao.PARAM_SUBJECT_CODE + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.SubjectDao.PARAM_SUBJECT_NAME + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_BY + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_CREATED_DATE + DaoConstants.General.COMMA
            + DaoConstants.General.COLON + DaoConstants.General.PARAM_IS_ACTIVE + ")";

    private static final String GET_ALL_SUBJECTS = "SELECT * FROM SUBJECT WHERE IS_ACTIVE = TRUE ";

    private static final String GET_ALL_ACTIVE_SUBJECT_IDS = " SELECT ID FROM SUBJECT WHERE IS_ACTIVE = TRUE";

    private static final String UPDATE_SUBJECT_BY_USING_SUBJECT_ID = "UPDATE SUBJECT SET "
            + " SUBJECT_NAME = " + DaoConstants.General.COLON + DaoConstants.SubjectDao.PARAM_SUBJECT_NAME + DaoConstants.General.COMMA
            + " SUBJECT_CODE = " + DaoConstants.General.COLON + DaoConstants.SubjectDao.PARAM_SUBJECT_CODE + DaoConstants.General.COMMA
            + " UPDATED_BY = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_BY + DaoConstants.General.COMMA
            + " UPDATED_DATE = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_UPDATED_DATE
            + " WHERE ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    private static final String DISABLE_SUBJECT_BY_SUBJECT_ID = "UPDATE SUBJECT SET IS_ACTIVE = FALSE WHERE "
            + " ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    private static final String GET_SUBJECT_BY_SUBJECT_ID = "SELECT * FROM SUBJECT WHERE IS_ACTIVE = TRUE AND "
            + " ID = " + DaoConstants.General.COLON + DaoConstants.General.PARAM_ID;

    /**
     * add subjects.
     *
     * @param subjectList
     * @return
     */
    public Boolean addSubjects(List<Subject> subjectList) {
        LOGGER.info("Entered addSubjects(" + subjectList + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        Integer runCount = subjectList.size();
        for (Subject subject : subjectList) {
            Long id = IdGenerator.generateId();

            params.addValue(DaoConstants.General.PARAM_ID, id);
            params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_CODE, subject.getSubjectCode());
            params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_NAME, subject.getSubjectName());
            params.addValue(DaoConstants.General.PARAM_CREATED_BY, subject.getCreatedBy());
            params.addValue(DaoConstants.General.PARAM_CREATED_DATE, subject.getCreatedDate());
            params.addValue(DaoConstants.General.PARAM_IS_ACTIVE, Boolean.TRUE);

            namedParameterJdbcTemplate.update(ADD_SUBJECT, params);
            runCount--;
        }

        if (runCount.equals(0)) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * save a subject in subject table.
     *
     * @param subject
     * @return
     */
    public Boolean saveSubject(Subject subject) {
        LOGGER.info("Entered saveSubject(" + subject + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();

        Long id = IdGenerator.generateId();

        params.addValue(DaoConstants.General.PARAM_ID, id);
        params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_CODE, subject.getSubjectCode().getSubjectCodeStr());
        params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_NAME, subject.getSubjectName());
        params.addValue(DaoConstants.General.PARAM_CREATED_BY, subject.getCreatedBy());
        params.addValue(DaoConstants.General.PARAM_CREATED_DATE, subject.getCreatedDate());
        params.addValue(DaoConstants.General.PARAM_IS_ACTIVE, Boolean.TRUE);

        try {
            namedParameterJdbcTemplate.update(ADD_SUBJECT, params);
            return Boolean.TRUE;
        } catch (DataAccessException dae) {
            return Boolean.FALSE;
        }
    }

    /**
     * get all active subjects.
     *
     * @return active subject list
     */
    public List<Subject> getAllActiveSubjects() {
        LOGGER.info("Entered getAllActiveSubjects()");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        return namedParameterJdbcTemplate.query(GET_ALL_SUBJECTS, (resultSet, i) -> {
            String subjectCodeStr = resultSet.getString(DaoConstants.SubjectDao.SUBJECT_CODE);
            SubjectCode subjectCode = new SubjectCode();

            String[] subjectCodeStrArray = subjectCodeStr.split("-");

            String prefix = subjectCodeStrArray[0];
            Integer codeNumber = Integer.parseInt(subjectCodeStrArray[1]);
            subjectCode.setPrefix(prefix);
            subjectCode.setCodeNumber(codeNumber);

            Subject subject = new Subject();
            subject.setSubjectCode(subjectCode);
            subjectCode.setSubjectCodeStr(prefix, codeNumber);

            subject.setSubjectName(resultSet.getString(DaoConstants.SubjectDao.SUBJECT_NAME));
            subject.setId(resultSet.getLong(DaoConstants.General.ID));

            return subject;
        });
    }

    /**
     * get all active subject ids.
     *
     * @return
     */
    public List<Long> getAllActiveSubjectIds() {
        LOGGER.info("Entered getAllActiveSubjectIds");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());
        return namedParameterJdbcTemplate.query(GET_ALL_ACTIVE_SUBJECT_IDS, (resultSet, i) -> {
            return resultSet.getLong(DaoConstants.General.ID);
        });
    }

    /**
     * update subject by using subject id.
     *
     * @param subject
     * @return
     */
    public Boolean updateSubject(Subject subject) {
        LOGGER.info("Entered updateSubject(" + subject + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, subject.getId());
        params.addValue(DaoConstants.General.PARAM_UPDATED_BY, subject.getUpdatedBy());
        params.addValue(DaoConstants.General.PARAM_UPDATED_DATE, subject.getUpdatedDate());
        params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_NAME, subject.getSubjectName());
        params.addValue(DaoConstants.SubjectDao.PARAM_SUBJECT_CODE, subject.getSubjectCode().getSubjectCodeStr());

        try {
            namedParameterJdbcTemplate.update(UPDATE_SUBJECT_BY_USING_SUBJECT_ID, params);
            return Boolean.TRUE;

        } catch (DataAccessException dae) {
            return Boolean.FALSE;
        }

    }

    /**
     * disable subject by using subject id.
     *
     * @param subjectId
     * @return
     */
    public Boolean deleteSubjectBySubjectId(Long subjectId) {
        LOGGER.info("Entered deleteSubjectBySubjectId(" + subjectId + ")");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, subjectId);

        try {
            namedParameterJdbcTemplate.update(DISABLE_SUBJECT_BY_SUBJECT_ID, params);
            return Boolean.TRUE;
        } catch (DataAccessException dae) {
            return Boolean.FALSE;
        }
    }

    /**
     * get a subject by subject id.
     *
     * @param subjectId
     * @return student object
     */
    public Subject getSubjectBySubjectId(Long subjectId) {
        LOGGER.info("Entered getSubjectBySubjectId(" + subjectId + ")");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(
                getJdbcTemplate().getDataSource());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(DaoConstants.General.PARAM_ID, subjectId);
        List<Subject> subjectList;
        if (subjectId != null) {
            subjectList = namedParameterJdbcTemplate
                    .query(GET_SUBJECT_BY_SUBJECT_ID, params, (resultSet, i) -> {
                        String subjectCodeStr = resultSet.getString(DaoConstants.SubjectDao.SUBJECT_CODE);
                        SubjectCode subjectCode = new SubjectCode();

                        String[] subjectCodeStrArray = subjectCodeStr.split("-");

                        String prefix = subjectCodeStrArray[0];
                        Integer codeNumber = Integer.parseInt(subjectCodeStrArray[1]);
                        subjectCode.setPrefix(prefix);
                        subjectCode.setCodeNumber(codeNumber);

                        Subject subject = new Subject();
                        subject.setSubjectCode(subjectCode);
                        subjectCode.setSubjectCodeStr(prefix, codeNumber);

                        subject.setSubjectName(resultSet.getString(DaoConstants.SubjectDao.SUBJECT_NAME));
                        subject.setId(resultSet.getLong(DaoConstants.General.ID));

                        return subject;
                    });

            if (!subjectList.isEmpty()) {
                return subjectList.get(0);
            }
        }
        return null;
    }
}
