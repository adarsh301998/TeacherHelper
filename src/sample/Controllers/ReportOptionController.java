package sample.Controllers;

import HelperClasses.DialogPopUp;
import HelperClasses.NavigationHelper;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

import java.io.IOException;

public class ReportOptionController {
    @FXML
    private AnchorPane titlePane;

    @FXML
    private FontAwesomeIcon close_btn;

    @FXML
    private Label reportOption;

    @FXML
    private VBox navigationLayout;

    @FXML
    private JFXButton open_btn;

    @FXML
    private JFXButton student_btn;

    @FXML
    private JFXButton key_btn;

    @FXML
    private JFXButton newTest_btn;

    @FXML
    private VBox vbox_optionLayout;

    @FXML
    private JFXButton studentResponse_btn;

    @FXML
    private JFXButton distractors_btn;

    @FXML
    private StackPane stack_pane;

    @FXML
    private JFXButton totalMarksBarChart_btn;

    @FXML
    private JFXButton guttFile_btn;

    @FXML
    private JFXButton binaryCorelation_btn;

    TestDetails testDetails;
    NavigationHelper navigationHelper;

    public void initialize() {
        testDetails = Bus.getInstance();
        navigationHelper = new NavigationHelper();
    }

    @FXML
    void closeEvent(MouseEvent event) {
        DialogPopUp.closeAlert(stack_pane);
    }

    private void openFrame(String frame, ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(frame));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handelNavigationOpenEvent(ActionEvent event) {
        String frame = "";
        if (event.getSource() == key_btn) {
            //open key
            frame = "scenes/keyMap.fxml";
        }
        if (event.getSource() == open_btn) {
            //Open test
            frame = "scenes/openTest.fxml";
        }
        if (event.getSource() == newTest_btn) {
            frame = "scenes/createNewTest.fxml";
        }
        if (event.getSource() == student_btn) {
            frame = "scenes/studentDetails.fxml";
        }


        // navigationHelper.loadFile(event, frame);
        openFrame(frame, event);

    }

    @FXML
    void openReportOption(ActionEvent event) {
        String frame = "";
        if (event.getSource() == studentResponse_btn) {
            frame = "scenes/report/displayStudentResponse.fxml";
        }
        if (event.getSource() == distractors_btn) {
            frame = "scenes/report/distractor.fxml";
        }
        if (event.getSource() == totalMarksBarChart_btn) {
            frame = "scenes/report/totalMarksBarChart.fxml";
        }
        if (event.getSource() == binaryCorelation_btn) {
            frame = "scenes/report/binaryCorelations.fxml";
        }
        if (event.getSource() == guttFile_btn) {
            frame = "scenes/report/gutt_file.fxml";
        }

        openFrame(frame, event);

    }

    @FXML
    void reportOptionClicked(MouseEvent event) {
        // TitlePane lable cicked

        DialogPopUp.basicInfoDialog(stack_pane, testDetails);

    }
}
