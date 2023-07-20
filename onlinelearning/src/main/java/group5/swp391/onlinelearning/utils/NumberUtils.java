package group5.swp391.onlinelearning.utils;

import java.util.Random;

public class NumberUtils {
    private NumberUtils() {

    }

    private static final Random random = new Random();

    public static int generateRandomNumber() {
        return 10000 + random.nextInt(90000);
    }
}
