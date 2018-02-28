package sample.Controllers;

import HelperClasses.DialogPopUp;
import HelperClasses.ListHelper;
import HelperClasses.Messages;
import HelperClasses.RadioButtonHelper;
import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private JFXListView<VBox> student_listView;

    @FXML
    private StackPane stackPane;

    @FXML
    private BorderPane top_borderPane;


    @FXML
    private JFXTextField subQuestionTxt;

    TestDetails testDetails;
    ToggleGroup[] toggleGroups;
    ArrayList<StudentDetails> studentDetails;
    int numberOfQuestion;

    int studentIndexCount = 0;
    int testIndex;
    int sub_ques;
    boolean dataSaved = true;
    int oldSelectedIndex = -1;

/*
    public StudentDetailsController() {
        *//*FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/studentDetails.fxml"));
//        loader.setRoot(this);
//        loader.setController(this);
        try {
            loader.load();
        } catch (IOException exc) {
            exc.printStackTrace();
            // this is pretty much fatal, so:
            System.exit(1);
        }*//*
    }*/


    /*public void initialize() {
     *//*RadioButtonHelper.gridConstraints(keymap_gridpane, 85);
        ques_num_txt.setEditable(true);
        keymap_save_button.setDisable(true);

        ques_num_txt.setOnAction(event -> {
            int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
            if (testDetails.getSubQuestionList().get(selectedIndex) != null)
                sub_ques_txt.setText(String.valueOf(testDetails.getSubQuestionList().get(selectedIndex)));
        })
        FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setGlyphName("FOLDER");
        icon.setStyle("-fx-background-color: #FFF");
        Image image = new Image(new FileInputStream("src//icons//error.png"));
        NumberValidator validator = new NumberValidator();
        validator.setMessage("Valid number");
        validator.setIcon(new ImageView(image));
        subQuestionTxt.getValidators().add(validator);*//*
        List question = testDetails.getQuestion();
        //initialize();
        ques_num_txt.getItems().addAll(question);

        // List question = testDetails.getQuestion();
        numberOfQuestion = question.size();
        //ques_num_txt.getItems().addAll(question);

        if (testDetails.getSubQuestionList() != null) {
            if (testDetails.getSubQuestionList().get(0)!=null)
                subQuestionTxt.setText(""+testDetails.getSubQuestionList().get(0));
        }

        loadStudentDetails(0);

        init_StudentList();
    }*/
    @FXML
    void closeEvent(MouseEvent event) {

        if (!dataSaved) {
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Text("Save"));

            Label label = new Label(Messages.ASK_FOR_SAVING_DATA);
            label.setFont(new Font("Segoi UI", 20));

            layout.setBody(label);

            JFXButton cancel = new JFXButton(Messages.CANCEL_TEXT);
            JFXButton savebtn = new JFXButton(Messages.SAVE_TXT);
            savebtn.setPrefWidth(100);
            cancel.setPrefWidth(100);
            cancel.getStyleClass().add("btn-dialog");
            savebtn.getStyleClass().add("btn-dialog");
            layout.setActions(cancel, savebtn);

            JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
            cancel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    dialog.close();
                    System.exit(0);
                }
            });

            savebtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    saveRadioData(event);
                    System.exit(0);
                }
            });
            dialog.show();
        } else {
            System.exit(0);
        }


    }

    public void init(TestDetails testDetails) {
        List question = testDetails.getQuestion();
        //initialize();
        ques_num_txt.getItems().addAll(question);
        this.testDetails = testDetails;
        // List question = testDetails.getQuestion();
        numberOfQuestion = question.size();
        //ques_num_txt.getItems().addAll(question);

        if (testDetails.getSubQuestionList() != null) {
            if (testDetails.getSubQuestionList().get(0) != null)
                subQuestionTxt.setText("" + testDetails.getSubQuestionList().get(0));
        }

        loadStudentDetails(0);

        init_StudentList();

    }

    private void loadStudentDetails(int index) {
//        ques_num_txt.getSelectionModel().selectFirst();
//        generateRadioButton(new ActionEvent());
        displayBasicStudentInfo(index);
    }

    public void initialize() {
        System.out.println("Hello world");
        //ques_num_txt = new JFXComboBox<>();

        //Getting data from bus
        testDetails = Bus.getInstance();

        RadioButtonHelper.gridConstraints(keymap_gridpane, 85);
        keymap_save_button.setDisable(true);
        ques_num_txt.getSelectionModel().selectFirst();
        NumberValidator numberValidator = new NumberValidator();
        RequiredFieldValidator validator1 = new RequiredFieldValidator();
        RequiredFieldValidator validator2 = new RequiredFieldValidator();
        Image image = null;
        try {
            image = new Image(new FileInputStream("src//icons//error.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ImageView imageView = new ImageView(image);
        numberValidator.setMessage(Validation.NUMBER_EXPECTED);
        validator1.setMessage(Validation.EMPTY_FEILD);
        validator2.setMessage(Validation.EMPTY_FEILD);
        numberValidator.setIcon(imageView);
        validator1.setIcon(new ImageView(image));
        validator2.setIcon(new ImageView(image));

        subQuestionTxt.getValidators().add(numberValidator);
        studentNameTxt.getValidators().add(validator1);
        rollNumberTxt.getValidators().add(validator2);

        List question = testDetails.getQuestion();
        //initialize();
        ques_num_txt.getItems().addAll(question);

        // List question = testDetails.getQuestion();
        numberOfQuestion = question.size();
        //ques_num_txt.getItems().addAll(question);

        if (testDetails.getSubQuestionList() != null) {
            if (testDetails.getSubQuestionList().get(0) != null)
                subQuestionTxt.setText("" + testDetails.getSubQuestionList().get(0));
        }

        loadStudentDetails(0);

        init_StudentList();


        //Initialize evaluation and response

        /*if (testDetails.getStudentDetails().get(studentIndexCount).getResponse() == null) {
            testDetails.getStudentDetails().get(studentIndexCount).
            for (int i=0;i<testDetails.getNumberOfQuestion();i++) {
                testDetails.getStudentDetails().get(StudentDetails)
            }
        }*/

    }

    private void init_StudentList() {

        student_listView.getItems().clear();
        if (testDetails != null && testDetails.getStudentDetails() != null) {
            for (int i = 0; i < testDetails.getStudentDetails().size(); i++) {
                StudentDetails studentDetails = testDetails.getStudentDetails().get(i);
                if (studentDetails != null && studentDetails.getRollNo() != null && studentDetails.getName() != null) {
                    try {

                        Label name = new Label(studentDetails.getName());
                        name.setGraphic(new ImageView(new Image(new FileInputStream("src//icons//student.png"))));
                        Label rollNumber = new Label(studentDetails.getRollNo());
                        rollNumber.setAlignment(Pos.BASELINE_RIGHT);
                        rollNumber.setMaxWidth(Double.MAX_VALUE);
                        VBox vBox = new VBox(name, rollNumber);
                        vBox.setSpacing(20);
                        student_listView.getItems().add(vBox);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    student_listView.setExpanded(true);
                    student_listView.depthProperty().set(3);

                }
            }
        }
    }

    // Proceed button code
    @FXML
    void generateRadioButton(ActionEvent event) {

        /*if (!dataSaved) {
            if (oldSelectedIndex!=-1)
            saveDataOperations(oldSelectedIndex);
        }*/
        if (subQuestionTxt.validate() && studentNameTxt.validate() && rollNumberTxt.validate()) {
            int selectedIndex = 0;
            if (ques_num_txt.getSelectionModel().getSelectedIndex() != -1) {
                selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
            }
            oldSelectedIndex = selectedIndex;
            testDetails.getSubQuestionList().set(selectedIndex, sub_ques);
            sub_ques = Integer.parseInt(subQuestionTxt.getText());
            testDetails.getSubQuestionList().set(selectedIndex, sub_ques);
            setRadioButton(sub_ques, selectedIndex);

            dataSaved = false;
            keymap_save_button.setDisable(false);

            //Initialize evaluation and response

            if (testDetails.getStudentDetails().get(studentIndexCount).getResponse() == null) {

            }


        }
    }


    private void setRadioButton(int sub_ques, int selectedIndex) {

        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        keymap_gridpane.getChildren().clear();

        try {
            toggleGroups = new ToggleGroup[sub_ques];
            if (testDetails.getStudentDetails().get(studentIndexCount).getResponse() != null) {
                toggleGroups = RadioButtonHelper.generateRadioButton(keymap_gridpane, sub_ques, toggleGroups, testDetails.getStudentDetails().get(studentIndexCount).getResponse().get(selectedIndex));

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    @FXML
    void generateReport(ActionEvent event) {
        checkDataSave();
    }

    @FXML
    void saveRadioData(ActionEvent event) {

        String questionNumber = (String) ques_num_txt.getSelectionModel().getSelectedItem();
        int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
        /*if (questionNumber == null) {
//            questionNumber = (String) ques_num_txt.getItems().get(0);
//            selectedIndex = 0;
//        }*/
        String studentName = studentNameTxt.getText();
        String rollno = rollNumberTxt.getText();

        // Saving data
        testDetails.getStudentDetails().get(studentIndexCount).setName(studentName);
        testDetails.getStudentDetails().get(studentIndexCount).setRollNo(rollno);
        saveDataOperations(selectedIndex);

        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
        snackbar.show(Messages.SAVE_SUCCESSFULL, Messages.TOAST_DURATION);


        keymap_save_button.setDisable(true);
        init_StudentList();
    }


    private void saveDataOperations(int selectedIndex) {
        ArrayList<Integer> selectedToggles = RadioButtonHelper.getSelectedToggles(toggleGroups);
        if (selectedToggles != null) {
            ArrayList<Character> response = RadioButtonHelper.getSelectedRadio(toggleGroups, selectedToggles);
            ArrayList<Integer> evaluation;
            ArrayList<Character> key = testDetails.getKey().get(selectedIndex);
            testDetails.getStudentDetails().get(studentIndexCount).getResponse().set(selectedIndex, response);
            //Evaluation will be done inside KeyToStudents
            if (testDetails.getSubQuestionList().get(selectedIndex) != null) {
                testDetails.setStudentDetails(ListHelper.keyToStudents(key, testDetails.getStudentDetails(), selectedIndex, testDetails.getSubQuestionList().get(selectedIndex)));
            }
            DataBaseCommunication.convertJavaToJSON(testDetails);

            RadioButtonHelper.deselectRadioButton(keymap_gridpane);
            keymap_gridpane.getChildren().clear();

            dataSaved = true;

//            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
//            snackbar.show(Messages.SAVE_SUCCESSFULL, Messages.TOAST_DURATION);

            init_StudentList();

        }

    }


    @FXML
    void nextStudent(ActionEvent event) {

        checkDataSave();
        if (studentIndexCount < testDetails.getNumberOfStudent() - 1) {
            studentIndexCount++;
            displayBasicStudentInfo(studentIndexCount);
        } else {
            JFXSnackbar snackbar = new JFXSnackbar(stackPane);
            snackbar.show("No more students are available", Messages.TOAST_DURATION);
            //studentIndexCount = 0;
        }


        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        keymap_gridpane.getChildren().clear();
    }

    private void displayBasicStudentInfo(int index) {

        studentNameTxt.setText("");
        rollNumberTxt.setText("");
        subQuestionTxt.setText("");

        if (testDetails.getStudentDetails().get(index).getName() != null) {
            studentNameTxt.setText(testDetails.getStudentDetails().get(index).getName());
        }
        if (testDetails.getStudentDetails().get(index).getRollNo() != null) {
            rollNumberTxt.setText(testDetails.getStudentDetails().get(index).getRollNo());
        }
        if (testDetails.getSubQuestionList() != null && testDetails.getSubQuestionList().get(0) != null) {
            subQuestionTxt.setText("" + testDetails.getSubQuestionList().get(0));
            setRadioButton(testDetails.getSubQuestionList().get(0), 0);
            //generateRadioButton(new ActionEvent());
        }

        ques_num_txt.getSelectionModel().selectFirst();


    }

    @FXML
    void questionChanged(ActionEvent event) {

        int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();

        if (testDetails.getSubQuestionList().get(selectedIndex) != null) {
            subQuestionTxt.setText(String.valueOf(testDetails.getSubQuestionList().get(selectedIndex)));
        } else {
            subQuestionTxt.setText("");
        }

    }

    @FXML
    void openKeyEvent(MouseEvent event) {


        try {
            checkDataSave();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("scenes/KeyMap.fxml"));
            Parent parent = loader.load();
            KeyMapController controller = loader.getController();
            controller.init(testDetails);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void openTestEvent(MouseEvent event) {

    }

    @FXML
    void getStudentDetails(MouseEvent event) {
        checkDataSave();
        int i = student_listView.getSelectionModel().getSelectedIndex();
        if (i >= 0) {
            studentIndexCount = i;
            loadStudentDetails(i);
        }
    }

    @FXML
    void showBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, testDetails);
    }


    private void checkDataSave() {
        if (!dataSaved) {
            System.out.println("Something not saved");
            saveRadioData(new ActionEvent());
        }
    }


}

