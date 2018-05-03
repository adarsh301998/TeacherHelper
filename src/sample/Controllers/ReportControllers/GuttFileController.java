package sample.Controllers.ReportControllers;

import HelperClasses.ArrayHelper;
import HelperClasses.ListGenerationHelper;
import HelperClasses.TableViewHelper;

import java.util.List;

public class GuttFileController extends BaseController {

    public void initialize() {


        /*int[][] guttArrayTable = GuttSortingHelper.getSortedArray(ArrayHelper.guttTableArray());
        int questionSortedIndex[] = GuttSortingHelper.getQuesSortedIndex();
        int questionSum[] = GuttSortingHelper.getQuesSum();
*/
        int guttArrayTable[][] = ArrayHelper.getGuttImprovised();


        List<List<String>> rowData = ListGenerationHelper.guttArrayToList(guttArrayTable);
        List<String> columnData = ListGenerationHelper.getCorrospondingQuestionNumber(guttArrayTable[0]);
        columnData.add(0, "RollNo");
        columnData.add("Total");

        TableViewHelper.insertDataInColumn(tableView, columnData);
        TableViewHelper.insertDataInRow(tableView, rowData);


    }


}
