package sample.Controllers;

import HelperClasses.RadioButtonHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.DataInstance;
import sample.DataClasses.StudentDetails;

import java.util.ArrayList;
import java.util.List;

public class StudentDetailsController {
    @FXML
    private AnchorPane key_map_main_layout;

    @FXML
    private JFXComboBox<?> ques_num_txt;

    @FXML
    private JFXTextField studentNameTxt;

    @FXML
    private JFXButton proceed_btn;

    @FXML
    private FontAwesomeIcon keymap_close_btn;

    @FXML
    private JFXTextField rollNumberTxt;

    @FXML
    private GridPane keymap_gridpane;

    @FXML
    private JFXButton student_response_btn;

    @FXML
    private JFXButton keymap_save_button;

    @FXML
    private AnchorPane side_anchor;

    @FXML
    private JFXButton nextStudent_btn;


    @FXML
    private JFXTextField subQuestionTxt;

    ToggleGroup[] toggleGroups;
    ArrayList<StudentDetails> studentDetails;
    int numberOfQuestion;


    int studentIndexCount = 0;
    int testIndex;
    int sub_ques;

    @FXML
    void closeEvent(MouseEvent event) {
        System.exit(0);
    }

    public void initialize() {
        List question = DataInstance.getInstance().getTestDetails().get(testIndex).getQuestion();
        testIndex = DataInstance.getInstance().getTestDetails().size();//-1
        numberOfQuestion = question.size();
        ques_num_txt.getItems().addAll(question);

        int sub_ques = 0;
        try {
            if (DataInstance.getInstance().getTestDetails().get(testIndex).getSubQuestionList().get(0) != null)
                sub_ques = DataInstance.getInstance().getTestDetails().get(testIndex).getSubQuestionList().get(0);
            subQuestionTxt.setText("" + sub_ques);
        } catch (Exception e) {
            subQuestionTxt.setText("");
        }
    }

    @FXML
    void generateRadioButton(ActionEvent event) {

        sub_ques = Integer.parseInt(subQuestionTxt.getText());


//DataInstance.getInstance().getTestDetails().get(testIndex).getStudentDetails().set()
        //studentDetails.add(new StudentDetails(studentName, rollno));
        //CreateNewTestController.testDetails.setStudentDetails(new ArrayList<>());
//        studentDetails.get(selectedIndex);
//        studentDetails.setEvaluation(new ArrayList<>());
//        for (int i=0;i<numberOfQuestion;i++) {
//            studentDetails.getResponse().add(new ArrayList<>());
//            studentDetails.getResponse().add(new ArrayList<>());
//        }
        try {
            toggleGroups = new ToggleGroup[sub_ques];
            toggleGroups = RadioButtonHelper.generateRadioButton(keymap_gridpane, sub_ques, 85, toggleGroups);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void generateReport(ActionEvent event) {
        DataBaseCommunication.convertJavaToJSON(DataInstance.getInstance());
    }

    @FXML
    void saveRadioData(ActionEvent event) {

        String questionNumber = (String) ques_num_txt.getSelectionModel().getSelectedItem();
        int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
        if (questionNumber == null) {
            questionNumber = (String) ques_num_txt.getItems().get(0);
            selectedIndex = 0;
        }
        String studentName = studentNameTxt.getText();
        String rollno = rollNumberTxt.getText();

        // Validation

        // Getting selected toogle
        ArrayList<Integer> selectedToggles = RadioButtonHelper.getSelectedToggles(toggleGroups);
        ArrayList<Character> response;
        if (selectedToggles != null) {

            response = RadioButtonHelper.getSelectedRadio(toggleGroups, selectedToggles);
            ArrayList<Integer> evaluation = new ArrayList<>();
            ArrayList<Character> key = DataInstance.getInstance().getTestDetails().get(testIndex).getKey().get(selectedIndex);
            for (int i = 0; i < response.size(); i++) {
                if (response.get(i) != null) {
                    if (response.get(i) == key.get(i)) {
                        evaluation.add(1);
                    } else evaluation.add(0);
                } else {
                    evaluation.add(0);
                }
            }
            /*int responseSize = studentDetails.get(studentIndexCount).getResponse()==null?0:studentDetails.get(studentIndexCount).getResponse().size();
            if (responseSize<selectedIndex) {
                for (int i=responseSize;i<selectedIndex;i++) {
                    studentDetails.get(studentIndexCount).getResponse().add(new ArrayList<>());
                    studentDetails.get(studentIndexCount).getEvaluation().add(new ArrayList<>());
                }
                studentDetails.get(studentIndexCount).getResponse().add(response);
                studentDetails.get(studentIndexCount).getEvaluation().add(evaluation);
            }else{
                studentDetails.get(studentIndexCount).getResponse().add(selectedIndex,response);
                studentDetails.get(studentIndexCount).getEvaluation().add(evaluation);
            }*/
            System.out.println("Key " + key + " response " + response + " evaluation " + evaluation);

            // Setting up of data

            DataInstance.getInstance().getTestDetails().get(testIndex).getSubQuestionList().set(selectedIndex, sub_ques);
            DataInstance.getInstance().getTestDetails().get(testIndex).getStudentDetails().get(studentIndexCount).setName(studentName);
            DataInstance.getInstance().getTestDetails().get(testIndex).getStudentDetails().get(studentIndexCount).setRollNo(rollno);
            DataInstance.getInstance().getTestDetails().get(testIndex).getStudentDetails().get(studentIndexCount).getResponse().set(selectedIndex, response);
            DataInstance.getInstance().getTestDetails().get(testIndex).getStudentDetails().get(studentIndexCount).getEvaluation().set(selectedIndex, evaluation);

        }


    }

    @FXML
    void nextStudent(ActionEvent event) {

        studentIndexCount++;
        studentNameTxt.setText("");
        rollNumberTxt.setText("");
        subQuestionTxt.setText("" + DataInstance.getInstance().getTestDetails().get(testIndex).getSubQuestionList().get(0));
        RadioButtonHelper.deselectRadioButton(toggleGroups);
        keymap_gridpane.getChildren().removeAll();

    }


}

