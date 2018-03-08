package HelperClasses;

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

public class ChartHelper {

    public static BarChart distrcatorChart(ArrayList<Integer> response, ArrayList<Integer> distractor) {

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

        // Series for response
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName("Response");
        for (int i = 0; i < option.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(option.get(i), response.get(i));
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


        //Series for distrcators
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series2.setName("Distractor");
        for (int i = 0; i < option.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(option.get(i), distractor.get(i));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        displayLabelForData(data);
                    }
                }
            });
            series2.getData().add(data);
        }


        barChart.getData().addAll(series1, series2);
        barChart.setPrefHeight(300);
        barChart.setPrefWidth(400);
        //barChart.setStyle("-fx-background-color: #3498db");

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
                dataText.setLayoutY(y);
            }
        });


    }


}
