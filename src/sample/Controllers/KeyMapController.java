package sample.Controllers;

import HelperClasses.Constants;
import HelperClasses.DialogPopUp;
import HelperClasses.ListHelper;
import HelperClasses.RadioButtonHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.BaseClasses.MainBaseController;
import sample.DataClasses.Bus;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.TestDetails;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import sample.DataClasses.DataInstance;

public class KeyMapController extends MainBaseController {
    @FXML
    private JFXComboBox<?> ques_num_txt;

    @FXML
    private JFXTextField sub_ques_txt;

    @FXML
    private JFXButton proceed_btn;


    boolean saveData = true;


    /*@FXML
    ScrollPane scrollPane;

    @FXML
    private JFXButton student_response_btn;
*/
    @FXML
    private GridPane keymap_gridpane;


    @FXML
    private JFXButton keymap_save_button;



    /*@FXML
    private JFXButton test_student;*/


    int subQuestions;
    String questionLabel;
    ToggleGroup[] toggleGroups;
    int testIndex;
    TestDetails testDetails;
    int oldSelectedIndex = -1;


    public void initialize() throws FileNotFoundException {

        //To set anchorPaneSize to maximum
        checkMaximize();
        setTollTip();

        final Tooltip openStudent_btn_tooltip = new Tooltip();
        openStudent_btn_tooltip.setText("Students");

        openStudents_btn.setTooltip(openStudent_btn_tooltip);


        final Tooltip openTest_btn_toltip = new Tooltip();
        openTest_btn_toltip.setText("Open Test");
        openTest_btn.setTooltip(openTest_btn_toltip);

        final Tooltip newTest_btn_tooltip = new Tooltip();
        newTest_btn_tooltip.setText("New Test");
        newTest_btn.setTooltip(newTest_btn_tooltip);


        final Tooltip openReport_btn_tooltip = new Tooltip();
        openReport_btn_tooltip.setText("Open report");
        openReport_btn.setTooltip(openReport_btn_tooltip);

        RadioButtonHelper.gridConstraints(keymap_gridpane, 85);
        ques_num_txt.setEditable(true);
        keymap_save_button.setDisable(true);


        /*ques_num_txt.setOnAction(event -> {
            int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
            if (testDetails.getSubQuestionList().get(selectedIndex) != null)
                sub_ques_txt.setText(String.valueOf(testDetails.getSubQuestionList().get(selectedIndex)));
        });*/
        /*FontAwesomeIcon icon = new FontAwesomeIcon();
        icon.setGlyphName("FOLDER");
        icon.setStyle("-fx-background-color: #FFF");*/

        // Setting up various validation on feilds
        Image image = new Image(new FileInputStream("src//icons//error.png"));
        NumberValidator validator = new NumberValidator();
        validator.setMessage("Valid number");
        validator.setIcon(new ImageView(image));
        sub_ques_txt.getValidators().add(validator);


        //Doing basic initialization activity
        testDetails = Bus.getInstance();
        List ques_list = testDetails.getQuestion();
        ques_num_txt.getItems().addAll(ques_list);
        ques_num_txt.getSelectionModel().selectFirst();
        if (testDetails.getSubQuestionList().get(0) != null) {
            sub_ques_txt.setText(String.valueOf(testDetails.getSubQuestionList().get(0)));
            generateRadioButtonOperation(0);
        }

        //Restrict user to enter in radio button without validation
        keymap_gridpane.setDisable(true);
    }

    //Prooced button
    @FXML
    void generateRadioButton(ActionEvent event) {


        /*if (!saveData) {
            if (oldSelectedIndex!=-1)
                saveDataOperations(oldSelectedIndex);
        }F
*/
        if (event.getSource().equals(proceed_btn) && sub_ques_txt.validate()) {

            questionLabel = String.valueOf(ques_num_txt.getSelectionModel().getSelectedItem());
            if (ques_num_txt.getSelectionModel().getSelectedIndex() == -1) {
                ques_num_txt.getSelectionModel().selectFirst();
            }
            int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();
            oldSelectedIndex = selectedIndex;
            /*
                questionLabel = (String) ques_num_txt.getItems().get(0);
                selectedIndex = 0;
            }*/
            subQuestions = Integer.parseInt(sub_ques_txt.getText());

            testDetails.getSubQuestionList().set(selectedIndex, subQuestions);

            generateRadioButtonOperation(selectedIndex);
            //Allow user to edit user response
            keymap_gridpane.setDisable(false);
        }
    }

