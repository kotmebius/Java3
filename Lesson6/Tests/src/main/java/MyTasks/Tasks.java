package MyTasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;


public class Tasks {
    private static final Logger LOGGER = LogManager.getLogger(Tasks.class);


    public static int[] taskTwoMethod(int[] arrayToWork) throws RuntimeException {
        int positionOfFour = -1;
        for (int i = 0; i < arrayToWork.length; i++) {
            if (arrayToWork[i] == 4) {
                positionOfFour = i;
            }
        }
        if (positionOfFour > 0) {
            return (Arrays.copyOfRange(arrayToWork, positionOfFour + 1, arrayToWork.length));
        } else {
            throw new RuntimeException("No four found");
        }
    }

    public static boolean taskThreeMethod(int[] arrayToWork) {
        for (int i = 0; i < arrayToWork.length; i++) {
            if (arrayToWork[i] == 1 || arrayToWork[i] == 1) {
                return true;
            }
        }
        return false;
    }

}
