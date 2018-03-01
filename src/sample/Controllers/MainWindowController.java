package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

//import sample.DataClasses.DataInstance;

public class MainWindowController {
    /*@FXML
    private FontAwesomeIcon btn_close;*/

    @FXML
    private JFXButton btn_new;

    @FXML
    private JFXButton btn_open;

//    @FXML
//    private JFXButton btn_key;
//
//    @FXML
//    private JFXButton btn_report;

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

        FXMLLoader loader = new FXMLLoader();
        Parent new_win;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (event.getSource() == btn_new) {
            loader.setLocation(getClass().getResource("scenes/createNewTest.fxml"));

        }
        if (event.getSource() == btn_open) {
            loader.setLocation(getClass().getResource("scenes/openTest.fxml"));
        }

        new_win = loader.load();
        stage.setScene(new Scene(new_win));

    }
}
