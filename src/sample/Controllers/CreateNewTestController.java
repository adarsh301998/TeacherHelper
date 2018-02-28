package sample.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.io.*;
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
    private JFXComboBox<?> institute_name;

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
        datePicker.setValue(LocalDate.now());
        // Adding validations
        RequiredFieldValidator textValidator1 = new RequiredFieldValidator();
        RequiredFieldValidator textValidator2 = new RequiredFieldValidator();
        RequiredFieldValidator textValidator3 = new RequiredFieldValidator();

        NumberValidator numberValidator1 = new NumberValidator();
        NumberValidator numberValidator2 = new NumberValidator();

        try {
            Image image = new Image(new FileInputStream("src//icons//error.png"));
            ImageView imageView = new ImageView(image);
            textValidator1.setMessage("Cant be empty");
            textValidator2.setMessage("Cant be empty");
            textValidator3.setMessage("Cant be empty");
            numberValidator1.setMessage("Valid number");
            numberValidator2.setMessage("Valid number");
            textValidator1.setIcon(imageView);
            textValidator2.setIcon(imageView);
            textValidator3.setIcon(imageView);
            numberValidator1.setIcon(imageView);
            numberValidator2.setIcon(imageView);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        classname.getValidators().add(textValidator3);
        teacherName_txt.getValidators().add(textValidator2);
        students_txt.getValidators().add(numberValidator1);
        question_txt.getValidators().add(numberValidator2);
        testName_txt.getValidators().add(textValidator1);


    }

    @FXML
    void createTestEvent(ActionEvent event) throws IOException {
        if (testName_txt.validate())
            if (event.getSource().equals(createTest_btn) &&
                    testName_txt.validate() && students_txt.validate() && question_txt.validate() && teacherName_txt.validate() && classname.validate()) {
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
                DataBaseCommunication.convertJavaToJSON(testDetails);
                saveFileName(testname);


                // Send data in main bus
                Bus.setInstance(testDetails);
                // Open key map scene
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scenes/keyMap.fxml"));
            Parent root = loader.load();
            /*KeyMapController controller = loader.getController();
            controller.init(testDetails);*/

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            scene.setFill(Color.TRANSPARENT);
            stage.setScene(scene);
        }
    }

    private void saveFileName(String testname) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src//Tests/testName.txt"));
            String line;
            testname += "\n";
            while ((line = reader.readLine()) != null) {
                testname += line + "\n";
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("src//Tests/testName.txt"));
            writer.write(testname);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private void initialize_arrayList(int ques, int num_students) {
        testDetails.setQuestion(new ArrayList<>());
        testDetails.setKey(new ArrayList<>());
        testDetails.setStudentDetails(new ArrayList<>());
        testDetails.setSubQuestionList(new ArrayList<>());

        for (int i = 0; i < ques; i++) {
            testDetails.getQuestion().add(String.valueOf(i + 1));
            testDetails.getSubQuestionList().add(null);
            testDetails.getKey().add(new ArrayList<>());
        }

        for (int i = 0; i < num_students; i++) {
            StudentDetails studentDetails = new StudentDetails();
            studentDetails.setResponse(new ArrayList<>());
            studentDetails.setEvaluation(new ArrayList<>());

            for (int j = 0; j < ques; j++) {
                studentDetails.getResponse().add(new ArrayList<>());
                studentDetails.getEvaluation().add(new ArrayList<>());
            }

            testDetails.getStudentDetails().add(studentDetails);
        }
    }
}
