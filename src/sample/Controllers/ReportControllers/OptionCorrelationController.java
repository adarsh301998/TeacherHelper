package sample.Controllers.ReportControllers;

import Analysis.Distractors;
import HelperClasses.ChartHelper;
import HelperClasses.Constants;
import HelperClasses.DialogPopUp;
import HelperClasses.ListGenerationHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

import java.io.IOException;
import java.util.ArrayList;

public class OptionCorrelationController {
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
        titlepane_Label.setText(Constants.CORRELATION);
        testDetails = Bus.getInstance();
        //Create tables Here
        listView.setExpanded(true);
        listView.setDepth(3);
        insertDataInListView();
    }

    private void insertDataInListView() {

        ArrayList<String> quesyionLabels = ListGenerationHelper.questionList();
        ArrayList<ArrayList<Integer>> studentResponseEachQues = ListGenerationHelper.allStudentResponseForEachOptionList();
        ArrayList<ArrayList<Double>> distractor = getDistractorsList(studentResponseEachQues);
        ArrayList<Character> keyList = ListGenerationHelper.getKeyList();


        for (int i = 0; i < quesyionLabels.size(); i++) {

            Label rollNumber = new Label(quesyionLabels.get(i));
            rollNumber.setFont(new Font("Segoi UI", 20));

            GridPane gridLayout = getGridLayout(studentResponseEachQues.get(i), distractor.get(i));

            //GridPane gridLayout = getGridLayout(studentResponseEachQues);

            gridLayout.setAlignment(Pos.CENTER);
            BarChart barChart = ChartHelper.distrcatorChart(studentResponseEachQues.get(i), distractor.get(i));
            Label keyLabel = new Label("Key : " + keyList.get(i));
            keyLabel.setFont(new Font("Segoi UI", 20));
            keyLabel.setStyle("-fx-text-fill: #27ae60");
            //keyLabel.setStyle("-fx-background-color: #FFFF");

            VBox chartAndKeyBox = new VBox(keyLabel, barChart);
            chartAndKeyBox.setSpacing(10);
            chartAndKeyBox.setAlignment(Pos.CENTER);
            HBox hBox = new HBox(rollNumber, gridLayout, chartAndKeyBox);
            hBox.setSpacing(100);

            listView.getItems().add(hBox);

        }


    }

    private GridPane getGridLayout(ArrayList<Integer> studentResponseEachQues, ArrayList<Double> distractor) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(5, 5, 5, 5));
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(" Options ");
        arrayList.add(" A ");
        arrayList.add(" B ");
        arrayList.add(" C ");
        arrayList.add(" D ");
        gridPane.setGridLinesVisible(true);
        int labelFontSize = 20;
        // Creating row for options
        for (int i = 0; i < arrayList.size(); i++) {
            Label label = new Label(arrayList.get(i));
            label.setFont(new Font("Segoi UI", labelFontSize));
            gridPane.add(label, i, 0);
        }


        Label label = new Label(" Response Count ");
        label.setFont(new Font("Segoi UI", labelFontSize));
        label.setStyle("-fx-text-fill: #F1602C");

        gridPane.add(label, 0, 1);

        Label label2 = new Label(Constants.CORRELATION + " ");
        label2.setFont(new Font("Segoi UI", labelFontSize));
        label2.setStyle("-fx-text-fill: #E3AF5D");
        gridPane.add(label2, 0, 2);

        for (int i = 0; i < studentResponseEachQues.size(); i++) {
            // adding student response
            Label label1 = new Label(" " + studentResponseEachQues.get(i) + " ");
            label1.setFont(new Font("Segoi UI", labelFontSize));
            label1.setStyle("-fx-text-fill: #F1602C");
            gridPane.add(label1, i + 1, 1);

            //adding distractors
            Label label3 = new Label(" " + distractor.get(i) + " ");
            label3.setFont(new Font("Segoi UI", labelFontSize));
            label3.setStyle("-fx-text-fill: #E3AF5D");
            gridPane.add(label3, i + 1, 2);
        }



        return gridPane;
    }

    private ArrayList<ArrayList<Double>> getDistractorsList(ArrayList<ArrayList<Integer>> studentResponseEachQues) {


        ArrayList<ArrayList<Double>> distrcatorsList = Distractors.getDistactors();

        return distrcatorsList;
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
