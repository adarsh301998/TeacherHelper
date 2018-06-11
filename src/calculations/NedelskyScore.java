package calculations;

import HelperClasses.ListGenerationHelper;

import java.util.ArrayList;
import java.util.List;

public class NedelskyScore extends CalculationBase {

    int TOLERANCE_PERCENT, NUMBER_OF_ITEMS, NUMBER_OF_STUDENTS;
    char options[] = {'A', 'B', 'C', 'D'};
    char NO_RESPONSE_CHARACTER = ' ';
    List<List<Character>> studentResponse;

    int QUESTION_START_COLUMN_INDEX, QUESTION_END_COLUMN_INDEX;

    int STUDENT_START_ROW_INDEX, STUDENT_END_ROW_INDEX;

    public NedelskyScore() {
        this(5);
    }

    public NedelskyScore(int tolerance_percent) {
        TOLERANCE_PERCENT = tolerance_percent;
        NUMBER_OF_ITEMS = getNumberOfQuestion();
        NUMBER_OF_STUDENTS = getNumberOfStudents();
        studentResponse = ListGenerationHelper.studentResponseInList();

        //Question Column index
        QUESTION_START_COLUMN_INDEX = 0;
        QUESTION_END_COLUMN_INDEX = studentResponse.get(0).size() - 1;

        //Calculating index for students(row wise)
        STUDENT_START_ROW_INDEX = 0;
        STUDENT_END_ROW_INDEX = studentResponse.size() - 1;
    }

    /*
     * 1. Counting response in each question
     * 2. Obtaining corrosponding percent
     * 3. Counting percent greater the tolerance percent
     * 4. Adding inverse of count to sum
     * 5. Repeat step 1 to 4 for each question
     * 6. Obtain score by SUM * NUMBER_OF_ITEMS
     * */
    public int getNedelskyScore() {

        double sum = 0.0;
        for (int j = QUESTION_START_COLUMN_INDEX; j <= QUESTION_END_COLUMN_INDEX; j++) {
            ArrayList<Integer> countArrayList = getCharacterCount(j);
            ArrayList<Double> characterCountPercent = getCharacterCountPercent(countArrayList);
            int count = getPercentCountGreaterThenTolerance(characterCountPercent);
            sum += (double) (1.0 / count);
            System.out.println(j + " " + countArrayList + " " + characterCountPercent + " COUNT : " + count + " SUM = " + sum);
        }

        int score = (int) sum;
        return score;
    }

    /*
     * Counting number of character appearance in Question
     * column index is equivalent to question index
     * */
    ArrayList<Integer> getCharacterCount(int columnIndex) {
        ArrayList<Integer> countArrayList = new ArrayList<>();

        int countA = 0, countB = 0, countC = 0, countD = 0;
        for (int i = STUDENT_START_ROW_INDEX; i <= STUDENT_END_ROW_INDEX; i++) {
            char response = studentResponse.get(i).get(columnIndex);
            if (response == options[0]) countA++;
            else if (response == options[1]) countB++;
            else if (response == options[2]) countC++;
            else if (response == options[3]) countD++;
        }

        countArrayList.add(countA);
        countArrayList.add(countB);
        countArrayList.add(countC);
        countArrayList.add(countD);

        return countArrayList;

    }

    /*
     * Calculating percent for each option
     * percent = count / Number of student * 100
     * */

    ArrayList<Double> getCharacterCountPercent(ArrayList<Integer> characterCount) {

        ArrayList<Double> percent = new ArrayList<>();

        /*for (Integer i : characterCount){

            double calculate =(double) ((i / NUMBER_OF_STUDENTS) * 100.0);
            percent.add(calculate);
        }*/

        for (int i = 0; i < characterCount.size(); i++) {
            double count = characterCount.get(i);
            double calculate = count / NUMBER_OF_STUDENTS;
            calculate = calculate * 100.0;
            percent.add(calculate);
        }
        return percent;
    }


    /*
     * Count number of percent less then tolerance
     * */

    int getPercentCountGreaterThenTolerance(ArrayList<Double> percentList) {
        int count = 0;
        for (Double i : percentList) {
            if (i > TOLERANCE_PERCENT)
                count++;
        }
        return count;
    }

}
