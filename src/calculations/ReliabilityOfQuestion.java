package calculations;

import HelperClasses.ArrayHelper;
import HelperClasses.Constants;

import java.util.ArrayList;

public class ReliabilityOfQuestion extends CalculationBase {


    int array[][];
    int QUESTION_COLUMN_START_INDEX = 1;
    int QUESTION_COLUMN_END_INDEX;
    int STUDENT_RAW_SCORE_COLUMN_INDEX;
    int STUDENT_RAW_SCORE_START_ROW_INDEX = 1;
    int STUDENT_RAW_SCORE_END_ROW_INDEX;
    int QUESTION_RAW_SCORE_ROW_INDEX;
    int QUESTION_RAW_SCORE_START_COLUMN_INDEX = 1;
    int QUESTION_RAW_SCORE_END_COLUMN_INDEX;
    int NUMBER_OF_STUDENT;
    int DISABLE_NO_QUESTION = -1;
    int NUMBER_OF_ITEMS;

    public ReliabilityOfQuestion() {
        array = ArrayHelper.getGuttImprovised(Constants.NOT_FULLY_IMPROVISED_GUTT_TABLE);
        STUDENT_RAW_SCORE_COLUMN_INDEX = array[0].length - 1;
        STUDENT_RAW_SCORE_END_ROW_INDEX = array.length - 2;
        QUESTION_RAW_SCORE_ROW_INDEX = array.length - 1;
        QUESTION_RAW_SCORE_END_COLUMN_INDEX = array[0].length - 2;
        QUESTION_COLUMN_END_INDEX = array[0].length - 2;
        NUMBER_OF_STUDENT = getNumberOfStudents();
        NUMBER_OF_ITEMS = getNumberOfQuestion();
        //NUMBER_OF_STUDENT = array.length - 2;
    }

    public Double getReliabilityScore() {
        return calculateReliability(DISABLE_NO_QUESTION);
    }

    public ArrayList<Double> getReliabilityScores() {
        ArrayList<Double> reliabilityScoreList = new ArrayList<>();

        for (int j = QUESTION_COLUMN_START_INDEX; j <= QUESTION_COLUMN_END_INDEX; j++) {
            double cal = calculateReliability(j);
            reliabilityScoreList.add(cal);
        }

        return reliabilityScoreList;
    }

    /*
     *
     * KR20 = (n / (n-1 )) * (1 - ( sumof(PiQi)/ variance^2))
     *
     * n - total items
     * Pi = NumberOfStudentAnsweredCorrectly / NumberOfStudent
     * Qi = NumberOfStudentAnsweredIncorrectly / NumberOfStudent
     *       ->For each question
     * Variance = standard Deviation
     *
     * When question Index is disable consider respective column is not in system
     * modify system according to changes
     * Change in student raw score
     * */

    private Double calculateReliability(int questionIndexDisable) {

        double sumPQ = getSumPQ(questionIndexDisable);
        double variance = getVariance(questionIndexDisable);
        double calculate;

        if (questionIndexDisable == -1) {
            calculate = (double) NUMBER_OF_ITEMS / (NUMBER_OF_ITEMS - 1.0) * (1.0 - (sumPQ / (variance)));
        } else {
            // Decrease number of items by 1 because question is disable
            int newNumberOfItems = NUMBER_OF_ITEMS - 1;
            calculate = (double) newNumberOfItems / (newNumberOfItems - 1.0) * (1.0 - (sumPQ / variance));
        }

        calculate = Math.round(calculate * 1000.0) / 1000.0;
        System.out.print(questionIndexDisable + ". ");
        System.out.print("sumPQ: " + sumPQ);
        System.out.print(" variance: " + (variance));
        System.out.println(" result: " + calculate);
        return calculate;

    }

    private Double getSumPQ(int questionIndexDisable) {

        double sum = 0.0;
        for (int j = QUESTION_RAW_SCORE_START_COLUMN_INDEX; j <= QUESTION_RAW_SCORE_END_COLUMN_INDEX; j++) {
            //Taking care to disable question column
            if (j == questionIndexDisable)
                continue;
            double p = (double) array[QUESTION_RAW_SCORE_ROW_INDEX][j] / NUMBER_OF_STUDENT;
            int difference = NUMBER_OF_STUDENT - array[QUESTION_RAW_SCORE_ROW_INDEX][j];
            double q = (double) difference / NUMBER_OF_STUDENT;
            sum += (p * q);
        }

        return sum;

    }


    /*
     * Steps
     * 1. Find mean of student raw score take care of Question Disable Index
     * 2. Calculate Mean Difference (Student score - Mean)
     *       ->sum mean difference
     * */
    private Double getVariance(int questionIndexDisable) {

        ArrayList<Integer> studentModifiedRawScore = new ArrayList<>();
        //Calculating mean
        int totalStudentRawScore = 0;
        for (int i = STUDENT_RAW_SCORE_START_ROW_INDEX; i <= STUDENT_RAW_SCORE_END_ROW_INDEX; i++) {
            int studentScoreInDisableIndex = 0;
            if (questionIndexDisable > 0) {
                studentScoreInDisableIndex = array[i][questionIndexDisable];
            }
            int studentRawScore = array[i][STUDENT_RAW_SCORE_COLUMN_INDEX] - studentScoreInDisableIndex;
            studentModifiedRawScore.add(studentRawScore);
            totalStudentRawScore += studentRawScore;

        }

        double mean = (double) totalStudentRawScore / NUMBER_OF_STUDENT;

        System.out.println("Total Raw Score : " + totalStudentRawScore);
        System.out.println("Mean : " + mean);
        // Calculate Mean difference sum
        double meanDifferenceSum = 0.0;
        for (int i = 0; i < studentModifiedRawScore.size(); i++) {
            double difference = (double) studentModifiedRawScore.get(i) - mean;
            double power = Math.pow(difference, 2.0);
            meanDifferenceSum += power;
        }

        double variance = meanDifferenceSum / NUMBER_OF_STUDENT;

        //standardDeviation = Math.sqrt(standardDeviation);

        return variance;


    }


}
