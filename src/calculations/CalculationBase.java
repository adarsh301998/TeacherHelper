package calculations;

import HelperClasses.ListGenerationHelper;
import sample.DataClasses.Bus;

import java.util.List;

public class CalculationBase {

    /*
     * Return Number of question
     * */

    public static int getNumberOfQuestion() {
        List<List<Integer>> arrayLists = ListGenerationHelper.studentEvaluationInList();

        return arrayLists.get(0).size();
    }

    /*
     * Return number of students
     * */

    public static int getNumberOfStudents() {
        return Bus.getInstance().getNumberOfStudent();
    }

}
