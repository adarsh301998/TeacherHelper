package sample.BaseClasses;

import LocationHelper.ScenesLocations;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import sample.Main;

import java.io.IOException;

public class MainBaseController extends Main {


    @FXML
    public StackPane stackPane;


    @FXML
    public JFXButton home_btn;

    @FXML
    public AnchorPane anchorPaneResize;

    @FXML
    public JFXButton openStudents_btn;

    @FXML
    public JFXButton openTest_btn;

    @FXML
    public JFXButton openReport_btn;


    @FXML
    public JFXButton openKey_btn;

    @FXML
    public JFXButton newTest_btn;

    @FXML
    public JFXButton uploadTestKey_btn;

    @FXML
    public JFXButton uploadStudentDetails_btn;


    public MainBaseController() {
        stage.maximizedProperty().addListener((ov, minimumSize, maximumSize) -> {
            resizePane(maximumSize);
        });


    }

    public void setTollTip() {
        if (home_btn != null) {
            final Tooltip home_btn_tooltip = new Tooltip();
            home_btn_tooltip.setText("Home");
            home_btn.setTooltip(home_btn_tooltip);
        }
    }

    public void loadFrame(String frameLocation) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(frameLocation));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void homeEvent(ActionEvent actionEvent) {
        loadFrame(ScenesLocations.MAIN_SCENE);
    }

    private void resizePane(Boolean maximumSize) {
        if (anchorPaneResize != null) {

            if (maximumSize) {
                anchorPaneResize.setPrefWidth(screenMaxWidth);
                anchorPaneResize.setPrefHeight(screenMaxHeight);
            } else {

                anchorPaneResize.setPrefWidth(screenMinimunWidth);
                anchorPaneResize.setPrefHeight(screenMinimunHeight);
            }
        }

    }

    public void checkMaximize() {
        if (stage.isMaximized()) {
            anchorPaneResize.setPrefWidth(screenMaxWidth);
            anchorPaneResize.setPrefHeight(screenMaxHeight);
        }
    }

    @FXML
    public void navigationActionEvent(ActionEvent actionEvent) {
        String scene = "";
        if (actionEvent.getSource() == openStudents_btn)
            scene = ScenesLocations.OPEN_STUDENT_SCENE;
        if (actionEvent.getSource() == openReport_btn)
            scene = ScenesLocations.REPORT_OPTION_SCENE;
        if (actionEvent.getSource() == openTest_btn)
            scene = ScenesLocations.OPEN_TEST_SCENE;
        if (actionEvent.getSource() == newTest_btn)
            scene = ScenesLocations.CREATE_NEW_TEST_SCENE;
        if (actionEvent.getSource() == openKey_btn)
            scene = ScenesLocations.KEY_SCENE;

        loadFrame(scene);

    }

    public Font getSegoiFontSyle(int size) {

        Font font = new Font("Segoi UI", size);

        return font;

    }

    public Label getSegoiLabel(String labelText, int size) {
        Label label = new Label(labelText);
        label.setFont(getSegoiFontSyle(size));
        return label;
    }


}
