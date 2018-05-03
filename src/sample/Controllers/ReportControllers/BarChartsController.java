package sample.Controllers.ReportControllers;

import Analysis.Distractors;
import HelperClasses.*;
import calculations.CalculationBase;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DataClasses.Bus;

import java.io.IOException;
import java.util.ArrayList;

public class BarChartsController extends BaseController {


    private static final int LOWER_BOUND_INDEX = 0;
    private static final int UPPER_BOUND_INDEX = 1;
    private static final int UNIT_TICK_INDEX = 2;

    private int TOP_GAP = 80;
    private int X_POS = 10;
    private int Y_POS = 10;
    private int LEFT_GAP = 80;
    private int ADJUST_LINE_Y_COORDINATE = 40;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private AnchorPane anchorPaneQuestionScore;

    @FXML
    private AnchorPane anchorPaneDifficultyLevel;

    public void initialize() {

        int maxScore = CalculationBase.getNumberOfQuestion();
        int cutOffScore = (int) (maxScore * 0.50);
        int maxQuesScore = CalculationBase.getNumberOfStudents();
        System.out.println("Max Ques Score : " + maxQuesScore);

        //Adding total marks bar chart
        Label totalMarksLabel = getLabel("Total Marks Bar Chart");
        totalMarksLabel.setLayoutX(X_POS);
        totalMarksLabel.setLayoutY(Y_POS);



        /*  STUDENT SCORE TAB   */

        //getting bounds for YAxis
        ArrayList<Double> axisLowerUpperUnits = calBoundsforBarChart(maxScore);

        BarChart<String, Number> totalMarksBarChart = ChartHelper.getIntegerBarChart(
                ListGenerationHelper.studentRollNumberList(),
                ListGenerationHelper.integerListToArrayList(ListGenerationHelper.studentsTotalMarks()),
                "Roll Numbers", "Marks", "Total Marks",
                axisLowerUpperUnits.get(LOWER_BOUND_INDEX),
                axisLowerUpperUnits.get(UPPER_BOUND_INDEX),
                axisLowerUpperUnits.get(UNIT_TICK_INDEX));
        totalMarksBarChart.setLayoutX(X_POS);
        TOP_GAP += (int) totalMarksLabel.getPrefHeight();
        totalMarksBarChart.setLayoutY(TOP_GAP);


        double totalMarksChartHeight = totalMarksBarChart.getPrefHeight();

        double relativePos = getLineYCoordinates(axisLowerUpperUnits, totalMarksChartHeight, maxScore);
        int xmin = X_POS + LEFT_GAP;


        //Adjusting y coordinate when greater then ADJUST_LINE_Y_COORDINATE
        if (maxScore > ADJUST_LINE_Y_COORDINATE) {
            relativePos += Constants.STROKE_WIDTH;
        }

        javafx.scene.shape.Line maxScoreLine = new javafx.scene.shape.Line(xmin, relativePos, totalMarksBarChart.getPrefWidth(), relativePos);
        maxScoreLine.setStroke(Color.GREEN);
        maxScoreLine.setStrokeWidth(Constants.STROKE_WIDTH);


        /*Adding cut off score*/

        double relativePosForCutOff = getLineYCoordinates(axisLowerUpperUnits, totalMarksChartHeight, cutOffScore);
        //Adjusting line to move up
        relativePosForCutOff -= 2 * Constants.STROKE_WIDTH;
        javafx.scene.shape.Line cutOffScoreLine = new javafx.scene.shape.Line(xmin, relativePosForCutOff, totalMarksBarChart.getPrefWidth(), relativePosForCutOff);
        cutOffScoreLine.setStroke(Color.RED);
        cutOffScoreLine.setStrokeWidth(Constants.STROKE_WIDTH);

        GridPane gridPane = getGridPane();
        gridPane.setLayoutX(X_POS);
        gridPane.setLayoutY(totalMarksBarChart.getPrefHeight() + TOP_GAP);

        anchorPane.getChildren().addAll(totalMarksLabel, totalMarksBarChart, maxScoreLine, cutOffScoreLine, gridPane);

        /*  STUDENT SCORE TAB END   */


        /*  QUESTION SCORE TAB  */


        //Adding question score
        ArrayList<Double> axisLowerUpperUnitsQuestion = calBoundsforBarChart(maxQuesScore);
        Label questionScoreLabel = getLabel("Question Score");

        BarChart<String, Number> questionScoreBarChart = ChartHelper.getIntegerBarChart(ListGenerationHelper.questionList(), ListGenerationHelper.integerListToArrayList(ListGenerationHelper.getQuestionScore()), "Question Label", "Score", "Question Score", axisLowerUpperUnitsQuestion.get(LOWER_BOUND_INDEX), axisLowerUpperUnitsQuestion.get(UPPER_BOUND_INDEX), axisLowerUpperUnitsQuestion.get(UNIT_TICK_INDEX));
        //BarChart<String, Number> questionScoreBarChart = ChartHelper.getIntegerBarChart(studentRollNumberList,totalMarks,"Question Label","Score", "Question Score",axisLowerUpperUnitsQuestion.get(LOWER_BOUND_INDEX), axisLowerUpperUnitsQuestion.get(UPPER_BOUND_INDEX), axisLowerUpperUnitsQuestion.get(UNIT_TICK_INDEX));

        questionScoreLabel.setLayoutX(X_POS);
        questionScoreLabel.setLayoutY(Y_POS);
        questionScoreBarChart.setLayoutX(X_POS);
        questionScoreBarChart.setLayoutY(TOP_GAP);

        relativePos = getLineYCoordinates(axisLowerUpperUnits, totalMarksChartHeight, maxQuesScore);
        //Adjusting y coordinate when greater then ADJUST_LINE_Y_COORDINATE
        if (maxScore > ADJUST_LINE_Y_COORDINATE) {
            relativePos += Constants.STROKE_WIDTH;
        }

        /*
         *
         *
         * Fix issue of question line addjustment number
         *
         * */

        javafx.scene.shape.Line maxQuesScoreLine = new javafx.scene.shape.Line(xmin, relativePos, questionScoreBarChart.getPrefWidth(), relativePos);
        maxQuesScoreLine.setStroke(Color.GREEN);
        maxQuesScoreLine.setStrokeWidth(Constants.STROKE_WIDTH);

        //vbox.getChildren().addAll(questionScoreLabel, questionScoreBarChart);
        anchorPaneQuestionScore.getChildren().addAll(questionScoreLabel, questionScoreBarChart, maxQuesScoreLine);


        /* QUESTION SCORE TAB END */


        /*  Difficulty level tab    */

        Label difficultyLevelLabel = getLabel("Difficulty Level");
        BarChart<String, Number> difficultyLevelBarChart = ChartHelper.getDoubleBarChart(ListGenerationHelper.questionList(), ListGenerationHelper.doubleListToArrayList(ListGenerationHelper.getQuestionDifficultyLevel()), "Questions", "", "Difficulty Level", 0.0, 1.0, 0.1);

        //BarChart<String, Number> difficultyLevelBarChart = ChartHelper.getDoubleBarChart(studentRollNumberList, difficultyLevel, "Questions", "","Difficulty Level",0.0,1.1,0.1);

        difficultyLevelLabel.setLayoutX(X_POS);
        difficultyLevelLabel.setLayoutY(Y_POS);

        difficultyLevelBarChart.setLayoutX(X_POS);
        difficultyLevelBarChart.setLayoutY(TOP_GAP);
        //vbox.getChildren().addAll(difficultyLevelLabel, difficultyLevelBarChart);
        anchorPaneDifficultyLevel.getChildren().addAll(difficultyLevelLabel, difficultyLevelBarChart);

        /*
         * Setting anchor pane height to maximum
         * */

        anchorPane.setPrefHeight(screenMaxHeight);
        anchorPaneQuestionScore.setPrefHeight(screenMaxHeight);
        anchorPaneDifficultyLevel.setPrefHeight(screenMaxHeight);


    }

