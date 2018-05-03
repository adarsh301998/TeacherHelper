package sample.Controllers.ReportControllers;

import HelperClasses.ChartHelper;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class BinaryCorelationController extends BaseController {

    @FXML
    private FontAwesomeIcon close_btn;

    @FXML
    private JFXButton back_btn;

    @FXML
    private Label titlePaneHeading;

    @FXML
    private VBox vbox;

    public void initialize() {
        anchorPaneResize.setPrefWidth(screenMinimunWidth);
        anchorPaneResize.setPrefHeight(screenMinimunHeight);
        anchorPaneResize.setPrefHeight(screenMinimunHeight);
        checkMaximize();

        BarChart<String, Number> binaryCorelationChart = ChartHelper.binaryDistractorChart();

        vbox.setPrefWidth(binaryCorelationChart.getPrefWidth());
        vbox.setPrefHeight(stackPane.getPrefHeight());
        vbox.setAlignment(Pos.CENTER);

        vbox.getChildren().add(binaryCorelationChart);

    }

    /*@FXML
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
    }*/
}
