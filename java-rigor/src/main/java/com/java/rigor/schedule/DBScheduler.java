package com.java.rigor.schedule;

import com.java.rigor.dao.StudentDao;
import com.java.rigor.exception.JavaRigorException;
import com.java.rigor.util.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * Created by sanandasena on 1/22/2016.
 */
@Component("DBScheduler")
public class DBScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DBScheduler.class);

    @Autowired
    private StudentDao studentDao;

    /**
     * delete old student data.
     *
     * @throws JavaRigorException
     */
    public void deleteOldMysqlData() throws JavaRigorException {
        LOGGER.info("Entered deleteOldStudentData()");

        try {

            Calendar calendar = Calendar.getInstance();

            Integer currentYear = calendar.get(Calendar.YEAR);

            Calendar tenYearsBackYear = Calendar.getInstance();
            tenYearsBackYear.set(Calendar.YEAR, (currentYear - 10));

            Long lastUpdatedDate = tenYearsBackYear.getTimeInMillis();

            studentDao.deleteOldStudentData(lastUpdatedDate);
            LOGGER.info("===================== Deleted old student's old data =====================");

        } catch (DataAccessException dae) {
            String errorMessage = "database error occurred while deleting student's old data!";
            LOGGER.error(errorMessage, dae);
            throw new JavaRigorException(ErrorCode.DATABASE_ERROR, errorMessage, dae);
        }
    }
}

