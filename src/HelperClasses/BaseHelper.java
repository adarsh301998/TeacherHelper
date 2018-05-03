package HelperClasses;

import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

public class BaseHelper {

    public static TestDetails testDetails = Bus.getInstance();


    static void updateTestDetails() {
        testDetails = Bus.getInstance();
    }


    public int getMaxRawScore() {
        return ListGenerationHelper.questionList().size();
    }

    public int getNumberOfStudent() {
        updateTestDetails();
        return testDetails.getNumberOfStudent();
    }

    public static int getMaxRawScoreStaticMethod() {
        return ListGenerationHelper.questionList().size();
    }

    public static int getNumberOfStudentStaticMethod() {
        updateTestDetails();
        return testDetails.getNumberOfStudent();
    }

    public static int getItemMaxScore() {
        updateTestDetails();
        return testDetails.getNumberOfStudent();
    }
}
