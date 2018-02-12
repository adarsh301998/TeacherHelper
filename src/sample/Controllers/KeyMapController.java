package sample.Controllers;

import HelperClasses.RadioButtonHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.DataClasses.DataInstance;
import sample.DataClasses.TestDetails;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KeyMapController {
    @FXML
    private JFXComboBox<?> ques_num_txt;

    @FXML
    private JFXTextField sub_ques_txt;

    @FXML
    private JFXButton proceed_btn;

    @FXML
    private FontAwesomeIcon keymap_close_btn;

//    @FXML
//    private AnchorPane keymap_anchorPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    private GridPane keymap_gridpane;

    @FXML
    private JFXButton student_response_btn;

    @FXML
    private AnchorPane key_map_main_layout;

    @FXML
    private JFXButton keymap_save_button;

    int subQuestions;
    String questionLabel;
    ToggleGroup[] toggleGroups;
    int testIndex;
    TestDetails testDetails;

    public void initialize() {
        List ques_list = new ArrayList<>();
        testIndex = DataInstance.getInstance().getTestDetails().size() - 1; // -1
        System.out.println(testIndex);
        System.out.println(DataInstance.getInstance().toString());
        System.out.println(DataInstance.getInstance().getTestDetails().get(0).getTeacherName());
        System.out.println(DataInstance.getInstance().getTestDetails().get(testIndex).toString());
        for (int i = 1; i <= DataInstance.getInstance().getTestDetails().get(testIndex).getNumberOfQuestion(); i++) {
            ques_list.add(i);
            int j = i - 1;
            DataInstance.getInstance().getTestDetails().get(testIndex).getQuestion().set(j, String.valueOf(i));
        }
        ques_num_txt.setEditable(true);
        ques_num_txt.getItems().addAll(ques_list);

        int sub_ques = 0;
        try {
            sub_ques = DataInstance.getInstance().getTestDetails().get(testIndex).getSubQuestionList().get(0);
            sub_ques_txt.setText("" + sub_ques);
        } catch (Exception e) {
            sub_ques_txt.setText("");
        }

    }

    @FXML
    void generateRadioButton(ActionEvent event) {
        if (event.getSource().equals(proceed_btn)) {
            questionLabel = String.valueOf(ques_num_txt.getSelectionModel().getSelectedItem());
            int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
            if (questionLabel == null) {
                questionLabel = (String) ques_num_txt.getItems().get(0);
                selectedIndex = 0;
            }
            subQuestions = Integer.parseInt(sub_ques_txt.getText());

            // Validation

//             DataInstance.getInstance().getTestDetails().get(testIndex).getSubQuestionList().set(selectedIndex, subQuestions);
            toggleGroups = new ToggleGroup[subQuestions];
            try {
                toggleGroups = RadioButtonHelper.generateRadioButton(keymap_gridpane, subQuestions, 85, toggleGroups);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void studentResponseEvent(ActionEvent event) {
        // Getting radio button response
        if (event.getSource().equals(student_response_btn)) {
            try {
                Parent parent = (AnchorPane) FXMLLoader.load(getClass().getResource("scenes/studentDetails.fxml"));
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void closeEvent(MouseEvent event) {
        System.exit(0);
    }

    /*@FXML
    void createSubQuestion(ActionEvent event) {
        if (event.getSource().equals(proceed_btn)) {
            int ques_number = Integer.parseInt(String.valueOf(ques_combo.getValue()));
            int ques_sub = Integer.parseInt(sub_question_txt.getText());
            scroll_pane.setVisible(true);
            grid_pane.add(new Label("A"), 1,0);
        }

    }*/

    @FXML
    void saveRadioData(ActionEvent event) {
        //getting selected toggle
        ArrayList<Integer> selectedToggles = RadioButtonHelper.getSelectedToggles(toggleGroups);
        int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
        ArrayList<Character> key = new ArrayList<>();
        if (selectedToggles != null) {
            key = RadioButtonHelper.getSelectedRadio(toggleGroups, selectedToggles);
        }

        //Settings question number
        /*for (int i=0;i<CreateNewTestController.testDetails.getNumberOfQuestion();i++) {
                if (i<selectedIndex)
                CreateNewTestController.testDetails.getQuestion().add(null);
            }*/

        DataInstance.getInstance().getTestDetails().get(testIndex).getQuestion().set(selectedIndex, questionLabel);
        DataInstance.getInstance().getTestDetails().get(testIndex).getKey().set(selectedIndex, key);
        //System.out.println(CreateNewTestController.testDetails.getKey());
        keymap_gridpane.getChildren().removeAll();
        RadioButtonHelper.deselectRadioButton(toggleGroups);
    }

    public void init_create(TestDetails testDetails) {
        this.testDetails = testDetails;

    }
}



