package com.java.rigor.test.dao;

import com.java.rigor.dao.StudentDao;
import com.java.rigor.test.base.BaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by sanandasena on 1/27/2016.
 */
public class StudentDaoTest extends BaseTestClass {

    @Autowired
    private StudentDao studentDao;

    @Test
    public void testGetAllStudentIds() throws Exception {
        // Assume database student table  is not empty
        List<Long> studentIds = studentDao.getAllStudentIds();
        Assert.assertTrue("Expected student id list is empty!", !studentIds.isEmpty());
    }


}
