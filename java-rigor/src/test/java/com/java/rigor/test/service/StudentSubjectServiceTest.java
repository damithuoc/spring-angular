package com.java.rigor.test.service;

import com.java.rigor.entity.Student;
import com.java.rigor.entity.Subject;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.StudentSubjectService;
import com.java.rigor.test.base.BaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by sanandasena on 2/2/2016.
 */
@WebAppConfiguration
public class StudentSubjectServiceTest extends BaseTestClass {

    @Autowired
    private StudentSubjectService studentSubjectService;

    @Test
    public void testGetAllStudentsWithSubjects() throws Exception {
        // Assume database student table is not empty.
        Collection<Student> studentCollection = studentSubjectService.getAllStudentWithSubjects();
        Assert.assertNotNull(studentCollection);
    }

    @Test
    public void testGetStudentWithSubjects() throws Exception {
        // test with null student id
        try {
            studentSubjectService.getStudentWithSubjects(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid student id
        Student student = studentSubjectService.getStudentWithSubjects(99999L);
        Assert.assertFalse("Expected student is not null!", student != null);

        // test with valid student id.
        student = studentSubjectService.getStudentWithSubjects(1452763769795662L);
        Assert.assertNotNull(student);
    }

    @Test
    public void testDeleteStudentSubject() throws Exception {
        // test with both student id and subject id null.
        try {
            studentSubjectService.deleteStudentSubject(null, null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid student id and null subject id
        try {
            studentSubjectService.deleteStudentSubject(9999L, null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with null student id and invalid subject id list
        try {
            studentSubjectService.deleteStudentSubject(null, new ArrayList<>());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid student id and invalid subject id
        try {
            studentSubjectService.deleteStudentSubject(8888L, new ArrayList<>());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid student but not subjects in account
        try {
            studentSubjectService.deleteStudentSubject(222222288L, new ArrayList<>());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid student with subjects and invalid subject id
        List<Long> invalidSubjectIdList = new ArrayList<>();
        invalidSubjectIdList.add(45454L);
        Long studentId = 1452763769795662L;
        try {
            studentSubjectService.deleteStudentSubject(studentId, invalidSubjectIdList);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid student and invalid subject ids with valid subject id
        invalidSubjectIdList.add(1453955356014891L);
        try {
            studentSubjectService.deleteStudentSubject(studentId, invalidSubjectIdList);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid student id and valid subject id list

        List<Long> validSubjectIdList = new ArrayList<>();
        validSubjectIdList.add(1454049981416561L);
        validSubjectIdList.add(1454384941438585L);

        Boolean deleteStatus = studentSubjectService.deleteStudentSubject(1452763769795662L, validSubjectIdList);
        Assert.assertTrue("Student subject delete unsuccessful!", deleteStatus);

    }

    @Test
    public void testEditStudentSubject() throws Exception {
        // test with both  student id and subject list null.
        try {
            studentSubjectService.editStudentSubjects(null, null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid student id and null subject list
        try {
            studentSubjectService.editStudentSubjects(999L, null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid student id and null subject list

        Long emptySubjectsValidStudentId = 222222288L;
        try {
            studentSubjectService.editStudentSubjects(emptySubjectsValidStudentId, null);
            Assert.fail("Expected an error!");

        } catch (JavaRigorException jre) {
        }

        // test with valid student id and empty subject list
        try {
            studentSubjectService.editStudentSubjects(emptySubjectsValidStudentId, new ArrayList<>());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid subjects
        List<Subject> invalidSubjectList = new ArrayList<>();
        Subject invalidSubject = new Subject();
        invalidSubjectList.add(invalidSubject);

        try {
            studentSubjectService.editStudentSubjects(emptySubjectsValidStudentId, invalidSubjectList);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid subject and null isActive
        invalidSubject.setId(8888L);
        List<Subject> invalidSubjectList2 = new ArrayList<>();
        invalidSubjectList2.add(invalidSubject);
        try {
            studentSubjectService.editStudentSubjects(emptySubjectsValidStudentId, invalidSubjectList2);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        invalidSubject.setIsActive(Boolean.FALSE);
        List<Subject> invalidSubjectList3 = new ArrayList<>();
        invalidSubjectList3.add(invalidSubject);

        Boolean editedStatus = studentSubjectService.editStudentSubjects(emptySubjectsValidStudentId, invalidSubjectList3);
        Assert.assertFalse("Student subject editing successful!", editedStatus);

        // test with invalid subject id valid isActive
        Subject invalidSubject2 = new Subject();
        invalidSubject2.setId(8888L);
        invalidSubject2.setIsActive(Boolean.TRUE);
        List<Subject> invalidSubjectList4 = new ArrayList<>();
        invalidSubjectList4.add(invalidSubject2);
        editedStatus = studentSubjectService.editStudentSubjects(emptySubjectsValidStudentId, invalidSubjectList4);
        Assert.assertFalse("Student subject editing successful!", editedStatus);

        // test with valid subject
        Subject validSubject1 = new Subject();
        validSubject1.setId(1453955356014891L);
        validSubject1.setIsActive(Boolean.TRUE);

        List<Subject> validSubjectList1 = new ArrayList<>();
        validSubjectList1.add(validSubject1);

        // TODO test with subject with student and subject without student.
//        editedStatus = studentSubjectService.editStudentSubjects(emptySubjectsValidStudentId, validSubjectList1);
//        Assert.assertTrue("Student subject editing unsuccessful!", editedStatus);

        // test with valid student with subjects and invalid subject list
        // ignore testing with null subjects
        Long subjectsWithValidStudentId = 1453171795080836L;

        Subject invalidSubject3 = new Subject();
        // test with invalid subject id
        invalidSubject3.setId(99999L);
        invalidSubject3.setIsActive(Boolean.TRUE);

        List<Subject> invalidSubjectList5 = new ArrayList<>();
        invalidSubjectList5.add(invalidSubject3);

        editedStatus = studentSubjectService.editStudentSubjects(subjectsWithValidStudentId, invalidSubjectList5);
        Assert.assertFalse("Student subject editing successful!", editedStatus);

        // test with duplication subject id and isActive TRUE
        Subject validSubject2 = new Subject();
        validSubject2.setId(1454049981416561L);
        validSubject2.setIsActive(Boolean.TRUE);

        List<Subject> validSubjectList2 = new ArrayList<>();
        validSubjectList2.add(validSubject2);

        editedStatus = studentSubjectService.editStudentSubjects(subjectsWithValidStudentId, validSubjectList2);
        Assert.assertFalse("Student subject editing successful!", editedStatus);

        Thread.sleep(1000);
        // test with duplication subject id and isActive FALSE
        Subject validSubject3 = new Subject();
        validSubject3.setId(1454049981416561L);
        validSubject3.setIsActive(Boolean.FALSE);

        List<Subject> validSubjectList3 = new ArrayList<>();
        validSubjectList3.add(validSubject3);

        editedStatus = studentSubjectService.editStudentSubjects(subjectsWithValidStudentId, validSubjectList3);
        Assert.assertFalse("Student subject editing successful!", editedStatus);
    }
}
