package HelperClasses;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationHelper {

    public void loadFrame(ActionEvent event, JFXButton open, JFXButton student, JFXButton key, JFXButton report, JFXButton newTest) {

        FXMLLoader loader = new FXMLLoader();
        Parent root;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (event.getSource() == key) {
            //open key
            loader.setLocation(getClass().getResource("scenes/keyMap.fxml"));
        }
        if (event.getSource() == open) {
            //Open test
            loader.setLocation(getClass().getResource("scenes/openTest.fxml"));
        }
        if (event.getSource() == report) {
            //Open report
            loader.setLocation(getClass().getResource("scenes/reportOptions.fxml"));
        }
        if (event.getSource() == newTest) {
            loader.setLocation(getClass().getResource("scenes/createNewTest.fxml"));
        }
        if (event.getSource() == student) {
            loader.setLocation(getClass().getResource("scenes/studentDetails.fxml"));
        }

        try {
            root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void loadFile(ActionEvent event, String fileName) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(fileName));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
