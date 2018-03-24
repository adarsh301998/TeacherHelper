package sample.Controllers.ReportControllers;

import HelperClasses.ChartHelper;
import HelperClasses.Constants;
import HelperClasses.DialogPopUp;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DataClasses.Bus;

import java.io.IOException;

public class BinaryCorelationController {
    @FXML
    private StackPane stackPane;

    @FXML
    private FontAwesomeIcon close_btn;

    @FXML
    private JFXButton back_btn;

    @FXML
    private Label titlePaneHeading;

    @FXML
    private VBox vbox;

    public void initialize() {

        titlePaneHeading.setText(Constants.CORRELATION);

        BarChart<String, Number> binaryCorelationChart = ChartHelper.binaryDistractorChart();

        vbox.setPrefWidth(binaryCorelationChart.getPrefWidth());

        System.out.println("Stack Pane = " + stackPane.getPrefHeight());
        vbox.setPrefHeight(stackPane.getPrefHeight());
        vbox.setAlignment(Pos.CENTER);


        vbox.getChildren().add(binaryCorelationChart);

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
    void displayBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, Bus.getInstance());
    }
}
