package sample.Controllers.ReportControllers;

import HelperClasses.ListGenerationHelper;
import HelperClasses.TableViewHelper;
import calculations.QuestionCorrelation;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class QuestionCorrelationController extends BaseController {


    public void initialize() {
        QuestionCorrelation questionCorrelation = new QuestionCorrelation();
        ArrayList<ArrayList<Double>> correlation = questionCorrelation.getQuestionCorrelation();
        List<String> questionNumberList = ListGenerationHelper.getQuestionNumberListWithOutSubQuestion();

        /*Combining questionList and correlation to form row for table*/
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < questionNumberList.size(); i++) {
            List<String> subRow = new ArrayList<>();
            subRow.add(questionNumberList.get(i));
            for (Double d : correlation.get(i)) {
                subRow.add(String.valueOf(d));
            }
            rows.add(subRow);
        }

        /*Adding Extra space in question list to form column heading*/
        questionNumberList.add(0, " ");


        /*Inserting data in table*/
        TableViewHelper.insertDataInColumn(tableView, questionNumberList);
        TableViewHelper.insertDataInRow(tableView, rows);

        /*Expansion policy*/
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

    }


}
