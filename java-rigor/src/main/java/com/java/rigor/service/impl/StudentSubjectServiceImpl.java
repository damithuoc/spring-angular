package com.java.rigor.service.impl;

import com.java.rigor.dao.StudentSubjectDao;
import com.java.rigor.entity.Student;
import com.java.rigor.entity.Subject;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.StudentService;
import com.java.rigor.service.StudentSubjectService;
import com.java.rigor.service.SubjectService;
import com.java.rigor.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by sanandasena on 2/2/2016.
 */
@Service("studentSubjectService ")
@Configurable
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentSubjectServiceImpl implements StudentSubjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubjectServiceImpl.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentSubjectDao studentSubjectDao;

    /**
     * add subjects for students accounts.
     *
     * @param studentId
     * @param subjectIdList
     * @return
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean addStudentSubjectsByUsingStudentId(final Long studentId, final List<Long> subjectIdList) throws JavaRigorException {
        LOGGER.info("Entered addStudentSubjectsByUsingStudentId(" + studentId + ", " + subjectIdList + ")");

        if (studentId == null) {
            String errorMessage = "Student id null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (subjectIdList == null || subjectIdList.isEmpty()) {
            String errorMessage = "Subject id list null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        List<Long> studentIdList = studentService.getAllActiveStudentIds();

        if (!studentIdList.contains(studentId)) {
            String errorMessage = "Invalid student id!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (!subjectService.validateSubjectIds(subjectIdList)) {
            String errorMessage = "Invalid subject id list!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        try {
            return studentSubjectDao.addStudentSubjectsByUsingStudentId(studentId, subjectIdList);

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while saving subjects for student account!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * get students with subjects.
     *
     * @return a student collection.
     */
    public Collection<Student> getAllStudentWithSubjects() throws JavaRigorException {
        LOGGER.info("Entered getAllStudentWithSubjects()");

        return studentSubjectDao.getAllStudentWithSubjects();
    }


    /**
     * @param subjectList
     * @param studentId
     * @return Boolean value, if editing success return Boolean.TRUE
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean editStudentSubjects(final Long studentId, final List<Subject> subjectList) throws JavaRigorException {
        LOGGER.info("Entered editStudentSubjects(" + subjectList + "," + studentId + ")");

        if (studentId == null) {
            String errorMessage = "Student id null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        // get student from db using student id.
        Student dbStudent = getStudentWithSubjects(studentId);

        if (dbStudent == null) {
            String errorMessage = "Invalid student id null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (subjectList == null || subjectList.isEmpty()) {
            String errorMessage = "Subject list null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        Boolean isInValidSubject = Boolean.FALSE;
        for (Subject subject : subjectList) {
            if (subject.getId() == null || subject.getIsActive() == null) {
                isInValidSubject = Boolean.TRUE;
                break;
            }
        }
        if (isInValidSubject) {
            String errorMessage = "Invalid subject!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        // get db student subject list
        List<Subject> studentSubjectList = dbStudent.getSubjectList();

        // add all db student subject ids into new list with
        // valid isActive.
        List<Long> subjectIdList = subjectList
                .stream()
                .filter(Subject::getIsActive)
                .map(Subject::getId)
                .collect(Collectors.toList());

        if (studentSubjectList == null || studentSubjectList.isEmpty()) {
            try {
                // if add subject successful this will return TRUE.
                // if add subject throw an error this will return FALSE.
                return addStudentSubjectsByUsingStudentId(studentId, subjectIdList);

            } catch (JavaRigorException jre) {
                return Boolean.FALSE;
            }

        } else {

            // get db student subject id list
            List<Long> studentSubjectIdList = studentSubjectList
                    .stream()
                    .map(Subject::getId)
                    .collect(Collectors.toList());

            // create new save subject id list
            List<Long> saveNewSubjectIds = subjectList
                    .stream()
                    .filter(subject -> subject.getIsActive() && !studentSubjectIdList.contains(subject.getId()))
                    .map(Subject::getId)
                    .collect(Collectors.toList());

            Boolean editStatus = Boolean.FALSE;

            if (!saveNewSubjectIds.isEmpty()) {
                try {
                    // if invalid subject id set this will throws an error
                    // if throw an error return FALSE.
                    editStatus = addStudentSubjectsByUsingStudentId(studentId, saveNewSubjectIds);

                } catch (JavaRigorException jre) {
                    return Boolean.FALSE;
                }
            }

            // get delete subject id list
            List<Long> deleteSubjectIdList = subjectList
                    .stream()
                    .filter(subject -> !subject.getIsActive() && studentSubjectIdList.contains(subject.getId()))
                    .map(Subject::getId)
                    .collect(Collectors.toList());

            if (!deleteSubjectIdList.isEmpty()) {
                try {
                    editStatus = deleteStudentSubject(studentId, deleteSubjectIdList);

                } catch (JavaRigorException jre) {
                    return Boolean.FALSE;
                }
            }

            if (saveNewSubjectIds.isEmpty() && deleteSubjectIdList.isEmpty()) {
                return editStatus;
            }
            return editStatus;
        }
    }

    /**
     * get student with subject list.
     *
     * @param studentId
     * @return student object
     */
    public Student getStudentWithSubjects(final Long studentId) throws JavaRigorException {
        LOGGER.info("Entered getStudentWithSubjects(" + studentId + ")");

        if (studentId == null) {
            String errorMessage = "Student id null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }
        return studentSubjectDao.getStudentWithSubjects(studentId);
    }

    /**
     * delete student subject by using student id and
     * subject ids.
     *
     * @param studentId
     * @param subjectIdList
     * @return Boolean if execution success return TRUE
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean deleteStudentSubject(final Long studentId, final List<Long> subjectIdList) throws JavaRigorException {
        LOGGER.info("Entered deleteStudentSubject(" + studentId + "," + subjectIdList + ")");

        if (studentId == null) {
            String errorMessage = "Student id null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (subjectIdList == null || subjectIdList.isEmpty()) {
            String errorMessage = "Subject id null or emprty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        // get student
        Student student = getStudentWithSubjects(studentId);

        if (student == null) {
            String errorMessage = "Invalid student id !";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        // validate subject code
        List<Subject> subjectList = student.getSubjectList();
        if (subjectList == null || subjectList.isEmpty()) {
            String errorMessage = "Student subject list null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        List<Long> dbSubjectIdList = subjectList
                .stream()
                .map(Subject::getId)
                .collect(Collectors.toList());

        Boolean isInValidSubject = Boolean.FALSE;
        for (Long subjectId : subjectIdList) {
            if (!dbSubjectIdList.contains(subjectId)) {
                isInValidSubject = Boolean.TRUE;
                break;
            }
        }

        if (isInValidSubject) {
            String errorMessage = "Invalid subject id!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }
        return studentSubjectDao.deleteStudentSubject(studentId, subjectIdList);
    }

}
