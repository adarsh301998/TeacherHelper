package sample.BaseClasses;

import com.jfoenix.controls.JFXToggleButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class TableBase extends MainBaseController {

    @FXML
    public JFXToggleButton contractTableToggle;

    @FXML
    public TableView<ObservableList<String>> tableView;

    @FXML
    public void contractTableEvent(ActionEvent event) {

        if (contractTableToggle.isSelected()) {
            //ArrayList<String> arrayList = ListGenerationHelper.smallSizeQuestionList();
            //arrayList.add(0, "RollNo.");

            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            //TableViewHelper.insertDataInColumn(tableView, arrayList);
        } else {
            //ArrayList<String> arrayList = ListGenerationHelper.questionList();
            //arrayList.add(0, "RollNo.");

            tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
            //TableViewHelper.insertDataInColumn(tableView, arrayList);
        }


    }


}
