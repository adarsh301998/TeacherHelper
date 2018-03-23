package sample.Controllers.ReportControllers;

import HelperClasses.DialogPopUp;
import HelperClasses.ListGenerationHelper;
import HelperClasses.TableViewHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

import java.io.IOException;
import java.util.List;

public class DisplayStudentResponseController {

    @FXML
    private JFXButton back_btn;

    @FXML
    private JFXToggleButton binaryToggle;

    @FXML
    private Label studentResponseLabel;

    @FXML
    private VBox tableVbox;

    @FXML
    private StackPane stack_pane;

    @FXML
    private FontAwesomeIcon close_btn;

    @FXML
    private TableView<ObservableList<String>> tableView;

    int count = 0;
    TestDetails testDetails;

    public void initialize() {
        testDetails = Bus.getInstance();

        //Create table
        /*ListGenerationHelper.questionList();
        ListGenerationHelper.studentResponseInList();
        ListGenerationHelper.studentRollNumberList();
        ListGenerationHelper.rollNumberAndResponseList();
        ListGenerationHelper.allStudentResponseForEachOptionList();
        ListGenerationHelper.studentEvaluationInList();*/


        //Adding Columns
        List<String> questionLabels = ListGenerationHelper.questionList();
        questionLabels.add(0, "RollNo.");
        TableViewHelper.insertDataInColumn(tableView, questionLabels);
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);

        //Adding rows
        insertRow(ListGenerationHelper.rollNumberAndResponseList());
    }

    private void insertRow(List<List<String>> displayList) {

        // Inserting rows
        tableView.getItems().clear();
        TableViewHelper.insertDataInRow(tableView, displayList);

    }


    private void openFrame(String frame, ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(frame));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void backFrameEvent(ActionEvent event) {
        openFrame("../scenes/reportOptions.fxml", event);
    }

    @FXML
    void displayBasicTestInfo(MouseEvent event) {

        DialogPopUp.basicInfoDialog(stack_pane, testDetails);
    }

    @FXML
    void closeEvent(MouseEvent event) {
        DialogPopUp.closeAlert(stack_pane);
    }

    @FXML
    void toggleActionEvent(ActionEvent event) {

        if (binaryToggle.isSelected()) {
            insertRow(ListGenerationHelper.rollNumberAndEvaluationList());
        } else {
            insertRow(ListGenerationHelper.rollNumberAndResponseList());
        }

    }
}
