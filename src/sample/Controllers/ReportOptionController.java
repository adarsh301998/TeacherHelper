package sample.Controllers;

import HelperClasses.DialogPopUp;
import HelperClasses.NavigationHelper;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import sample.BaseClasses.MainBaseController;
import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

public class ReportOptionController extends MainBaseController {
    @FXML
    private AnchorPane titlePane;

    @FXML
    private FontAwesomeIcon close_btn;

    @FXML
    private Label reportOption;

    @FXML
    private VBox navigationLayout;
/*
    @FXML
    private JFXButton open_btn;

    @FXML
    private JFXButton student_btn;

    @FXML
    private JFXButton key_btn;

    @FXML
    private JFXButton newTest_btn;*/

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
        setTollTip();
        testDetails = Bus.getInstance();
        navigationHelper = new NavigationHelper();
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

        loadFrame(frame);

    }

    @FXML
    void reportOptionClicked(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stack_pane, testDetails);

    }
}
