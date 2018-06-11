package calculations;

import HelperClasses.ListGenerationHelper;

import java.util.List;

public class MathCutOffScore extends CalculationBase {


    /*Add all question difficulty level to get cutOff score*/
    public static int getCutOffScore() {

        List<Integer> questionScore = ListGenerationHelper.getQuestionScore();
        int numberOfStudent = getNumberOfStudents();
        double sum = 0.0;
        int cutOffScore = 0;
        for (Integer integer : questionScore) {
            double difficultyLevel = (double) integer / numberOfStudent;
            sum += difficultyLevel;
        }
        cutOffScore = (int) Math.round(sum);

        return cutOffScore;
    }


}
