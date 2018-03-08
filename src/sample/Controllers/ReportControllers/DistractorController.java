package sample.Controllers.ReportControllers;

import HelperClasses.ChartHelper;
import HelperClasses.DialogPopUp;
import HelperClasses.ListGenerationHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

import java.io.IOException;
import java.util.ArrayList;

public class DistractorController {
    @FXML
    private FontAwesomeIcon close_btn;

    @FXML
    private JFXButton back_btn;

    @FXML
    private Label titlepane_Label;

    @FXML
    private JFXListView<HBox> listView;

    @FXML
    private StackPane stackPane;

    TestDetails testDetails;

    public void initialize() {
        testDetails = Bus.getInstance();
        //Create tables Here
        listView.setExpanded(true);
        listView.setDepth(3);
        insertDataInListView();
    }

    private void insertDataInListView() {

        ArrayList<String> studentRollNumbers = ListGenerationHelper.studentRollNumberList();
        ArrayList<ArrayList<Integer>> studentResponseEachQues = ListGenerationHelper.allStudentResponseForEachOptionList();
        ArrayList<ArrayList<Integer>> distractor = getDistractorsList(studentResponseEachQues);

        for (int i = 0; i < studentRollNumbers.size(); i++) {

            Label rollNumber = new Label(studentRollNumbers.get(i));
            rollNumber.setFont(new Font("Segoi UI", 30));

            GridPane gridLayout = getGridLayout(studentResponseEachQues.get(i), distractor.get(i));

            //GridPane gridLayout = getGridLayout(studentResponseEachQues);

            BarChart barChart = ChartHelper.distrcatorChart(studentResponseEachQues.get(i), distractor.get(i));

            HBox hBox = new HBox(rollNumber, (Node) gridLayout, barChart);
            hBox.setSpacing(100);

            listView.getItems().add(hBox);

        }


    }

    private GridPane getGridLayout(ArrayList<Integer> studentResponseEachQues, ArrayList<Integer> distractor) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(" Options ");
        arrayList.add(" A ");
        arrayList.add(" B ");
        arrayList.add(" C ");
        arrayList.add(" D ");
        gridPane.setGridLinesVisible(true);
        for (int i = 0; i < arrayList.size(); i++) {
            Label label = new Label(arrayList.get(i));
            label.setFont(new Font("Segoi UI", 30));
            gridPane.add(label, i, 0);
        }
        Label label = new Label(" Response ");
        label.setFont(new Font("Segoi UI", 30));
        gridPane.add(label, 0, 1);

        Label label2 = new Label(" Distractor ");
        label2.setFont(new Font("Segoi UI", 30));
        gridPane.add(label2, 0, 2);

        for (int i = 0; i < studentResponseEachQues.size(); i++) {
            // adding student response
            Label label1 = new Label(" " + studentResponseEachQues.get(i) + " ");
            label1.setFont(new Font("Segoi UI", 30));
            gridPane.add(label1, i + 1, 1);

            //adding distractors
            Label label3 = new Label(" " + distractor.get(i) + " ");
            label3.setFont(new Font("Segoi UI", 30));
            gridPane.add(label3, i + 1, 2);
        }


        return gridPane;
    }

    private ArrayList<ArrayList<Integer>> getDistractorsList(ArrayList<ArrayList<Integer>> studentResponseEachQues) {


        return studentResponseEachQues;
    }

    @FXML
    void backEvent(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../scenes/reportOptions.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void closeEvent(MouseEvent event) {
        DialogPopUp.closeAlert(stackPane);
    }

    @FXML
    void showBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, testDetails);
    }
}
