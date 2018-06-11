package sample.Controllers.ReportControllers;

import Analysis.Distractors;
import HelperClasses.*;
import calculations.CalculationBase;
import calculations.MathCutOffScore;
import calculations.NedelskyScore;
import calculations.ReliabilityOfQuestion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DataClasses.Bus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

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

    @FXML
    private AnchorPane anchorPaneReliability;

    @FXML
    Tab studentScoreTab;

    public void initialize() {

        int maxScore = CalculationBase.getNumberOfQuestion();
        /*Getting cut of score*/
        int cutOffScore = MathCutOffScore.getCutOffScore();
        System.out.println("MathCutOffScore : " + cutOffScore);
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

        relativePos = getLineYCoordinates(axisLowerUpperUnitsQuestion, totalMarksChartHeight, maxQuesScore);
        //Adjusting y coordinate when greater then ADJUST_LINE_Y_COORDINATE


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


        // Setting up positions
        difficultyLevelLabel.setLayoutX(X_POS);
        difficultyLevelLabel.setLayoutY(Y_POS);
        difficultyLevelBarChart.setLayoutX(X_POS);
        difficultyLevelBarChart.setLayoutY(TOP_GAP);
        //vbox.getChildren().addAll(difficultyLevelLabel, difficultyLevelBarChart);
        anchorPaneDifficultyLevel.getChildren().addAll(difficultyLevelLabel, difficultyLevelBarChart);


        /*











                RELIABILITY TAB
         *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          *
          * */


        ReliabilityOfQuestion reliabilityOfQuestion = new ReliabilityOfQuestion();

        /*Get total reliability in below variable */
        Double totalReliabilityTest = reliabilityOfQuestion.getReliabilityScore();


        /*Get Reliability list in below varible*/
        ArrayList<Double> reliabilityList = reliabilityOfQuestion.getReliabilityScores();
        Double maxReliabilityScore = Collections.max(reliabilityList);
        Double minReliabilityScore = Collections.min(reliabilityList);
        Double upperBound = 0.0, lowerBound = 1.0, unitTick = 0.1;

        /*
         * Getting upper and lower bounds for chart
         * 1. Upper bound >(greater then) totalReliabilityTest and max(reliabilityList)
         * 2. Lower bound <(less then) min(reliabilityList)
         * */

        //Calculating upper bound

        upperBound = maxReliabilityScore + 0.002;
        lowerBound = minReliabilityScore;
        unitTick = upperBound - lowerBound;

//        //Calculating lower bound
//        while(lowerBound > minReliabilityScore){
//            lowerBound -=unitTick;
//        }

        ArrayList<Double> arrayListLowerUpperUnitReliability = new ArrayList<>();

        //Lower unit
        arrayListLowerUpperUnitReliability.add(lowerBound);
        //Upper unit of chart
        arrayListLowerUpperUnitReliability.add(upperBound);
        //Unit tick
        arrayListLowerUpperUnitReliability.add(unitTick);
        Label reliabilityLabel = getLabel("Reliability");

        //Creating dummy bar chart
        DummyBarChart dummyBarChart = new DummyBarChart();
        ArrayList<String> x = new ArrayList<>();
        x.add("Q1");
        ArrayList<Double> y = new ArrayList<>();
        y.add(totalReliabilityTest);
        BarChart<String, Number> barChart = dummyBarChart.getDoubleDummyBarChart(x, y, "Question", "ReliabilityScore", "Reliability", arrayListLowerUpperUnitReliability.get(LOWER_BOUND_INDEX), arrayListLowerUpperUnitReliability.get(UPPER_BOUND_INDEX), arrayListLowerUpperUnitReliability.get(UNIT_TICK_INDEX));
        barChart.setPrefHeight(500);
        barChart.setLayoutX(X_POS);
        barChart.setLayoutY(TOP_GAP);

        anchorPaneReliability.getChildren().add(barChart);

        /*Removing dummy chart when student score tab changed*/
        studentScoreTab.setOnSelectionChanged((event -> {
            //Removing all nodes
            for (int i = 0; i < anchorPaneReliability.getChildren().size(); i++) {
                anchorPaneReliability.getChildren().remove(i);
            }

            //Creating required bar chart
            BarChart<String, Number> reliabilityBarChart = ChartHelper.getDoubleBarChart(ListGenerationHelper.questionList(), reliabilityList, "Question", "ReliabilityScore", "Reliability", arrayListLowerUpperUnitReliability.get(LOWER_BOUND_INDEX), arrayListLowerUpperUnitReliability.get(UPPER_BOUND_INDEX), arrayListLowerUpperUnitReliability.get(UNIT_TICK_INDEX));
            //increasing bar chart height
            reliabilityBarChart.setPrefHeight(500);
            reliabilityLabel.setLayoutX(X_POS);
            reliabilityLabel.setLayoutY(Y_POS);

            reliabilityBarChart.setLayoutX(X_POS);
            reliabilityBarChart.setLayoutY(TOP_GAP);

            double lineY = dummyBarChart.lineY + TOP_GAP + 14;
            double lineX = xmin;
            javafx.scene.shape.Line line = new javafx.scene.shape.Line(lineX, lineY, reliabilityBarChart.getPrefWidth(), lineY);
            line.setStroke(Color.RED);
            line.setStrokeWidth(Constants.STROKE_WIDTH);

            Text text = new Text(String.valueOf(totalReliabilityTest));
            text.setLayoutX(xmin - 15.0);
            text.setLayoutY(lineY);

            anchorPaneReliability.getChildren().addAll(reliabilityLabel, reliabilityBarChart, line, text);


        }));




        /* END RELIABILITY TEST*/


        /*
         * Setting anchor pane height to maximum
         * */

        anchorPane.setPrefHeight(screenMaxHeight);
        anchorPaneQuestionScore.setPrefHeight(screenMaxHeight);
        anchorPaneDifficultyLevel.setPrefHeight(screenMaxHeight);
        anchorPaneReliability.setPrefHeight(screenMaxHeight);


    }

    private double getReliabilityLineYCoordinate(ArrayList<Double> arrayListLowerUpperUnitReliability, double reliabilityChartHeight, Double reliabilityScore) {

        return 100.0;
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
        Label meanScoreValue = new Label("  " + String.valueOf(Math.round(CalculationHelper.getMeanScore() * 100.0) / 100.0) + "  ");
        meanScoreValue.setFont(getSegoiFontSyle(20));

        NedelskyScore nedelskyScore = new NedelskyScore();
        Label nedelSkyScoreLabel = new Label("  NedelSkyScore  ");
        nedelSkyScoreLabel.setFont(getSegoiFontSyle(20));
        Label nedelSkyScoreValue = new Label("  " + nedelskyScore.getNedelskyScore() + "  ");
        nedelSkyScoreValue.setFont(getSegoiFontSyle(20));

        gridPane.add(standardDeviationLabel, 0, 0);
        gridPane.add(standardDeviationValue, 1, 0);
        gridPane.add(meanScoreLabel, 0, 1);
        gridPane.add(meanScoreValue, 1, 1);
        gridPane.add(nedelSkyScoreLabel, 0, 2);
        gridPane.add(nedelSkyScoreValue, 1, 2);
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
