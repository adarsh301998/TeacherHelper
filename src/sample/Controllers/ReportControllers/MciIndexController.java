package sample.Controllers.ReportControllers;

import HelperClasses.ArrayHelper;
import HelperClasses.ChartHelper;
import HelperClasses.Constants;
import HelperClasses.GuttDataHelper;
import calculations.ModifiedCautionIndexCalculation;
import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class MciIndexController extends BaseController {


    /*
        Gutt Table Structure
     * [StudentIndex, Evaluation, TotalMarks]
     *
     * */

    @FXML
    AnchorPane anchorPaneStudentMCI;

    @FXML
    AnchorPane anchorPaneQuestionMCI;


    public static final String TOP_RIGHT = "Top Right";
    public static final String TOP_LEFT = "Top Left";
    public static final String DOWN_RIGHT = "Down Right";
    public static final String DOWN_LEFT = "DOWN LEFT";
    public static final String STUDENT_MCI_HEADING = "STUDENT MCI";
    public static final String QUESTION_MCI_HEADING = "QUESTION MCI";

    public static final int HEADING_SIZE = 40;


    double defaultMciIndex = 0.3;
    double defaultPercent = 50.0;
    // difficulty level is taken in terns to percent
    double defaultDifficultyLevel = 50;

    JFXListView<VBox> jfxListView = new JFXListView<>();

    JFXListView<VBox> questionMCIListView = new JFXListView<>();


    public void initialize() {

        checkMaximize();


        int[][] gutArray = ArrayHelper.getGuttImprovised(Constants.FULLY_IMPROVISED_GUTT_TABLE);

        GuttDataHelper guttDataHelper = new GuttDataHelper(gutArray);


        double strokeWidth = Constants.STROKE_WIDTH;
        double left_gap = 65;



        /*Start Student MCI
         * 1. getting necessary list to plot point in scatter chart
         * */
        ArrayList<Double> mciArrayList = ModifiedCautionIndexCalculation.getMCI(gutArray);

        ArrayList<String> rollNumbers = guttDataHelper.getRollNumberFromGutt();

        ArrayList<String> studentNames = guttDataHelper.getStudentnameFromGutt();

        ArrayList<Double> studentPercentage = guttDataHelper.getStudentPercentFromGutt();


        Label studentMciIndexLabel = getSegoiLabel(STUDENT_MCI_HEADING, HEADING_SIZE);
        studentMciIndexLabel.setLayoutX(200);
        studentMciIndexLabel.setLayoutY(80);

        jfxListView.setExpanded(true);
        jfxListView.setDepth(Constants.LIST_DEPTH);
        jfxListView.setOrientation(Orientation.VERTICAL);
        jfxListView.setLayoutX(720);
        jfxListView.setLayoutY(150);

        /*
         * print list to check answers
         * */


        for (int i = 0; i < mciArrayList.size(); i++) {
            System.out.println(rollNumbers.get(i) + "\\t" + studentNames.get(i) + "\\t" + mciArrayList.get(i) + "\\t" + studentPercentage.get(i));
        }


        ScatterChart<Number, Number> scatterChart = ChartHelper.getScatterChart(mciArrayList, rollNumbers, studentPercentage, false);
        scatterChart.setPrefWidth(700);
        scatterChart.setPrefHeight(500);
        scatterChart.setStyle("-fx-background-color : #EEF1F6");
        scatterChart.setLayoutX(20);
        scatterChart.setLayoutY(140);

        int top_gap = (int) scatterChart.getLayoutY();
        int xMin = (int) scatterChart.getLayoutX() + 52;
        int xMax = (int) (scatterChart.getPrefWidth());
        int yMin = (int) scatterChart.getLayoutY() + 18;
        int yMax = (int) scatterChart.getPrefHeight() + top_gap - 100;


        // You may need add and subtract number to adjust line to correct line
        // Number is dependent on top gap or left gap
        double yPosIn_50 = calculateRelativePositionInChart(100, scatterChart.getPrefHeight(), defaultPercent) + top_gap - 38;
        double xPosIn_0_3 = calculateRelativePositionInChart(1.2, scatterChart.getPrefWidth(), defaultMciIndex) + left_gap - 8;


        //Setting up horizontal lines in scatter chart
        Line horizontalLine = new Line(xMin, yPosIn_50, xMax, yPosIn_50);
        horizontalLine.setStrokeWidth(strokeWidth);


        horizontalLine.setOnMouseEntered((event -> {
            horizontalLine.setCursor(Cursor.HAND);
        }));

        //Only change in y
        horizontalLine.setOnMouseDragged((event -> {
            double x = event.getX();
            double y = event.getY();
            horizontalLine.setStartX(xMin);
            horizontalLine.setEndX(xMax);
            if (y > yMax) {
                y = yMax;
            }
            if (y < yMin) {
                y = yMin;
            }
            horizontalLine.setStartY(y);
            horizontalLine.setEndY(y);

            /*
             * We are calculate relative position from fix point 50
             * 1. we know y coordinates at 50
             * 2. the we calculate y coordinates relative to 50
             * */

            double calYinYAxis = y - top_gap;
            double newYposIn_50 = yPosIn_50 - top_gap;
            defaultPercent = ((calYinYAxis * 50) / newYposIn_50);

            defaultPercent = 100 - defaultPercent;

            updateList(mciArrayList, studentPercentage, studentNames, defaultPercent, defaultMciIndex, false);
        }));


        Line verticalLine = new Line(xPosIn_0_3, yMin, xPosIn_0_3, yMax);
        verticalLine.setStrokeWidth(strokeWidth);

        verticalLine.setOnMouseDragged(event -> {
            double x = event.getX();

            if (x < xMin) x = xMin;
            if (x > xMax) x = xMax;

            verticalLine.setStartX(x);
            verticalLine.setEndX(x);

            //System.out.println(x);


            /*
             * We are calculate relative position from fix point 0.3
             * 1. we know x coordinates at 0.3
             * 2. the we calculate x coordinates relative to 0.3
             * */

            double calXinAxis = x - left_gap;
            double newXposIn_0_3 = xPosIn_0_3 - left_gap;
            defaultMciIndex = (calXinAxis * 0.3) / newXposIn_0_3;

            //defaultMciIndex = (Math.round(defaultMciIndex*10.0))/10.0;

            updateList(mciArrayList, studentPercentage, studentNames, defaultPercent, defaultMciIndex, false);


            //System.out.println(defaultMciIndex);

            verticalLine.setStartY(yMin);
            verticalLine.setEndY(yMax);

        });

        verticalLine.setOnMousePressed(event -> {
            verticalLine.setCursor(Cursor.HAND);
        });

        updateList(mciArrayList, studentPercentage, studentNames, defaultPercent, defaultMciIndex, false);


        anchorPaneStudentMCI.getChildren().addAll(studentMciIndexLabel, scatterChart, jfxListView, horizontalLine, verticalLine);


        /*End Student Mci*/


        /*Question MCI*/

        /*
         * 1. Getting necessary list to plot points in scatter chart
         * 2. Difficulty Level is property of question, that is we are using difficulty level instead of percent
         * 3. use questionMCI prefix in every variable name
         * */
        ArrayList<String> questionList = guttDataHelper.getQuestionNumberListFromGutt();
        ArrayList<Double> questionMCI = ModifiedCautionIndexCalculation.getQuestionMCI(gutArray);
        ArrayList<Double> questionDifficultyLevel = guttDataHelper.getQuestionDifficultyLevelFromGutt();


        //Heading Label
        Label questionMciLabel = getSegoiLabel(QUESTION_MCI_HEADING, HEADING_SIZE);
        questionMciLabel.setLayoutX(200);
        questionMciLabel.setLayoutY(80);

        //Setting up listView to display question number according to quadrant
        questionMCIListView.setExpanded(true);
        questionMCIListView.setDepth(Constants.LIST_DEPTH);
        questionMCIListView.setOrientation(Orientation.VERTICAL);
        questionMCIListView.setLayoutX(720);
        questionMCIListView.setLayoutY(150);

        //Setting up scatter chart
        ScatterChart<Number, Number> questionMCIScatterChart = ChartHelper.getScatterChart(questionMCI, questionList, questionDifficultyLevel, true);
        questionMCIScatterChart.setPrefWidth(700);
        questionMCIScatterChart.setPrefHeight(500);
        questionMCIScatterChart.setStyle("-fx-background-color : #EEF1F6");
        questionMCIScatterChart.setLayoutX(20);
        questionMCIScatterChart.setLayoutY(140);


        // You may need add and subtract number to adjust line to correct line
        // Number is dependent on top gap or left gap
        // getting relative position of points in chart according to defaultPos
        double scatterChartYPosInDefaultPos = calculateRelativePositionInChart(100, questionMCIScatterChart.getPrefHeight(), defaultDifficultyLevel) + top_gap - 38;
        double scatterChartXPosInDefaultPos = calculateRelativePositionInChart(1.2, questionMCIScatterChart.getPrefWidth(), defaultMciIndex) + left_gap - 8;


        //Setting up horizontal lines in scatter chart
        Line questionMCIHorizontalLine = new Line(xMin, scatterChartYPosInDefaultPos, xMax, scatterChartYPosInDefaultPos);
        questionMCIHorizontalLine.setStrokeWidth(strokeWidth);


        questionMCIHorizontalLine.setOnMouseEntered((event -> {
            questionMCIHorizontalLine.setCursor(Cursor.HAND);
        }));

        //Only change in y
        questionMCIHorizontalLine.setOnMouseDragged((event -> {
            double x = event.getX();
            double y = event.getY();
            questionMCIHorizontalLine.setStartX(xMin);
            questionMCIHorizontalLine.setEndX(xMax);
            if (y > yMax) {
                y = yMax;
            }
            if (y < yMin) {
                y = yMin;
            }
            questionMCIHorizontalLine.setStartY(y);
            questionMCIHorizontalLine.setEndY(y);


            double calYinYAxis = y - top_gap;
            double scatterChartYPosInDefaultPosNew = scatterChartYPosInDefaultPos - top_gap;
            defaultDifficultyLevel = ((calYinYAxis * 50) / scatterChartYPosInDefaultPosNew);

            defaultDifficultyLevel = 100 - defaultDifficultyLevel;

            updateList(questionMCI, questionDifficultyLevel, questionList, defaultDifficultyLevel, defaultMciIndex, true);
        }));


        //Setting Up vertical lines in scatter chart
        Line questionMCIVerticalLine = new Line(scatterChartXPosInDefaultPos, yMin, scatterChartXPosInDefaultPos, yMax);
        questionMCIVerticalLine.setStrokeWidth(strokeWidth);

        // Changing cursor
        questionMCIVerticalLine.setOnMousePressed(event -> {
            questionMCIVerticalLine.setCursor(Cursor.HAND);
        });

        //Dragging line
        questionMCIVerticalLine.setOnMouseDragged(event -> {
            double x = event.getX();

            if (x < xMin) x = xMin;
            if (x > xMax) x = xMax;

            questionMCIVerticalLine.setStartX(x);
            questionMCIVerticalLine.setEndX(x);

            //System.out.println(x);

            double calXinAxis = x - left_gap;
            double scatterChartXPosInDefaultPosNew = scatterChartXPosInDefaultPos - left_gap;
            defaultMciIndex = (calXinAxis * 0.3) / scatterChartXPosInDefaultPosNew;

            //defaultMciIndex = (Math.round(defaultMciIndex*10.0))/10.0;

            updateList(questionMCI, questionDifficultyLevel, questionList, defaultDifficultyLevel, defaultMciIndex, true);

            questionMCIVerticalLine.setStartY(yMin);
            questionMCIVerticalLine.setEndY(yMax);

        });


        updateList(questionMCI, questionDifficultyLevel, questionList, defaultDifficultyLevel, defaultMciIndex, true);

        anchorPaneQuestionMCI.getChildren().addAll(questionMciLabel, questionMCIScatterChart, questionMCIListView, questionMCIHorizontalLine, questionMCIVerticalLine);

        /*End Question MCI*/

    }

    private void updateList(ArrayList<Double> mciArrayList, ArrayList<Double> yAxisPoints, ArrayList<String> displayContent, double lineYPos, double lineMci, boolean questionMCI) {


        VBox topRight = new VBox(getSegoiLabel(TOP_RIGHT, Constants.STANDARD_FONT_SIZE));
        VBox topLeft = new VBox(getSegoiLabel(TOP_LEFT, Constants.STANDARD_FONT_SIZE));
        VBox downRight = new VBox(getSegoiLabel(DOWN_RIGHT, Constants.STANDARD_FONT_SIZE));
        VBox downLeft = new VBox(getSegoiLabel(DOWN_LEFT, Constants.STANDARD_FONT_SIZE));

        //Converting difficulty from percent to decimal points
        if (questionMCI) lineYPos /= 100.0;

        for (int i = 0; i < mciArrayList.size(); i++) {

            double mci = mciArrayList.get(i);
            double yPos = yAxisPoints.get(i);
            double roundMci = Math.round(mci * 100000.0) / 100000.0;
            Label label = new Label(displayContent.get(i) + "\t=\t" + roundMci);


            if (yPos > lineYPos) {

                if (mci < lineMci) {
                    //TOP
                    topLeft.getChildren().add(label);
                    if (questionMCI) {
                        System.out.println("TOP LEFT");
                        System.out.println("Nodes Difficulty level : " + yPos + " line current position : " + lineYPos + " Label Content :" + label.getText());
                        //System.out.println(lineYPos +  " " + yPos);
                        System.out.println();
                    }
                } else {
                    if (questionMCI) {
                        System.out.println("TOP RIGHT");
                        System.out.println("Nodes Difficulty level : " + yPos + " line current position : " + lineYPos + " Label Content :" + label.getText());
                        System.out.println();
                    }
                    topRight.getChildren().add(label);
                }
            } else {
                //Down
                /*if (questionMCI)
                System.out.println("Check Down MCI : " + mci +" line : " + lineMci + " YPOS : " + yPos + " Line y POS : "+lineYPos);
                */
                if (mci < lineMci) downLeft.getChildren().add(label);
                else downRight.getChildren().add(label);
            }

        }

        HBox hbox = new HBox(topLeft, topRight, downLeft, downRight);
        hbox.setLayoutX(720);
        hbox.setLayoutY(150);
        hbox.setSpacing(20);


        if (!questionMCI) {
            jfxListView.getItems().clear();
            jfxListView.getItems().addAll(topLeft, topRight, downLeft, downRight);
        } else {
            questionMCIListView.getItems().clear();
            questionMCIListView.getItems().addAll(topLeft, topRight, downLeft, downRight);
        }
    }

    private double calculateRelativePositionInChart(double upperBoundInAxis, double lengthOfAxis, double value) {
        //Remove space required by number labels Axis
        lengthOfAxis -= 10;
        double calPos = (value * lengthOfAxis) / upperBoundInAxis;
        return calPos;
    }
}
