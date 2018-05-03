package sample.Controllers.ReportControllers;

import HelperClasses.ArrayHelper;
import HelperClasses.ChartHelper;
import HelperClasses.GuttDataHelper;
import calculations.ModifiedCautionIndexCalculation;
import com.jfoenix.controls.JFXListView;
import javafx.geometry.Orientation;
import javafx.scene.Cursor;
import javafx.scene.chart.ScatterChart;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class MciIndexController extends BaseController {


    /*
        Gutt Table Structure
     * [StudentIndex, Evaluation, TotalMarks]
     *
     * */

    double defaultMciIndex = 0.3;
    double defaultPercent = 50.0;


    JFXListView<VBox> jfxListView = new JFXListView<>();


    public void initialize() {

        checkMaximize();


        int[][] gutArray = ArrayHelper.getGuttImprovised();

        GuttDataHelper guttDataHelper = new GuttDataHelper(gutArray);

        ArrayList<Double> mciArrayList = ModifiedCautionIndexCalculation.getMCI(gutArray);

        ArrayList<String> rollNumbers = guttDataHelper.getRollNumberFromGutt();

        ArrayList<String> studentNames = guttDataHelper.getStudentnameFromGutt();

        ArrayList<Double> studentPercentage = guttDataHelper.getStudentPercentFromGutt();

        Label studentMciIndexLable = new Label("STUDENT MCI INDEX");
        studentMciIndexLable.setFont(new Font("Segoi UI", 40));
        studentMciIndexLable.setLayoutX(200);
        studentMciIndexLable.setLayoutY(80);

        jfxListView.setExpanded(true);
        jfxListView.setDepth(3);
        jfxListView.setOrientation(Orientation.VERTICAL);
        jfxListView.setLayoutX(720);
        jfxListView.setLayoutY(150);

        anchorPaneResize.getChildren().add(jfxListView);

        anchorPaneResize.getChildren().add(studentMciIndexLable);

        ScatterChart<Number, Number> scatterChart = ChartHelper.getScatterChart(mciArrayList, rollNumbers, studentPercentage);
        scatterChart.setPrefWidth(700);
        scatterChart.setPrefHeight(500);
        scatterChart.setStyle("-fx-background-color : #EEF1F6");

        scatterChart.setLayoutX(20);
        scatterChart.setLayoutY(140);
        anchorPaneResize.getChildren().add(scatterChart);

        double strokeWidth = 3;
        int xMin = (int) scatterChart.getLayoutX() + 52;
        int xMax = (int) (scatterChart.getPrefWidth());
        int yMin = (int) scatterChart.getLayoutY() + 18;
        // (int)scatterChart.getPrefHeight() + 45
        int top_gap = (int) scatterChart.getLayoutY();
        int yMax = (int) scatterChart.getPrefHeight() + top_gap - 100;

        double left_gap = 65;
        // You may need add and subtract number to adjust line to correct line
        // Number is dependent on top gap or left gap
        double yPosIn_50 = calculateRelativePositionInChart(100, scatterChart.getPrefHeight(), 50) + top_gap - 38;
        double xPosIn_0_3 = calculateRelativePositionInChart(1.2, scatterChart.getPrefWidth(), 0.3) + left_gap - 8;


        //NumberAxis xAxis = (NumberAxis) scatterChart.getXAxis();
        //System.out.println(xAxis.getWidth());
        //double Tgap = xAxis.getWidth() / (xAxis.getUpperBound() - xAxis.getLowerBound());
        //double Tgap = xAxis.getWidth() / (xAxis.getUpperBound() - xAxis.getLowerBound());

        //System.out.println(Tgap);

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

            double calYinYAxis = y - top_gap;
            double newYposIn_50 = yPosIn_50 - top_gap;
            defaultPercent = ((calYinYAxis * 50) / newYposIn_50);

            defaultPercent = 100 - defaultPercent;

            updateList(mciArrayList, studentPercentage, studentNames, defaultPercent, defaultMciIndex);


        }));

        anchorPaneResize.getChildren().add(horizontalLine);

        Line verticalLine = new Line(xPosIn_0_3, yMin, xPosIn_0_3, yMax);
        verticalLine.setStrokeWidth(strokeWidth);

        verticalLine.setOnMouseDragged(event -> {
            double x = event.getX();

            if (x < xMin) x = xMin;
            if (x > xMax) x = xMax;

            verticalLine.setStartX(x);
            verticalLine.setEndX(x);

            //System.out.println(x);

            double calXinAxis = x - left_gap;
            double newXposIn_0_3 = xPosIn_0_3 - left_gap;
            defaultMciIndex = (calXinAxis * 0.3) / newXposIn_0_3;

            //defaultMciIndex = (Math.round(defaultMciIndex*10.0))/10.0;

            updateList(mciArrayList, studentPercentage, studentNames, defaultPercent, defaultMciIndex);


            //System.out.println(defaultMciIndex);

            verticalLine.setStartY(yMin);
            verticalLine.setEndY(yMax);

        });

        verticalLine.setOnMousePressed(event -> {
            verticalLine.setCursor(Cursor.HAND);
        });

        updateList(mciArrayList, studentPercentage, studentNames, defaultPercent, defaultMciIndex);

        anchorPaneResize.getChildren().add(verticalLine);


    }

    private void updateList(ArrayList<Double> mciArrayList, ArrayList<Double> studentPercent, ArrayList<String> rollNumber, double linePercent, double lineMci) {


        jfxListView.getItems().clear();
        VBox topRight = new VBox(new Label("Top Right"));
        VBox topLeft = new VBox(new Label("Top Left"));
        VBox downRight = new VBox(new Label("Down Right"));
        VBox downLeft = new VBox(new Label("Down Left"));


        for (int i = 0; i < mciArrayList.size(); i++) {

            double mci = mciArrayList.get(i);
            double percent = studentPercent.get(i);

            Label label = new Label(rollNumber.get(i));

            if (percent > linePercent) {
                if (mci < lineMci) {
                    topLeft.getChildren().add(label);
                    //System.out.println(linePercent +  " " + percent);
                } else topRight.getChildren().add(label);
            } else {
                if (mci < lineMci) downLeft.getChildren().add(label);
                else downRight.getChildren().add(label);
            }

        }

        HBox hbox = new HBox(topLeft, topRight, downLeft, downRight);
        hbox.setLayoutX(720);
        hbox.setLayoutY(150);
        hbox.setSpacing(20);


        jfxListView.getItems().addAll(topLeft, topRight, downLeft, downRight);

        if (anchorPaneResize.getChildren().size() > 3) {
            /*for (int i = 0;i<anchorPaneResize.getChildren().size();i++) {
                if (anchorPaneResize.getChildren().get(i) instanceof HBox) {
                    anchorPaneResize.getChildren().remove(i);
                    break;
                }
            }*/
        }
        //anchorPaneResize.getChildren().add(hbox);

    }

    private double calculateRelativePositionInChart(double upperBoundInAxis, double lengthOfAxis, double value) {
        //Remove space required by number labels Axis
        lengthOfAxis -= 10;
        double calPos = (value * lengthOfAxis) / upperBoundInAxis;
        return calPos;
    }
}
