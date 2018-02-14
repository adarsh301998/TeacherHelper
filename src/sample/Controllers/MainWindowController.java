package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DataClasses.TestDetails;

import java.io.IOException;

//import sample.DataClasses.DataInstance;

public class MainWindowController {
    @FXML
    private FontAwesomeIcon btn_close;

    @FXML
    private JFXButton btn_new;

    @FXML
    private JFXButton btn_open;

    @FXML
    private JFXButton btn_key;

    @FXML
    private JFXButton btn_report;

    @FXML
    private AnchorPane rootScene;


    @FXML
    public void initialize() {
    }


    @FXML
    void closeEvent(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void handelEvent(ActionEvent event) throws IOException {

        Parent new_win;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (event.getSource() == btn_new) {
            new_win = (AnchorPane) FXMLLoader.load(getClass().getResource("scenes/createNewTest.fxml"));
            Scene scene = new Scene(new_win);

            TestDetails testDetails = new TestDetails();

            /*if (DataInstance.getInstance().getTestDetails() != null) {

                testDetails.setIndex(DataInstance.getInstance().getTestDetails().size());
                DataInstance.getInstance().getTestDetails().add(testDetails);
            } else {
                testDetails.setIndex(0);
                DataInstance.setInstance(new MainDataClass());
                DataInstance.getInstance().setTestDetails(new ArrayList<>());
                DataInstance.getInstance().getTestDetails().add(testDetails);
            }*/


            stage.setScene(scene);
        }
        if (event.getSource() == btn_open) {
            new_win = (AnchorPane) FXMLLoader.load(getClass().getResource("scenes/openTests.fxml"));
            Scene scene = new Scene(new_win);
            stage.setScene(scene);
        }

    }
}
