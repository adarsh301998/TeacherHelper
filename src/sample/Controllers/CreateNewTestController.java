package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DataClasses.DataInstance;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreateNewTestController {
    @FXML
    private JFXTextField testName_txt;

    @FXML
    private JFXTextField students_txt;

    @FXML
    private JFXTextField question_txt;

    @FXML
    private JFXTextField teacherName_txt;

    @FXML
    private JFXDatePicker datePicker;

    @FXML
    private ComboBox<?> institute_name;

    @FXML
    private JFXButton createTest_btn;

    @FXML
    private FontAwesomeIcon btn_close;


    @FXML
    private JFXTextField classname;

    // public static TestDetails testDetails;

    int testIndex;

    @FXML
    void closeEvent(MouseEvent event) {
        System.exit(0);
    }

    public void initialize() {
        List combo_box_list = new ArrayList();
        combo_box_list.add("Birla Institute Of Technology");
        institute_name.getItems().addAll(combo_box_list);

        testIndex = DataInstance.getInstance().getTestDetails().size();
    }

    @FXML
    void createTestEvent(ActionEvent event) throws IOException {
        if (event.getSource().equals(createTest_btn)) {
            String testname = testName_txt.getText();
            int num_student = Integer.parseInt(students_txt.getText());
            int ques = Integer.parseInt(question_txt.getText());
            String teacherName = teacherName_txt.getText();
            LocalDate date = datePicker.getValue();
            String institute = (String) institute_name.getValue();
            String classLabel = classname.getText();

            //Initialize Question,Key,Student arrayList
            initialize_arrayList(ques, num_student);

            //Apply some validation

            //Initialize other data
            DataInstance.getInstance().getTestDetails().get(testIndex).setTestName(testname);
            DataInstance.getInstance().getTestDetails().get(testIndex).setNumberOfStudent(num_student);
            DataInstance.getInstance().getTestDetails().get(testIndex).setNumberOfQuestion(ques);
            DataInstance.getInstance().getTestDetails().get(testIndex).setTeacherName(teacherName);
            DataInstance.getInstance().getTestDetails().get(testIndex).setDateTime(date);
            DataInstance.getInstance().getTestDetails().get(testIndex).setInstitute(institute);
            DataInstance.getInstance().getTestDetails().get(testIndex).setClassLabel(classLabel);

            System.out.println("Index" + testIndex);
            // Open key map scene
            Parent root = FXMLLoader.load(getClass().getResource("scenes/keyMap.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        }
    }

    private void initialize_arrayList(int ques, int num_students) {
        DataInstance.getInstance().getTestDetails().get(testIndex).setQuestion(new ArrayList<>());
        DataInstance.getInstance().getTestDetails().get(testIndex).setKey(new ArrayList<>());
        DataInstance.getInstance().getTestDetails().get(testIndex).setStudentDetails(new ArrayList<>());
        DataInstance.getInstance().getTestDetails().get(testIndex).setSubQuestionList(new ArrayList<>());
        for (int i = 0; i < ques; i++) {
            DataInstance.getInstance().getTestDetails().get(testIndex).getQuestion().add(null);
            DataInstance.getInstance().getTestDetails().get(testIndex).getSubQuestionList().add(null);
            DataInstance.getInstance().getTestDetails().get(testIndex).getKey().add(new ArrayList<>());
        }

        for (int i = 0; i < num_students; i++) {
            DataInstance.getInstance().getTestDetails().get(testIndex).getStudentDetails().add(null);
        }
    }
}
