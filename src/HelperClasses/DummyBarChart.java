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

public class DummyBarChart {
    /*dummy barchart to calculate Y coordinate */
    public double lineY;

    public BarChart getDoubleDummyBarChart(ArrayList<String> xArrayList, ArrayList<Double> yArrayList, String xLabel, String yLabel, String seriesLabel, double lowerBound, double upperBound, double tickUnit) {

        CategoryAxis x = new CategoryAxis();
        x.setCategories(FXCollections.<String>observableArrayList(xArrayList));
        x.setLabel(xLabel);

        NumberAxis y = new NumberAxis(lowerBound, upperBound, tickUnit);
        y.setLabel(yLabel);

        BarChart<String, Number> barChart = new BarChart<>(x, y);

        // Series for response
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        series1.setName(seriesLabel);
        for (int i = 0; i < yArrayList.size(); i++) {
            final XYChart.Data<String, Number> data = new XYChart.Data(xArrayList.get(i), yArrayList.get(i));
            data.nodeProperty().addListener(new ChangeListener<Node>() {
                @Override
                public void changed(ObservableValue<? extends Node> observable, Node oldValue, Node newValue) {
                    if (newValue != null) {
                        displayLabelForDummyData(data);
                    }
                }
            });
            series1.getData().add(data);
        }

        barChart.getData().addAll(series1);
        double prefWidth = (yArrayList.size() * 100.0) * (1.0 / 2.0);
        barChart.setPrefHeight(500);
        barChart.setPrefWidth(prefWidth);
        //barChart.setStyle("-fx-background-color: #3498db");

        return barChart;
    }

    private void displayLabelForDummyData(XYChart.Data<String, Number> data) {
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
                lineY = newValue.getMinY();
                System.out.println("Min y : " + newValue.getMinY());

                if (dataText.getText().charAt(0) == '-') {
                    y = Math.round(newValue.getMaxY() + dataText.prefHeight(-1));
                }
                dataText.setLayoutY(y);
            }
        });

    }

    /*dummy bar chart end*/
}
