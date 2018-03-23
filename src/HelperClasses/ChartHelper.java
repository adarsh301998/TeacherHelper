package HelperClasses;

import Analysis.Distractors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChartHelper {

    static int index = 0, index2 = 0;
    public static BarChart distrcatorChart(ArrayList<Integer> response, ArrayList<Double> distractor) {

        index = 0;
        index2 = 0;

        CategoryAxis x = new CategoryAxis();
        x.setCategories(FXCollections.<String>observableArrayList(Arrays.asList("A", "B", "C", "D")));
        x.setLabel("Options");

        NumberAxis y = new NumberAxis();
        y.setLabel("Response");

        BarChart<String, Number> barChart = new BarChart<>(x, y);

        ArrayList<String> option = new ArrayList<>();
        option.add("A");
        option.add("B");
        option.add("C");
        option.add("D");

        ArrayList<Double> scalledResponse = scalingBar(response);

        // Series for response
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(Constants.RESPONSE);
        for (int i = 0; i < option.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(option.get(i), scalledResponse.get(i));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        displayLabelForScalledChartData(data, String.valueOf(response.get(index)));
                        index++;
                    }
                }
            });
            series1.getData().add(data);
        }


        //Series for distrcators
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName(Constants.CORRELATION);
        for (int i = 0; i < option.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(option.get(i), distractor.get(i));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        displayLabelForScalledChartData(data, String.valueOf(distractor.get(index2)));
                        index2++;
                    }
                }
            });
            series2.getData().add(data);
        }


        barChart.getData().addAll(series1, series2);
        barChart.setPrefHeight(Constants.OPTION_CORRELATION_BAR_CHART_HEIGHT);
        barChart.setPrefWidth(Constants.OPTION_CORRELATION_BAR_CHART_WIDTH);
        //barChart.setStyle("-fx-background-color: #3498db");

        return barChart;

    }


    public static BarChart totalMarksBarChar() {

        ArrayList<String> studentRollNumbers = ListGenerationHelper.studentRollNumberList();

        List<Integer> studentTotalMarksList = ListGenerationHelper.studentsTotalMarks();

        CategoryAxis x = new CategoryAxis();
        x.setCategories(FXCollections.<String>observableArrayList(studentRollNumbers));
        x.setLabel("Roll Numbers");

        NumberAxis y = new NumberAxis();
        y.setLabel("Marks");

        BarChart<String, Number> barChart = new BarChart<>(x, y);

        // Series for response
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Total Marks");
        for (int i = 0; i < studentTotalMarksList.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(studentRollNumbers.get(i), studentTotalMarksList.get(i));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        displayLabelForData(data);
                    }
                }
            });
            series1.getData().add(data);
        }

        barChart.getData().addAll(series1);
        double prefWidth = (studentTotalMarksList.size() * 100.0) * (3.0 / 4.0);
        barChart.setPrefHeight(450);
        barChart.setPrefWidth(prefWidth);
        //barChart.setStyle("-fx-background-color: #3498db");

        return barChart;
    }

    public static BarChart binaryDistractorChart() {


        ArrayList<Double> distractors = Distractors.getBinaryDistractors();
        ArrayList<String> quesList = ListGenerationHelper.questionList();

        CategoryAxis x = new CategoryAxis();
        x.setCategories(FXCollections.<String>observableArrayList(quesList));
        x.setLabel("Questions");

        NumberAxis y = new NumberAxis();
        //y.setLabel("Distractors");

        BarChart<String, Number> barChart = new BarChart<>(x, y);

        // Series for response
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(Constants.CORRELATION);
        for (int i = 0; i < distractors.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(quesList.get(i), distractors.get(i));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        displayLabelForData(data);
                    }
                }
            });
            series1.getData().add(data);
        }

        barChart.getData().addAll(series1);
        double prefWidth = (quesList.size() * 100.0) * (3.0 / 4.0);
        barChart.setPrefHeight(450);
        barChart.setPrefWidth(prefWidth);

        return barChart;
    }


    private static void displayLabelForData(XYChart.Data<String, Number> data) {
        final Node node = data.getNode();

        final Text dataText = new Text(data.getYValue() + " ");

        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {

                Group parentGroup = (Group) newValue;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                dataText.setLayoutX(Math.round(newValue.getMinX() + newValue.getWidth() / 2 - dataText.prefWidth(-1) / 2));
                long y = Math.round(newValue.getMinY() - dataText.prefHeight(-1) * 0.5);
                if (dataText.getText().charAt(0) == '-') {
                    y = Math.round(newValue.getMaxY() + dataText.prefHeight(-1));
                }
                dataText.setLayoutY(y);
            }
        });


    }

    private static void displayLabelForScalledChartData(XYChart.Data<String, Number> data, String text) {
        final Node node = data.getNode();

        final Text dataText = new Text(text);

        node.parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {

                Group parentGroup = (Group) newValue;
                parentGroup.getChildren().add(dataText);
            }
        });

        node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
            @Override
            public void changed(ObservableValue<? extends Bounds> observable, Bounds oldValue, Bounds newValue) {
                dataText.setLayoutX(Math.round(newValue.getMinX() + newValue.getWidth() / 2 - dataText.prefWidth(-1) / 2));
                long y = Math.round(newValue.getMinY());
                if (text.charAt(0) == '-') {
                    // getting y coordinates in case of minus sign
                    y = Math.round(newValue.getMaxY() + newValue.getWidth() / 2);
                }
                dataText.setLayoutY(y);
            }
        });


    }

    private static ArrayList<Double> scalingBar(ArrayList<Integer> response) {

        int max = Collections.max(response);
        max += 10;

        ArrayList<Double> list = new ArrayList<>();

        for (int i = 0; i < response.size(); i++) {

            double d = (double) response.get(i) / max;
            list.add(d);

        }

        return list;


    }



}
