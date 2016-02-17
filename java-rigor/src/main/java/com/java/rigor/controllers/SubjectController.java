package com.java.rigor.controllers;

import com.java.rigor.entity.Subject;
import com.java.rigor.entity.SubjectCode;
import com.java.rigor.entity.User;
import com.java.rigor.entity.UserRole;
import com.java.rigor.exception.ErrorView;
import com.java.rigor.exception.JavaRigorException;
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

import java.util.List;

/**
 * Created by sanandasena on 1/29/2016.
 */
@Controller
@RequestMapping(value = "/subject")
public class SubjectController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String loadSubject() throws JavaRigorException {
        LOGGER.info("Entered loadSubject()");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User logInUser = userService.getUserByUsername(username);
        if (logInUser == null) {
            return "login";
        }
        return "subject";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseEntity<ErrorView> saveSubject(@RequestBody Subject subject) throws JavaRigorException {
        LOGGER.info("Entered saveSubject(" + subject + ")");

        try {
            subjectService.saveSubject(subject);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (JavaRigorException jre) {

            return handleInternalServerError(jre);
        }

    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Subject>> getAllSubjects() throws JavaRigorException {
        LOGGER.info("Entered getAllActiveSubjects()");

        List<Subject> subjectList = subjectService.getAllActiveSubjects();
        if (subjectList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(subjectList, HttpStatus.OK);
    }

    @RequestMapping(value = "/prefix", method = RequestMethod.GET)
    public ResponseEntity<List<SubjectCode>> getAllSubjectPrefixes() throws JavaRigorException {
        LOGGER.info("Entered getAllSubjectPrefixes()");
        List<SubjectCode> subjectCodeList = subjectService.getAllSubjectPrefixes();
        return new ResponseEntity<>(subjectCodeList, HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<ErrorView> editSubject(@PathVariable("id") Long id, @RequestBody Subject subject) throws JavaRigorException {
        LOGGER.info("Entered editSubject(" + subject + ")");
        subject.setId(id);
        if (AuthUser.hasRole(UserRole.ROLE_ADMIN.getRoleName())) {

            try {
                subjectService.updateSubject(subject);
                return new ResponseEntity<>(HttpStatus.OK);

            } catch (JavaRigorException jre) {

                return handleInternalServerError(jre);
            }
        }
        return handleUnAuthorizedError();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<ErrorView> deleteSubject(@PathVariable("id") Long id) throws JavaRigorException {
        LOGGER.info("Entered deleteStudent(" + id + ")");

        if (AuthUser.hasRole(UserRole.ROLE_ADMIN.getRoleName())) {

            try {
                subjectService.deleteSubjectBySubjectId(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);

            } catch (JavaRigorException jre) {

                return handleInternalServerError(jre);
            }
        }

        return handleUnAuthorizedError();
    }

}
