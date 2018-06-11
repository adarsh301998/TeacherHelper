package calculations;

import HelperClasses.ArrayHelper;

import java.util.ArrayList;

public class QuestionCorrelation extends CalculationBase {

    // [StudentRollNumber, StudentResponse, Total]
    // Student Response according to serial wise question
    int[][] studentResponse;
    int NUMBER_OF_QUESTION;
    int QUESTION_START_COLUMN_INDEX;
    int QUESTION_END_COLUMN_INDEX;
    int QUESTION_START_ROW_INDEX;
    int QUESTION_END_ROW_INDEX;

    public QuestionCorrelation() {
        studentResponse = ArrayHelper.guttTableArray();
        NUMBER_OF_QUESTION = getNumberOfQuestion();
        QUESTION_START_COLUMN_INDEX = 1;
        QUESTION_END_COLUMN_INDEX = studentResponse[0].length - 2;
        QUESTION_END_ROW_INDEX = 0;
        QUESTION_END_ROW_INDEX = studentResponse.length - 1;
    }

    public ArrayList<ArrayList<Double>> getQuestionCorrelation() {
        /*for (int i=0;i<studentResponse.length;i++){
            for (int j=0;j<studentResponse[0].length;j++){
                System.out.print(studentResponse[i][j] + "\t");
            }
            System.out.println();
        }*/
        ArrayList<ArrayList<Double>> mainArrayList = new ArrayList<>();
        for (int j = QUESTION_START_COLUMN_INDEX; j <= QUESTION_END_COLUMN_INDEX; j++) {
            mainArrayList.add(getCorrelation(j));

        }

        return mainArrayList;

    }

    private ArrayList<Double> getCorrelation(int questionIndex) {

        ArrayList<Double> subArrayList = new ArrayList<>();

        for (int j = QUESTION_START_COLUMN_INDEX; j <= QUESTION_END_COLUMN_INDEX; j++) {

            /*Counting sequence to calculate correlation with Question
             * n11 -> studentResponse[i][questionIndex] = 1 and studentResponse[i][j] = 1 ( j = will loop through each and every question)
             * We count all possible 4 combination
             * F(12)[question 1 and 2] = N11N00 - N01N10/((N11+N10)(N01+N00)(N11+N01)(N10+N00))^1/2
             * We are calculating Correlation of questionIndex with every question
             * */
            int n11 = 0, n10 = 0, n01 = 0, n00 = 0, correct = 1, wrong = 0;
            for (int i = QUESTION_START_ROW_INDEX; i <= QUESTION_END_ROW_INDEX; i++) {
                if (studentResponse[i][questionIndex] == correct && studentResponse[i][j] == correct) n11++;
                else if (studentResponse[i][questionIndex] == correct && studentResponse[i][j] == wrong) n10++;
                else if (studentResponse[i][questionIndex] == wrong && studentResponse[i][j] == correct) n01++;
                else if (studentResponse[i][questionIndex] == wrong && studentResponse[i][j] == wrong) n00++;
            }
            double calculate = (double) ((n11 * n00 - n01 * n10) / Math.sqrt((n11 + n10) * (n01 + n00) * (n11 + n01) * (n10 + n00)));
            calculate = Math.round(calculate * 1000.0) / 1000.0;
            subArrayList.add(calculate);
        }

        return subArrayList;
    }
}
