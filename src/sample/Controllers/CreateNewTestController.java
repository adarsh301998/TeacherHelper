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
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.TestDetails;

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

    TestDetails testDetails;

    @FXML
    void closeEvent(MouseEvent event) {
        System.exit(0);
    }

    public void initialize() {
        testDetails = new TestDetails();
        List combo_box_list = new ArrayList();
        combo_box_list.add("Birla Institute Of Technology");
        institute_name.getItems().addAll(combo_box_list);
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
            if (date == null) {
                date = LocalDate.now();
            }
            if (institute == null) {
                institute = institute_name.getItems().get(0).toString();
            }

            //Initialize other data
            testDetails.setTestName(testname);
            testDetails.setNumberOfStudent(num_student);
            testDetails.setNumberOfQuestion(ques);
            testDetails.setTeacherName(teacherName);
            testDetails.setDateTime(date);
            testDetails.setInstitute(institute);
            testDetails.setClassLabel(classLabel);

            testname += date.toString();
            //Save Data
            DataBaseCommunication.convertJavaToJSON(testDetails, testname);

            // Open key map scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scenes/keyMap.fxml"));
            Parent root = loader.load();
            KeyMapController controller = loader.getController();
            controller.init_create(testDetails);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        }
    }


    private void initialize_arrayList(int ques, int num_students) {
        testDetails.setQuestion(new ArrayList<>());
        testDetails.setKey(new ArrayList<>());
        testDetails.setStudentDetails(new ArrayList<>());
        testDetails.setSubQuestionList(new ArrayList<>());
        for (int i = 0; i < ques; i++) {
            testDetails.getQuestion().add(null);
            testDetails.getSubQuestionList().add(null);
            testDetails.getKey().add(new ArrayList<>());
        }

        for (int i = 0; i < num_students; i++) {
            testDetails.getStudentDetails().add(null);
        }
    }
}
