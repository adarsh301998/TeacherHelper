package sample.Controllers;

import HelperClasses.DialogPopUp;
import HelperClasses.ListHelper;
import HelperClasses.Messages;
import HelperClasses.RadioButtonHelper;
import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DataClasses.Bus;
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

    boolean saveData = true;

//    @FXML
//    private AnchorPane keymap_anchorPane;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private JFXButton student_response_btn;

    @FXML
    private GridPane keymap_gridpane;

    @FXML
    private StackPane stackPane;

    @FXML
    private JFXButton keymap_save_button;

    int subQuestions;
    String questionLabel;
    ToggleGroup[] toggleGroups;
    int testIndex;
    TestDetails testDetails;
    int oldSelectedIndex = -1;


    public void initialize() throws FileNotFoundException {
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
    }


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

    @FXML
    void closeEvent(MouseEvent event) {
        if (!saveData) {
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

    @FXML
    void openEvent(MouseEvent event) {
        checkSave();
    }
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
        snackbar.show(Messages.SAVE_SUCCESSFULL, Messages.TOAST_DURATION);

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

    @FXML
    public void openStudentResponse() {
        studentResponseEvent(new ActionEvent());
    }

}



