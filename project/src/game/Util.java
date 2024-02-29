package game;

import java.util.Random;

/**
 *  A general utility class used to perform calculations
 */
public class Util {
    /**
     * Randomly generates an integer within 0 to 100 and performs a check with the inputted rates
     * and outputs according its success or failure
     *
     * @param successRate the odds of the output being a success
     * @return true if the output is a success, false otherwise
     */
    public static boolean isSuccess(int successRate){
        if(successRate == 100){
            return true;
        }
        if (successRate == 0){
            return false;
        }
        Random random = new Random();
        int number = random.nextInt(101);
        return number <= successRate;
    }
}
