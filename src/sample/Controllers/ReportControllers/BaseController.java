package sample.Controllers.ReportControllers;

import HelperClasses.DialogPopUp;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.DataClasses.Bus;

import java.io.IOException;

public class BaseController {

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton back_btn;

    @FXML
    private Label titlePaneHeading;

    @FXML
    private JFXButton minimizeButton;

    @FXML
    private JFXButton maximizeButton;

    @FXML
    private JFXButton closeButton;

    @FXML
    void displayBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, Bus.getInstance());
    }

    @FXML
    void titlePaneEvent(ActionEvent event) {

        if (event.getSource() == back_btn) {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("../scenes/reportOptions.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (event.getSource() == minimizeButton) {

        }
        if (event.getSource() == maximizeButton) {

        }
        if (event.getSource() == closeButton) {
            DialogPopUp.closeAlert(stackPane);
        }

    }


}