    private void generateRadioButtonOperation(int selectedIndex) {
        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        keymap_gridpane.getChildren().clear();
        int subQuestions = testDetails.getSubQuestionList().get(selectedIndex);
        toggleGroups = new ToggleGroup[subQuestions];
        try {
            toggleGroups = RadioButtonHelper.generateRadioButton(keymap_gridpane, subQuestions, toggleGroups, testDetails.getKey().get(selectedIndex));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        saveData = false;
        keymap_save_button.setDisable(false);

    }

    @FXML
    void studentResponseEvent(ActionEvent event) {

        checkSave();
            try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("scenes/studentDetails.fxml"));

                Parent parent = loader.load();
//                StudentDetailsController controller = new StudentDetailsController();
//                controller.init(testDetails);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(parent);
                stage.setScene(scene);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    /*    @FXML
        void closeEvent(MouseEvent event) {
            if (!saveData) {
                JFXDialogLayout layout = new JFXDialogLayout();
                layout.setHeading(new Text("Save"));

                Label label = new Label(Constants.ASK_FOR_SAVING_DATA);
                label.setFont(new Font("Segoi UI", 20));

                layout.setBody(label);

                JFXButton cancel = new JFXButton(Constants.CANCEL_TEXT);
                JFXButton savebtn = new JFXButton(Constants.SAVE_TXT);
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
                DialogPopUp.closeAlert(stackPane);
            }
        }*/
/*
    @FXML
    void openEvent(MouseEvent event) {
        checkSave();
    }*/
    @FXML
    void saveRadioData(ActionEvent event) {
        //getting selected toggle

        int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();

        saveDataOperations(selectedIndex);
    }

    private void saveDataOperations(int selectedIndex) {
        ArrayList<Integer> selectedToggles = RadioButtonHelper.getSelectedToggles(toggleGroups);
        ArrayList<Character> key = RadioButtonHelper.getSelectedRadio(toggleGroups, selectedToggles);
        questionLabel = ques_num_txt.getSelectionModel().getSelectedItem().toString();

        testDetails.getQuestion().set(selectedIndex, questionLabel);
        testDetails.getKey().set(selectedIndex, key);

        RadioButtonHelper.deselectRadioButton(keymap_gridpane);
        testDetails.setStudentDetails(ListHelper.keyToStudents(key, testDetails.getStudentDetails(), selectedIndex, testDetails.getSubQuestionList().get(selectedIndex)));
        DataBaseCommunication.convertJavaToJSON(testDetails);
        keymap_gridpane.getChildren().clear();

        saveData = true;
        keymap_save_button.setDisable(true);


        // Updating question list combo Box
        List list = testDetails.getQuestion();
        ques_num_txt.getItems().clear();
        ques_num_txt.getItems().addAll(list);
        ques_num_txt.getSelectionModel().select(selectedIndex);

        JFXSnackbar snackbar = new JFXSnackbar(stackPane);
        snackbar.show(Constants.SAVE_SUCCESSFULL, Constants.TOAST_DURATION);

    }


    public void init(TestDetails packet) {
        testDetails = packet;
        List ques_list = testDetails.getQuestion();

   /*     for (int i = 1; i <= testDetails.getNumberOfQuestion(); i++) {
            ques_list.add(i);
            int j = i - 1;
            testDetails.getQuestion().set(j, String.valueOf(i));
        }*/
        ques_num_txt.getItems().addAll(ques_list);
        ques_num_txt.getSelectionModel().selectFirst();
        if (testDetails.getSubQuestionList().get(0) != null) {
            sub_ques_txt.setText(String.valueOf(testDetails.getSubQuestionList().get(0)));
            generateRadioButtonOperation(0);
        }
    }

    @FXML
    public void showBasicInfo(MouseEvent event) {
        DialogPopUp.basicInfoDialog(stackPane, testDetails);
    }

    private void checkSave() {

        if (!saveData) {
            saveRadioData(new ActionEvent());
        }


    }

    @FXML
    public void questonChanged(ActionEvent event) {
        int selectedIndex = ques_num_txt.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1 && testDetails.getSubQuestionList().get(selectedIndex) != null) {
            sub_ques_txt.setText(String.valueOf(testDetails.getSubQuestionList().get(selectedIndex)));
        } else {
            sub_ques_txt.setText("");
        }

    }

  /*  @FXML
    public void openStudentResponse(MouseEvent event) {
        studentResponseEvent(new ActionEvent());
    }*/

    @FXML
    public void openEvent(ActionEvent event) {

        /*FXMLLoader loader = new FXMLLoader();
        Parent root;
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        if(event.getSource() == openStudents_btn) {
            //open key
            loader.setLocation(getClass().getResource("scenes/studentDetails.fxml"));
            //stage.setX(40);
        }
        if (event.getSource() == openTest_btn) {
            //Open test
            loader.setLocation(getClass().getResource("scenes/openTest.fxml"));
        }
        if (event.getSource() == openReport_btn) {
            //Open report
        }

        try {
            root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

    @FXML
    void openStudentEvent(ActionEvent event) {
        try {
            checkSave();
            Parent root = FXMLLoader.load(getClass().getResource("scenes/studentDetails.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openTestEvent(ActionEvent event) {
        try {
            checkSave();
            Parent root = FXMLLoader.load(getClass().getResource("scenes/openTest.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void newTestFile(ActionEvent event) {
        try {
            checkSave();
            Parent root = FXMLLoader.load(getClass().getResource("scenes/createNewTest.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void openTestReport(ActionEvent event) {
        try {
            checkSave();
            Parent root = FXMLLoader.load(getClass().getResource("scenes/reportOptions.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



   /* @FXML
    void testEvent(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        Parent root;
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        loader.setLocation(getClass().getResource("scenes/studentDetails.fxml"));
        try {
            root = loader.load();

            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
}



