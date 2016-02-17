package com.java.rigor.test.base;

import com.java.rigor.constants.Constants;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by sanandasena on 1/19/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public class BaseTestClass {

    // set the security details for unit tets
    @BeforeClass
    public static void setUp() {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(Constants.SYSTEM_USER, Constants.SYSTEM_ROLE));

    }

}
