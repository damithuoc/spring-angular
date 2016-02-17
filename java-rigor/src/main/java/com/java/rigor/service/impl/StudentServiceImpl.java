package com.java.rigor.service.impl;

import com.java.rigor.dao.StudentDao;
import com.java.rigor.entity.Student;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.StudentService;
import com.java.rigor.util.EmailSender;
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

import java.util.Calendar;
import java.util.List;

/**
 * Created by sanandasena on 1/11/2016.
 */
@Service("studentService")
@Configurable
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StudentServiceImpl implements StudentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private EmailSender emailSender;

    @Autowired
    private StudentDao studentDao;

    /**
     * save a student
     *
     * @param student
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public void saveStudent(Student student) throws JavaRigorException {

        LOGGER.info("Entered saveStudent(" + student + ")");

        if (student == null) {
            String errorMessage = "The student is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (student.getStudentName() == null || student.getStudentName().isEmpty()) {
            String errorMessage = "The student name  is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

//         set student registration number
        student.setRegistrationNumber(generateStudentRegNumber());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         get the login user
        String username = auth.getName();
//         set login user for created by
        student.setCreatedBy(username);
//         set student registration created date in long(millis)
        student.setCreatedDate(System.currentTimeMillis());

        try {
            studentDao.saveStudent(student);
            LOGGER.info("Saved a student!");

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while saving  a studentS!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);

        }
    }

    //generate student registration number
    private String generateStudentRegNumber() {
        LOGGER.info("Enterd generateStudenteRegNumber()");

        Integer year = Calendar.getInstance().get(Calendar.YEAR);
        Integer defaultStudentCount = 12000;

        List<Long> studentIdList = studentDao.getAllStudentIds();
        Integer studentCount = defaultStudentCount + studentIdList.size();

        return "" + year + "/cs/" + studentCount;
    }

    /**
     * get all students
     *
     * @return
     * @throws JavaRigorException
     */
    public List<Student> getAllActiveStudents() throws JavaRigorException {
        LOGGER.info("Entered getAllActiveStudents()");
        return studentDao.getAllActiveStudents();
    }

    /**
     * update student by using student regiatration number.
     *
     * @param student
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public void updateStudent(Student student) throws JavaRigorException {
        LOGGER.info("Entered updateStudent(" + student + ")");

        if (student == null) {
            String errorMessage = "The student is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (student.getId() == null) {
            String errorMessage = "The student id is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (student.getStudentName() == null || student.getStudentName().isEmpty()) {
            String errorMessage = "The student name  is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        if (student.getRegistrationNumber() == null || student.getRegistrationNumber().isEmpty()) {
            String errorMessage = "The student registration number  is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        student.setUpdatedBy(username);
        student.setUpdatedDate(System.currentTimeMillis());

        try {
            studentDao.updateStudent(student);
            LOGGER.info("updated a student!");

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while updating  a student!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * get a student by using registration number.
     *
     * @param regNumber
     * @return
     * @throws JavaRigorException
     */
    public Student getStudentByUsingRegNumber(final String regNumber) throws JavaRigorException {
        LOGGER.info("Entered getStudentByUsingRegNumber(" + regNumber + ")");

        if (regNumber == null || regNumber.isEmpty()) {
            String errorMessage = "The student registration number  is null or empty!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        return studentDao.getStudentByUsingRegNumber(regNumber);
    }

    /**
     * delete a student by student id.
     *
     * @param id
     * @throws JavaRigorException
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = JavaRigorException.class)
    public Boolean deleteStudent(final Long id) throws JavaRigorException {
        LOGGER.info("Entered deleteStudent(" + id + ")");

        if (id == null) {
            String errorMessage = "The student id is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        Student student = new Student();
        student.setId(id);
        student.setUpdatedDate(System.currentTimeMillis());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        student.setUpdatedBy(username);


        try {
            return studentDao.deleteStudent(student);

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while deleting  a student!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    /**
     * get a student by using student id.
     *
     * @param id
     * @return
     * @throws JavaRigorException
     */
    public Student getStudentById(final Long id) throws JavaRigorException {
        LOGGER.info("Entered getStudentById(" + id + ")");

        if (id == null) {
            String errorMessage = "The student id is null!";
            LOGGER.error(errorMessage);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, null);
        }

        try {
            return studentDao.getStudentById(id);

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while fetching a student!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }

    //TODO can't implement smtp port issue.
    public Boolean sendEmail(String email) throws JavaRigorException {
        LOGGER.info("Entered sendEmail(" + email + ")");

        try {
            emailSender.sendEmail(email);
            return Boolean.TRUE;

        } catch (JavaRigorException jre) {
            String errorMessage = "Error occurred whlie sending email!";
            LOGGER.error(errorMessage, jre);
            throw new JavaRigorException(ErrorCode.INVALID_REQUEST, errorMessage, jre);
        }
    }

    /**
     * get all active student's ids.
     *
     * @return
     * @throws JavaRigorException
     */
    public List<Long> getAllActiveStudentIds() throws JavaRigorException {
        LOGGER.info("Entered getAllActiveStudentIds()");

        return studentDao.getAllActiveStudentIds();
    }
}
