package group5.swp391.onlinelearning.utils;

import java.util.Random;

public class NumberUtils {
    public static int generateRandomNumber() {
        Random random = new Random();
        return 10000 + random.nextInt(90000);
    }
}
