package sample.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sample.DataClasses.TestDetails;

public class StudentDialogController {
    @FXML
    private VBox vbox;

    @FXML
    void closeEvent(MouseEvent event) {

    }


    public void init(TestDetails testDetails, int index) {

        Label studentName = new Label(testDetails.getStudentDetails().get(index).getName());
        Label teacherName = new Label(testDetails.getTeacherName());
        Label instituteName = new Label(testDetails.getInstitute());
        Label date = new Label(testDetails.getDateTime().toString());

        vbox.getChildren().addAll(studentName, teacherName, instituteName, date);


    }

}
