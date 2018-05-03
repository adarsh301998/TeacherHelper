package calculations;

import HelperClasses.ListGenerationHelper;

import java.util.ArrayList;
import java.util.List;

public class BasicStudentReportCalculation extends CalculationBase {

    /*
     * 1. Percent calculation
     * */

    public static ArrayList<Double> getStudentPercent() {

        List<Integer> studentTotalMarks = ListGenerationHelper.studentsTotalMarks();
        int totalNumberOfQues = getNumberOfQuestion();

        ArrayList<Double> studentPercent = new ArrayList<>();

        for (int i = 0; i < studentTotalMarks.size(); i++) {
            double percent = (double) studentTotalMarks.get(i) / totalNumberOfQues;
            percent *= 100.0;
            studentPercent.add(percent);
        }
        return studentPercent;

    }

}
