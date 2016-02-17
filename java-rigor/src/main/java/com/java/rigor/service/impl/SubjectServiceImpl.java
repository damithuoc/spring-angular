package com.java.rigor.service.impl;

import com.java.rigor.dao.StudentDao;
import com.java.rigor.dao.SubjectDao;
import com.java.rigor.entity.Subject;
import com.java.rigor.entity.SubjectCode;
import com.java.rigor.entity.SubjectCodePrefix;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.SubjectService;
import com.java.rigor.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Created by sanandasena on 1/29/2016.
 */
@Service("subjectService")
@Configurable
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SubjectServiceImpl implements SubjectService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectServiceImpl.class);

    @Autowired
    private SubjectDao subjectDao;

    @Autowired
    private StudentDao studentDao;

    /**
     * add subjects
     *
     * @param subjectList
     * @return Boolean, if success return true
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean addSubjects(List<Subject> subjectList) throws JavaRigorException {
        LOGGER.info("Entered addSubjects(" + subjectList + ") ");

        if (subjectList == null || subjectList.isEmpty()) {
            String errorMessage = "Subject list null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = auth.getName();

        List<Subject> dbSubjectList = getAllActiveSubjects();

        List<Subject> saveSubjectList = new ArrayList<>();

        for (Subject subject : subjectList) {
            if (subject == null) {
                String errorMessage = "Subject null or empty!";
                LOGGER.error(errorMessage);
                throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
            }

            if (subject.getSubjectCode() == null) {
                String errorMessage = "Subject code null or empty!";
                LOGGER.error(errorMessage);
                throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
            }

            if (subject.getSubjectName() == null || subject.getSubjectName().isEmpty()) {
                String errorMessage = "Subject name null or empty!";
                LOGGER.error(errorMessage);
                throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
            }

            if (!dbSubjectList.contains(subject)) {
                subject.setCreatedBy(loginUser);
                subject.setCreatedDate(System.currentTimeMillis());
                saveSubjectList.add(subject);
            }
        }

        try {
            if (!saveSubjectList.isEmpty()) {
                return subjectDao.addSubjects(saveSubjectList);
            }
            return Boolean.FALSE;

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while saving subjects!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }

    }

    /**
     * save a subject.
     *
     * @param subject
     * @return
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean saveSubject(Subject subject) throws JavaRigorException {
        LOGGER.info("Entered saveSubject(" + subject + ")");

        if (!subjectCommonValidation(subject)) {
            String errorMessage = "Invalid subject found!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = auth.getName();

        subject.setCreatedBy(loginUser);
        subject.setCreatedDate(System.currentTimeMillis());

        try {
            return subjectDao.saveSubject(subject);

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while saving subject!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * get all subject code prefixes.
     *
     * @return
     * @throws JavaRigorException
     */
    public List<SubjectCode> getAllSubjectPrefixes() throws JavaRigorException {
        LOGGER.info("Entered getAllSubjectPrefixes()");

        List<SubjectCodePrefix> subjectCodePrefixList = new ArrayList<>(EnumSet.allOf(SubjectCodePrefix.class));
        List<SubjectCode> subjectCodeList = new ArrayList<>();

        for (SubjectCodePrefix prefix : subjectCodePrefixList) {
            SubjectCode subjectCode = new SubjectCode();
            subjectCode.setPrefix(prefix.getCode());
            subjectCodeList.add(subjectCode);
        }

        return subjectCodeList;
    }

    /**
     * get all active subjects.
     *
     * @return
     * @throws JavaRigorException
     */
    public List<Subject> getAllActiveSubjects() throws JavaRigorException {
        LOGGER.info("Entered getAllActiveSubjects()");

        return subjectDao.getAllActiveSubjects();
    }

    /**
     * @param subject
     * @return
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean updateSubject(Subject subject) throws JavaRigorException {
        LOGGER.info("Entered updateSubject(" + subject + ")");

        if (!subjectCommonValidation(subject)) {
            String errorMessage = "Invalid subject found!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loginUser = auth.getName();

        subject.setUpdatedBy(loginUser);
        subject.setUpdatedDate(System.currentTimeMillis());

        return subjectDao.updateSubject(subject);
    }

    //validate subjects by using subject ids.

    /**
     * @param subjectIdList
     * @return
     * @throws JavaRigorException
     */
    public Boolean validateSubjectIds(final List<Long> subjectIdList) throws JavaRigorException {
        LOGGER.info("Entered validateSubjectIds(" + subjectIdList + ")");

        List<Long> dbSubjectIdList = getAllActiveSubjectIds();
        if (dbSubjectIdList.isEmpty()) {
            return Boolean.FALSE;
        }

        if (subjectIdList == null || subjectIdList.isEmpty()) {
            return Boolean.FALSE;
        }

        Boolean isValidId = Boolean.TRUE;

        for (Long saveSubjectId : subjectIdList) {
            if (!dbSubjectIdList.contains(saveSubjectId)) {
                isValidId = Boolean.FALSE;
                break;
            }
        }

        if (isValidId) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    // validation common fields in a subject.
    private Boolean subjectCommonValidation(Subject subject) throws JavaRigorException {
        LOGGER.info("Entered subjectCommonValidation(" + subject + ")");

        if (subject == null) {
            String errorMessage = "Subject is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (subject.getSubjectCode() == null) {
            String errorMessage = "Subject code is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        SubjectCode subjectCode = subject.getSubjectCode();

        if (subjectCode.getPrefix() == null || subjectCode.getPrefix().isEmpty()) {
            String errorMessage = "Subject code prefix is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        // validate subject code prefix TODO
        List<SubjectCodePrefix> subjectCodePrefixeList = new ArrayList<>(EnumSet.allOf(SubjectCodePrefix.class));
        List<String> prefixStrs = new ArrayList<>();
        subjectCodePrefixeList.forEach(subjectCodePrefix -> prefixStrs.add(subjectCodePrefix.getCode()));

        if (prefixStrs.isEmpty() || !prefixStrs.contains(subjectCode.getPrefix())) {
            String errorMessage = "Invalid subject code prefix !";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (subjectCode.getCodeNumber() == null || (subjectCode.getCodeNumber() > 5000 || subjectCode.getCodeNumber() < 1000)) {
            String errorMessage = "Invalid subject codeNumber !";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (subject.getSubjectName() == null || subject.getSubjectName().isEmpty()) {
            String errorMessage = "Subject name is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }
        // set subject code in string.
        subjectCode.setSubjectCodeStr(subjectCode.getPrefix(), subjectCode.getCodeNumber());

        return Boolean.TRUE;
    }

    /**
     * disable subject by using subject id.
     *
     * @param subjectId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean deleteSubjectBySubjectId(final Long subjectId) throws JavaRigorException {
        LOGGER.info("Entered deleteSubjectBySubjectId(" + subjectId + ")");

        if (subjectId == null) {
            String errorMessage = "Subject id is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        List<Long> subjectIds = new ArrayList<>();
        subjectIds.add(subjectId);
        if (!validateSubjectIds(subjectIds)) {
            String errorMessage = "Invalid subject id!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        try {
            return subjectDao.deleteSubjectBySubjectId(subjectId);

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while deleting subject!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * get all subject ids.
     *
     * @return
     * @throws JavaRigorException
     */
    public List<Long> getAllActiveSubjectIds() throws JavaRigorException {
        LOGGER.info("Entered getAllActiveSubjectIds()");

        return subjectDao.getAllActiveSubjectIds();
    }
}
