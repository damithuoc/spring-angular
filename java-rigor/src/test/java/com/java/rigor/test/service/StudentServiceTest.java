package com.java.rigor.test.service;

import com.java.rigor.constants.Constants;
import com.java.rigor.entity.Student;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.StudentService;
import com.java.rigor.test.base.BaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

/**
 * Created by sanandasena on 1/19/2016.
 */
@WebAppConfiguration
public class StudentServiceTest extends BaseTestClass {

    @Autowired
    private StudentService studentService;

    @Test
    public void saveStudentTest() throws Exception {
        //test with null student
        try {
            studentService.saveStudent(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty student
        Student student = new Student();
        try {
            studentService.saveStudent(student);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with student name
        student.setStudentName("test case");
        studentService.saveStudent(student);

        //test with student not required fields
        Student saveStudent = new Student();
        saveStudent.setStudentName("test last");
        saveStudent.setEmail("studentttt@test.com");
        saveStudent.setAddress("test address");
        studentService.saveStudent(saveStudent);
    }

    @Test
    public void testGetAllStudents() throws Exception {

        List<Student> fetchedStudents;
        fetchedStudents = studentService.getAllActiveStudents();
        Assert.assertFalse(fetchedStudents == null);
    }

    @Test
    public void testGetStudentByRegNumber() throws Exception {
        // test with null reg number
        try {
            studentService.getStudentByUsingRegNumber(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid reg number
        Student fetchedStudent = new Student();
        fetchedStudent = studentService.getStudentByUsingRegNumber("test");
        Assert.assertFalse(fetchedStudent != null);

        // test with correct reg number
        fetchedStudent = studentService.getStudentByUsingRegNumber("2016/cs/12014");
        Assert.assertFalse(fetchedStudent == null);
    }

    @Test
    public void testUpdateStudent() throws Exception {
        //test with null student
        try {
            studentService.updateStudent(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty student
        Student updateStudent = new Student();
        try {
            studentService.updateStudent(updateStudent);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with null student id;
        updateStudent.setId(null);
        try {
            studentService.saveStudent(updateStudent);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with student name null and invalid student id
        updateStudent.setId(96636042222L); // set the invalid student id
        updateStudent.setStudentName(null);
        try {
            studentService.updateStudent(updateStudent);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with student name empty
        updateStudent.setStudentName(Constants.EMPTY_STRING);
        try {
            studentService.updateStudent(updateStudent);
        } catch (JavaRigorException jre) {
        }

        // test with reg number null
        updateStudent.setStudentName("upStudent");
        updateStudent.setRegistrationNumber(null);
        try {
            studentService.updateStudent(updateStudent);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with reg number empty
        updateStudent.setRegistrationNumber(Constants.EMPTY_STRING);
        try {
            studentService.updateStudent(updateStudent);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        //set update student reg number
        updateStudent.setRegistrationNumber("2016/cs/12024");
        // test with invalid student id and update email
        updateStudent.setEmail("update@update.com");
        // fetch before updated student
        Student fetchedStudent = studentService.getStudentByUsingRegNumber("2016/cs/12024");
        studentService.updateStudent(updateStudent);
        Thread.sleep(1000);// wiating for update valid
        Assert.assertNotSame(fetchedStudent.getEmail(), updateStudent.getEmail(), "Expected email addresses to be equal!");

        // test with valid student  details
        fetchedStudent = studentService.getStudentByUsingRegNumber("2016/cs/12024");
        updateStudent.setId(1453196636042222L);
        studentService.updateStudent(updateStudent);
        Thread.sleep(1000);
        Assert.assertNotSame(fetchedStudent.getEmail(), updateStudent.getEmail(), "Expected email addresses to can't be equal");

    }

    @Test
    public void testDisableStudentByStudentId() throws Exception {
        // test with null student id
        try {
            studentService.deleteStudent(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid student id
        Boolean deleteStatus = studentService.deleteStudent(3432433L);
        Thread.sleep(100);
        Assert.assertFalse("Expected student deleted!", deleteStatus);

        // test with valid student id
        deleteStatus = studentService.deleteStudent(1453196381960590L);
        Thread.sleep(100);
        Assert.assertTrue("Expected student not deleted!", deleteStatus);
    }

    @Test
    public void testGetStudentById() throws Exception {
        // test with null id.
        try {
            studentService.getStudentById(null);
            Assert.fail("Expected en error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid student id.
        Student fetchStudent = studentService.getStudentById(895385L);
        Assert.assertFalse("Fetched student can't be null!", fetchStudent != null);

        // test with valid student id.
        fetchStudent = studentService.getStudentById(1453430365518220L);
        Assert.assertFalse("Fetched student can be null!", fetchStudent == null);
    }

    @Test
    public void testSendEmail() throws Exception {
        Boolean sentStatus = studentService.sendEmail("sajithnandasena.uoc@gmail.com");
        Assert.assertTrue("Unsuccessful!", !sentStatus);
    }

}
