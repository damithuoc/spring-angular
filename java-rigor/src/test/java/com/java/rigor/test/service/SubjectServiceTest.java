package com.java.rigor.test.service;

import com.java.rigor.constants.Constants;
import com.java.rigor.entity.Subject;
import com.java.rigor.entity.SubjectCode;
import com.java.rigor.entity.SubjectCodePrefix;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.service.SubjectService;
import com.java.rigor.test.base.BaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by work on 1/30/16.
 */

@WebAppConfiguration
public class SubjectServiceTest extends BaseTestClass {

    @Autowired
    private SubjectService subjectService;

    @Test
    public void testAddSubjects() throws Exception {
        // test with null subject list
        try {
            subjectService.addSubjects(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty subject list
        try {
            subjectService.addSubjects(new ArrayList<>());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid subject
        List<Subject> invalidSubjectList = new ArrayList<>();
        Subject invalidSubject = new Subject();
        invalidSubjectList.add(invalidSubject);
        try {
            subjectService.addSubjects(invalidSubjectList);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty  null subject code

        invalidSubject.setSubjectCode(null);
        invalidSubjectList.add(invalidSubject);
        try {
            subjectService.addSubjects(invalidSubjectList);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid subject code and null subject name
        String subjectCode1 = SubjectCodePrefix.CS.getCode().concat("3006");

//        Subject invalidSubject2 = new Subject();
//        invalidSubject2.setSubjectCode(subjectCode1);
//        invalidSubject2.setSubjectName(null);
//
//        List<Subject> invalidSubjectList2 = new ArrayList<>();
//        invalidSubjectList2.add(invalidSubject2);
//
//        try {
//            subjectService.addSubjects(invalidSubjectList2);
//            Assert.fail("Expected an error!");
//        } catch (JavaRigorException jre) {
//        }
//
//        // test with valid subject code and empty subject name
//        invalidSubject2.setSubjectName(Constants.EMPTY_STRING);
//        invalidSubjectList2.add(invalidSubject2);
//        try {
//            subjectService.addSubjects(invalidSubjectList2);
//            Assert.fail("Expected an error!");
//        } catch (JavaRigorException jre) {
//        }
//
//        // test with valid subject
//        Subject validSubject1 = new Subject();
//        validSubject1.setSubjectCode(subjectCode1);
//        validSubject1.setSubjectName("Advanced Computer Systems");
//
//        List<Subject> validSubjectList1 = new ArrayList<>();
//        validSubjectList1.add(validSubject1);
//
//        Boolean subjectSavedStatus = subjectService.addSubjects(validSubjectList1);
//        Assert.assertTrue("Subjects saving unsuccessful!", subjectSavedStatus);
//
//        // test with valid subject with invalid subject
//
//        List<Subject> validSubjectList3 = new ArrayList<>();
//        Subject validSubject2 = new Subject();
//        validSubject2.setSubjectCode(SubjectCodePrefix.CS.getCode().concat("3004"));
//        validSubject2.setSubjectName("APT");
//        validSubjectList3.add(validSubject2);
//
//        validSubjectList3.add(new Subject());
//
//        try {
//            subjectService.addSubjects(validSubjectList3);
//            Assert.fail("Expected an error!");
//        } catch (JavaRigorException jre) {
//        }
    }

    @Test
    public void testSaveSubject() throws Exception {
        // test with null subject
        try {
            subjectService.saveSubject(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty subject
        try {
            subjectService.saveSubject(new Subject());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with null subject code
        Subject invalidSubject = new Subject();
        invalidSubject.setSubjectCode(null);
        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with subject code empty
        invalidSubject.setSubjectCode(new SubjectCode());
        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test  with empty subject code prefix
        SubjectCode invalidSubjectCode = new SubjectCode();
        invalidSubjectCode.setPrefix(Constants.EMPTY_STRING);

        invalidSubject.setSubjectCode(invalidSubjectCode);
        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid subject code prefix and invalid subject code number
        String subjectCodePrefix = SubjectCodePrefix.CS.getCode();
        invalidSubjectCode.setPrefix(subjectCodePrefix);
        invalidSubjectCode.setCodeNumber(null);

        invalidSubject.setSubjectCode(invalidSubjectCode);

        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        invalidSubjectCode.setCodeNumber(0);
        invalidSubject.setSubjectCode(invalidSubjectCode);
        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        invalidSubjectCode.setCodeNumber(444);
        invalidSubject.setSubjectCode(invalidSubjectCode);
        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }


        invalidSubjectCode.setCodeNumber(9999999);
        invalidSubject.setSubjectCode(invalidSubjectCode);
        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with subject name null and valid subject code
        SubjectCode validSubjectCode01 = new SubjectCode();
        validSubjectCode01.setPrefix(subjectCodePrefix);
        validSubjectCode01.setCodeNumber(1001);

        invalidSubject.setSubjectCode(validSubjectCode01);
        invalidSubject.setSubjectName(null);

        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty subject name
        invalidSubject.setSubjectName(Constants.EMPTY_STRING);
        try {
            subjectService.saveSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid subject
        Subject validSubject = new Subject();
        validSubject.setSubjectCode(validSubjectCode01);
        validSubject.setSubjectName("Embed systems");

        Boolean savedStatus = subjectService.saveSubject(validSubject);
        Assert.assertFalse("Subject saved unsuccessful!", !savedStatus);

    }

    @Test
    public void testUpdateSubject() throws Exception {
        // test with null
        try {
            subjectService.updateSubject(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty subject and subject id
        try {
            subjectService.updateSubject(new Subject());
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with subject code null with invalid id
        Subject invalidSubject = new Subject();
        invalidSubject.setId(34345353L);

        try {
            subjectService.updateSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test  with empty subjectCode
        invalidSubject.setSubjectCode(new SubjectCode());
        try {
            subjectService.updateSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid subject code prefix.
        SubjectCode invalidSubjectCode = new SubjectCode();
        invalidSubjectCode.setPrefix(Constants.EMPTY_STRING);

        try {
            subjectService.updateSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid subject code prefix
        invalidSubjectCode.setPrefix("test");
        invalidSubject.setSubjectCode(invalidSubjectCode);
        try {
            subjectService.updateSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test  with valid subject code prefix and invalid subject code number
        invalidSubjectCode.setPrefix(SubjectCodePrefix.AM.getCode());
        invalidSubjectCode.setCodeNumber(0);
        invalidSubject.setSubjectCode(invalidSubjectCode);
        try {
            subjectService.updateSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with subject name null
        invalidSubjectCode.setCodeNumber(2000);
        invalidSubject.setSubjectName(null);
        invalidSubject.setSubjectCode(invalidSubjectCode);
        try {
            subjectService.updateSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with empty subject name
        invalidSubject.setSubjectName(Constants.EMPTY_STRING);
        invalidSubject.setSubjectCode(invalidSubjectCode
        );
        try {
            subjectService.updateSubject(invalidSubject);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid subject
        List<Subject> dbSubjectList = subjectService.getAllActiveSubjects();
        // assume dbSubjectList is not empty.
        Subject validSubject = dbSubjectList.get(0);
        validSubject.setSubjectName("test update");

        // TODO this is not a best way :)
        Boolean updateStatus = subjectService.updateSubject(validSubject);
        Assert.assertFalse("Expected update not successful!", !updateStatus);

    }

    @Test
    public void testDeleteSubjectBySubjectId() throws Exception {
        // test with null
        try {
            subjectService.deleteSubjectBySubjectId(null);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with invalid subject id
        try {
            subjectService.deleteSubjectBySubjectId(89458934L);
            Assert.fail("Expected an error!");
        } catch (JavaRigorException jre) {
        }

        // test with valid subject id.
        Boolean deleteStatus = subjectService.deleteSubjectBySubjectId(1454317966919284L);
        Assert.assertTrue("Subject deleted unsuccessful!", deleteStatus);
    }
}
