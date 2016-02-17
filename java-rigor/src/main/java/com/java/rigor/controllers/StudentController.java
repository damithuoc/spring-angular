package com.java.rigor.controllers;

import com.java.rigor.entity.Student;
import com.java.rigor.entity.User;
import com.java.rigor.entity.UserRole;
import com.java.rigor.exception.ErrorView;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.StudentService;
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

import java.util.List;

/**
 * Created by sanandasena on 1/6/2016.
 */
@Controller
@RequestMapping(value = "/student")
public class StudentController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String loadStudentHome() throws JavaRigorException {
        LOGGER.info("Entered loadStudentHome()");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User logInUser = userService.getUserByUsername(username);
        if (logInUser == null) {
            return "login";
        }
        return "studentHome";// view of students
    }

    @RequestMapping(value = "/save", method = RequestMethod.PUT)
    public ResponseEntity<ErrorView> saveStudent(@RequestBody Student student) throws JavaRigorException {
        LOGGER.info("Entered saveStudent(" + student + ")");
        try {
            studentService.saveStudent(student);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (JavaRigorException jre) {

            return handleInternalServerError(jre);
        }
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Student>> getAllStudents() throws JavaRigorException {
        LOGGER.info("Entered getAllStudents()");

        List<Student> studentList;
        try {
            studentList = studentService.getAllActiveStudents();
        } catch (JavaRigorException jre) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (studentList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ErrorView> editStudent(@PathVariable("id") Long id, @RequestBody Student student) throws JavaRigorException {
        LOGGER.info("Entered editStudent(" + student + ")");
        student.setId(id);
        if (AuthUser.hasRole(UserRole.ROLE_ADMIN.getRoleName())) {
            try {
                studentService.updateStudent(student);
                return new ResponseEntity<>(HttpStatus.OK);

            } catch (JavaRigorException jre) {

                return handleInternalServerError(jre);
            }
        }

        return handleUnAuthorizedError();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ErrorView> deleteStudent(@PathVariable("id") Long id) throws JavaRigorException {
        LOGGER.info("Entered deleteStudent(" + id + ")");

        if (AuthUser.hasRole(UserRole.ROLE_ADMIN.getRoleName())) {
            try {
                studentService.deleteStudent(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } catch (JavaRigorException jre) {

                return handleInternalServerError(jre);
            }
        }

        return handleUnAuthorizedError();
    }

}


