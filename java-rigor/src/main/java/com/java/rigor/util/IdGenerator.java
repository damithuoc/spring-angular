package com.java.rigor.util;

import com.java.rigor.constants.Constants;

import java.util.Random;

/**
 * Created by sanandasena on 1/11/2016.
 */
public class IdGenerator {
    private IdGenerator() {
        //This is a utility class
    }

    public static Long generateId() {

        long millis = System.currentTimeMillis();

        int randomInt = getRandomThreeDigitNumber();
        return Long.valueOf(millis + Constants.EMPTY_STRING + randomInt);

    }

    private static int getRandomThreeDigitNumber() {
        int low = 100;
        int high = 1000;

        Random random = new Random();

        return random.nextInt(high - low) + low;
    }
}
