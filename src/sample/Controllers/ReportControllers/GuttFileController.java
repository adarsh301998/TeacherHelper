package sample.Controllers.ReportControllers;

import HelperClasses.ArrayHelper;
import HelperClasses.GuttSortingHelper;
import HelperClasses.ListGenerationHelper;
import HelperClasses.TableViewHelper;

import java.util.List;

public class GuttFileController extends BaseController {

    public void initialize() {


        int[][] guttArrayTable = GuttSortingHelper.getSortedArray(ArrayHelper.guttTableArray());
        int questionSortedIndex[] = GuttSortingHelper.getQuesSortedIndex();
        int questionSum[] = GuttSortingHelper.getQuesSum();

        for (int i = 0; i < guttArrayTable.length; i++) {

        }

        List<List<String>> rowData = ListGenerationHelper.guttArrayToList(guttArrayTable, questionSum);
        List<String> columnData = ListGenerationHelper.sortedQuestion(questionSortedIndex);
        columnData.add(0, "RollNo");
        columnData.add("Total");

        TableViewHelper.insertDataInColumn(tableView, columnData);
        TableViewHelper.insertDataInRow(tableView, rowData);


    }


}
