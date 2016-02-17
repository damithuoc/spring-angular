package com.java.rigor.controllers;

import com.java.rigor.entity.Student;
import com.java.rigor.entity.Subject;
import com.java.rigor.entity.User;
import com.java.rigor.entity.UserRole;
import com.java.rigor.exception.ErrorView;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.StudentSubjectService;
import com.java.rigor.service.SubjectService;
import com.java.rigor.service.UserService;
import com.java.rigor.to.AuthUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 2/2/2016.
 */
@Controller
@RequestMapping(value = "/student/subject")
public class StudentSubjectController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentSubjectController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private StudentSubjectService studentSubjectService;

    @RequestMapping(method = RequestMethod.GET)
    public String loadStudentSubject() throws JavaRigorException {
        LOGGER.info("Entered loadStudentSubject()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User logInUser = userService.getUserByUsername(username);
        if (logInUser == null) {
            return "login";
        }
        return "studentSubject";
    }

    @RequestMapping(value = "/subjects", method = RequestMethod.GET)
    public ResponseEntity<List<Subject>> getAllSubjects() throws JavaRigorException {
        LOGGER.info("Entered getAllActiveSubjects()");

        List<Subject> subjectList = subjectService.getAllActiveSubjects();
        if (subjectList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(subjectList, HttpStatus.OK);
    }

    @RequestMapping(value = "/students", method = RequestMethod.GET)
    public ResponseEntity<Collection<Student>> getAllStudents() throws JavaRigorException {
        LOGGER.info("Entered getAllStudents()");

        Collection<Student> studentList = studentSubjectService.getAllStudentWithSubjects();
        if (studentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @RequestMapping(value = "/add/{id}", method = RequestMethod.POST)
    public ResponseEntity<ErrorView> addSubjectsToStudents(@PathVariable("id") Long studentId,
                                                           @RequestBody List<Long> subjectIdList) throws JavaRigorException {
        LOGGER.info("Entered addSubjectsToStudents(" + studentId + "," + subjectIdList + ")");

        try {
            studentSubjectService.addStudentSubjectsByUsingStudentId(studentId, subjectIdList);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (JavaRigorException jre) {
            return handleInternalServerError(jre);
        }
    }

    @RequestMapping(value = "/edit/{studentId}", method = RequestMethod.POST)
    public ResponseEntity<ErrorView> editStudentSubjects(@PathVariable("studentId") Long studentId, @RequestBody List<Subject> subjectList) throws JavaRigorException {
        LOGGER.info("Entered editStudentSubjects(" + studentId + ", " + subjectList + ")");

        if (AuthUser.hasRole(UserRole.ROLE_ADMIN.getRoleName())) {

            try {
                studentSubjectService.editStudentSubjects(studentId, subjectList);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } catch (JavaRigorException jre) {
                return handleInternalServerError(jre);
            }

        }
        return handleUnAuthorizedError();
    }

}