    private GridPane getGridPane() {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setGridLinesVisible(true);

        Label standardDeviationLabel = new Label("  Standard Deviation  ");
        standardDeviationLabel.setFont(getSegoiFontSyle(20));
        Label standardDeviationValue = new Label("  " + String.valueOf((Math.round(Distractors.standardDeviation() * 100)) / 100.0) + " ");
        standardDeviationValue.setFont(getSegoiFontSyle(20));
        Label meanScoreLabel = new Label("  Mean Score  ");
        meanScoreLabel.setFont(getSegoiFontSyle(20));
        Label meanScoreValue = new Label("  " + String.valueOf(CalculationHelper.getMeanScore() + "  "));
        meanScoreValue.setFont(getSegoiFontSyle(20));

        gridPane.add(standardDeviationLabel, 0, 0);

        gridPane.add(standardDeviationValue, 1, 0);


        gridPane.add(meanScoreLabel, 0, 1);


        gridPane.add(meanScoreValue, 1, 1);
        return gridPane;

    }

    private Label getLabel(String s) {
        Label label = new Label(s);
        label.setFont(getSegoiFontSyle(Constants.STANDARD_FONT_SIZE));
        label.setStyle(Constants.BROWN_TEXT_FILL);
        return label;
    }

    @FXML
    void backEvent(ActionEvent event) {
        System.out.println("back Event occured");
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
    void displayBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, Bus.getInstance());

    }

    private ArrayList<Double> calBoundsforBarChart(int maxScore) {

        double lowerBound = 0;
        double upperBound = maxScore + 5;
        double unitTick = maxScore / 2.0;

        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(lowerBound);
        arrayList.add(upperBound);
        arrayList.add(unitTick);
        return arrayList;
    }


    private double getLineYCoordinates(ArrayList<Double> axisLowerUpperUnits, double chartHeight, double value) {

        double relativePos = ChartHelper.calculateRelativePositionInChart(axisLowerUpperUnits.get(UPPER_BOUND_INDEX), chartHeight, value);


        chartHeight -= Constants.LABEL_SPACE_IN_AXIS - Constants.CHART_TOP_GAP;

        relativePos -= chartHeight - Constants.STROKE_WIDTH;

        //relativePos -=(totalMarksChartHeight-Constants.LABEL_SPACE_IN_AXIS);
        if (relativePos < 0) {
            //relativePos =2 * relativePos;
            relativePos = Math.abs(relativePos);
        }
        //relativePos +=15;
        relativePos += TOP_GAP;


        return relativePos;
    }


}
