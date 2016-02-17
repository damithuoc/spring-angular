package com.java.rigor.test.dao;

import com.java.rigor.dao.SubjectDao;
import com.java.rigor.entity.Subject;
import com.java.rigor.test.base.BaseTestClass;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by sanandasena on 2/1/2016.
 */
public class SubjectDaoTest extends BaseTestClass {

    @Autowired
    private SubjectDao subjectDao;

    @Test
    public void testGetSubjectById() throws Exception {
        // test with null id.
        Subject subject = subjectDao.getSubjectBySubjectId(null);
        Assert.assertFalse("Expected subject can not be null!", subject != null);

        // test with invalid subject id
        subject = subjectDao.getSubjectBySubjectId(9548795L);
        Assert.assertFalse("Expected subject can not be null!", subject != null);

        // test with valid subject id.
        subject = subjectDao.getSubjectBySubjectId(1453955356014891L);
        Assert.assertNotNull("Expected subject can be null!", subject);
    }
}
