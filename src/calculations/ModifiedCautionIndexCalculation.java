package calculations;

import HelperClasses.ArrayHelper;

import java.util.ArrayList;

public class ModifiedCautionIndexCalculation {

    /*
     * Student curve is made by the raw score of the student
     * */

    static int[][] guttArray;
    static int itemSumRowIndex;
    static int itemSumColumnIndex;
    static int rawScoreRowIndex;
    static int rawScoreColumnIndex;
    final static int STUDENT_ROW_INDEX = 1;
    static int STUDENT_EVALUATION_SIZE;


    public static ArrayList<Double> getQuestionMCI(int[][] array) {

        int newArray[][] = ArrayHelper.transposeMatrix(array);
        ArrayList<Double> questionMCIList = getMCI(newArray);

        return questionMCIList;

    }

    public static ArrayList<Double> getMCI(int[][] array) {
        guttArray = array;
        //Setting up necessary variables
        itemSumColumnIndex = 1;
        itemSumRowIndex = guttArray.length - 1;
        rawScoreColumnIndex = guttArray[0].length - 1;
        rawScoreRowIndex = 1;
        STUDENT_EVALUATION_SIZE = array.length - 2;

        ArrayList<Double> mciList = new ArrayList<>();

        for (int i = STUDENT_ROW_INDEX; i < STUDENT_EVALUATION_SIZE; i++) {
            int w = getWVariable(i);
            int y = getYVariable(i);
            int x = getXVariable(i);
            int z = getZVariable(i);
            int diff1 = w - x;

            double cal;

            if (diff1 == 0) cal = 0.0;
            else cal = (double) diff1 / (y - z);

            mciList.add(cal);
        }

        return mciList;
    }

    /*
     * Calculate W
     * 1. Scan left side of student curve
     * 2. if question is answered incorrectly then add number of student answered it correctly
     * */

    private static int getWVariable(int rowToScanIndex) {
        int totalScore = guttArray[rowToScanIndex][rawScoreColumnIndex];
        //System.out.println(totalScore);
        int sum = 0;
        for (int i = 1; i <= totalScore; i++) {

            if (guttArray[rowToScanIndex][i] == 0) {
                sum += guttArray[itemSumRowIndex][i];
            }
            //System.out.println("i="+i);
        }
        return sum;
    }


    /*
     * Calculate y variable
     * 1. Add up item score left of student curve*/

    private static int getYVariable(int rowToScanIndex) {
        int totalScore = guttArray[rowToScanIndex][rawScoreColumnIndex];
        int sum = 0;
        for (int i = 1; i <= totalScore; i++) {
            sum += guttArray[itemSumRowIndex][i];
        }
        return sum;

    }

    /*
     * Calculate X variable
     * 1. scan right of student curve
     * 2. if student answered item correctly then add item score to sum
     * 3. return sum*/
    private static int getXVariable(int rowToScanIndex) {
        int totalScore = guttArray[rowToScanIndex][rawScoreColumnIndex] + 1;
        int sum = 0;
        for (int i = totalScore; i < rawScoreColumnIndex; i++) {
            if (guttArray[rowToScanIndex][i] == 1) {
                sum += guttArray[itemSumRowIndex][i];
            }
        }
        return sum;
    }

    /*
     * Calculate Variable Z
     * 1. scan right of student curve
     * 2. Add item score from mth location
     *
     * mth location calculation
     * m = TotalNumber of item in test + 1 - StudentRawScore[rowToScanIndex]
     * */

    private static int getZVariable(int rowToScanIndex) {
        int totalScore = guttArray[rowToScanIndex][rawScoreColumnIndex] + 1;
        int sum = 0;
        int numOfItem = guttArray[0].length - 2;
        int m = numOfItem + 1 - guttArray[rowToScanIndex][rawScoreColumnIndex];
        //to ignore roll number column
        // m = m + 1;
        for (int i = m; i < rawScoreColumnIndex; i++) {
            sum += guttArray[itemSumRowIndex][i];
        }
        return sum;
    }

}