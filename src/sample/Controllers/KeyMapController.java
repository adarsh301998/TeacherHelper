package sample.Controllers;

import HelperClasses.ListHelper;
import HelperClasses.RadioButtonHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.TestDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import sample.DataClasses.DataInstance;

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
    ScrollPane scrollPane;

    @FXML
    private JFXButton student_response_btn;

    @FXML
    private GridPane keymap_gridpane;

    int subQuestions;
    String questionLabel;
    ToggleGroup[] toggleGroups;
    int testIndex;
    TestDetails testDetails;


    public void initialize() throws FileNotFoundException {
        ques_num_txt.setEditable(true);


        ques_num_txt.setOnAction(event -> {
            int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
            if (testDetails.getSubQuestionList().get(selectedIndex) != null)
                sub_ques_txt.setText(String.valueOf(testDetails.getSubQuestionList().get(selectedIndex)));
        });
        /*FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setGlyphName("FOLDER");
        icon.setStyle("-fx-background-color: #FFF");*/
        Image image = new Image(new FileInputStream("src//icons//error.png"));
        NumberValidator validator = new NumberValidator();
        validator.setMessage("Valid number");
        validator.setIcon(new ImageView(image));
        sub_ques_txt.getValidators().add(validator);

        /*sub_ques_txt.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    if (!sub_ques_txt.validate()){
                        proceed_btn.setDisable(true);
                    }else {
                        proceed_btn.setDisable(false);
                    }
                }
            }
        });*/
        /*sub_ques_txt.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.println(sub_ques_txt.getText());
                String s = sub_ques_txt.getText();
                Pattern pattern = Pattern.compile("\\d+");
                Matcher matcher = pattern.matcher(s);
                sub_ques_txt.validate();
                if (matcher.find()){

                    proceed_btn.setDisable(false);
                }else {
                    //sub_ques_txt.validate();
                    proceed_btn.setDisable(true);
                }
            }
        });*/

    }


    @FXML
    void generateRadioButton(ActionEvent event) {
        if (event.getSource().equals(proceed_btn) && sub_ques_txt.validate()) {
            RadioButtonHelper.deselectRadioButton(keymap_gridpane);
            keymap_gridpane.getChildren().clear();
            questionLabel = String.valueOf(ques_num_txt.getSelectionModel().getSelectedItem());
            if (ques_num_txt.getSelectionModel().getSelectedIndex() == -1) {
                ques_num_txt.getSelectionModel().selectFirst();
            }
            int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
            /*
                questionLabel = (String) ques_num_txt.getItems().get(0);
                selectedIndex = 0;
            }*/
            subQuestions = Integer.parseInt(sub_ques_txt.getText());

            // Validation

            testDetails.getSubQuestionList().set(selectedIndex, subQuestions);
            toggleGroups = new ToggleGroup[subQuestions];
            try {
                toggleGroups = RadioButtonHelper.generateRadioButton(keymap_gridpane, subQuestions, 75, toggleGroups, testDetails.getKey().get(selectedIndex));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    void studentResponseEvent(ActionEvent event) {

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

    @FXML
    void openEvent(MouseEvent event) {

    }
    @FXML
    void saveRadioData(ActionEvent event) {
        //getting selected toggle
        ArrayList<Integer> selectedToggles = RadioButtonHelper.getSelectedToggles(toggleGroups);
        int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
        ArrayList<Character> key = new ArrayList<>();

        key = RadioButtonHelper.getSelectedRadio(toggleGroups, selectedToggles);

        //Settings question number

        testDetails.getQuestion().set(selectedIndex, questionLabel);
        testDetails.getKey().set(selectedIndex, key);

        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        testDetails.setStudentDetails(ListHelper.keyToStudents(key, testDetails.getStudentDetails(), selectedIndex));
        DataBaseCommunication.convertJavaToJSON(testDetails, testDetails.getTestName());
        keymap_gridpane.getChildren().clear();
    }

    public void init_create(TestDetails packet) {
        testDetails = packet;
        List ques_list = new ArrayList<>();
        ;
        System.out.println(testDetails.getTeacherName());
        for (int i = 1; i <= testDetails.getNumberOfQuestion(); i++) {
            ques_list.add(i);
            int j = i - 1;
            testDetails.getQuestion().set(j, String.valueOf(i));
        }
        ques_num_txt.getItems().addAll(ques_list);

        int sub_ques = 0;
        try {
            sub_ques = testDetails.getSubQuestionList().get(0);
            sub_ques_txt.setText("" + sub_ques);
        } catch (Exception e) {
            sub_ques_txt.setText("");
        }
    }

    public void init_open(TestDetails packet) {
        testDetails = packet;

        List list = testDetails.getQuestion();
        ques_num_txt.getItems().removeAll();
        ques_num_txt.getItems().addAll(list);
        sub_ques_txt.setText("" + testDetails.getSubQuestionList().get(0));

    }


}



