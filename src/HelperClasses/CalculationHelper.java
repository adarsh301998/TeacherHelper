package HelperClasses;

import java.util.List;

public class CalculationHelper {

    public static Double getMeanScore() {

        List<Integer> totalmarks = ListGenerationHelper.studentsTotalMarks();
        int sum = 0;
        for (Integer integer : totalmarks) {
            sum += integer;
        }
        double mean = (double) sum / totalmarks.size();
        return mean;
    }

}
