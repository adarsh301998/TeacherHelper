package sample.Controllers;

import HelperClasses.DialogPopUp;
import HelperClasses.Messages;
import HelperClasses.ReaderHelper;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSnackbar;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

//import sample.DataClasses.DataInstance;

public class MainWindowController {
    /*@FXML
    private FontAwesomeIcon btn_close;*/

    @FXML
    private JFXButton btn_new;

    @FXML
    private JFXButton btn_open;

//    @FXML
//    private JFXButton btn_key;
//
//    @FXML
//    private JFXButton btn_report;

    @FXML
    private AnchorPane rootScene;

    @FXML
    private StackPane stackPane;


    @FXML
    public void initialize() {

        //Create test for test

        //TestDetails testDetails = createTest();
        //Bus.setInstance(testDetails);
        //DataBaseCommunication.convertJavaToJSON(testDetails);

    }

    private TestDetails createTest() {

        TestDetails testDetails = new TestDetails();

        testDetails.setTestName("TestCase1");
        testDetails.setNumberOfQuestion(1);
        testDetails.setSubQuestionList(new ArrayList<>());
        testDetails.getSubQuestionList().add(25);
        testDetails.setNumberOfStudent(16);
        testDetails.setTestName("TestCase1");
        testDetails.setInstitute("Birla Institute of technology");
        testDetails.setDateTime("2018-12-13");
        testDetails.setClassLabel("EEEVI");
        testDetails.setTeacherName("Adarsh");
        testDetails.setQuestion(new ArrayList<>());
        testDetails.getQuestion().add("1");

        ArrayList<ArrayList<Character>> key = new ArrayList<>();
        key.add(readKey());
        testDetails.setKey(key);

        ArrayList<StudentDetails> students = new ArrayList<>();

        ArrayList<ArrayList<Character>> studentresponse = readStudentResponse();
        System.out.println(studentresponse.toString());
        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {

            int temp = i;
            String s = String.valueOf(temp + 1);
            StudentDetails studentDetails = new StudentDetails();
            studentDetails.setName(s);
            studentDetails.setRollNo(s);
            //Setting up response
            ArrayList<ArrayList<Character>> r = new ArrayList<>();
            ArrayList<Character> r2 = studentresponse.get(i);
            r.add(r2);

            studentDetails.setResponse(r);

            //Setting up evaluation

            ArrayList<ArrayList<Integer>> k = new ArrayList<>();
            ArrayList<Integer> k1 = new ArrayList<>();
            for (int j = 0; j < r2.size(); j++) {
                if (r2.get(j) == key.get(0).get(j)) {
                    k1.add(1);
                } else {
                    k1.add(0);
                }
            }
            k.add(k1);

            studentDetails.setEvaluation(k);

            students.add(studentDetails);


        }
        testDetails.setStudentDetails(students);


        return testDetails;
    }

    private ArrayList<Character> convertStringToArrayList(String s) {

        ArrayList<Character> list = new ArrayList<>();

        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }

        return list;
    }

    private ArrayList<Character> readKey() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src//Tests//key.txt"));
            String data = reader.readLine();
            ArrayList<Character> key = convertStringToArrayList(data);
            return key;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    private ArrayList<ArrayList<Character>> readStudentResponse() {

        try {
            // Reading student response
            BufferedReader reader = new BufferedReader(new FileReader("src//Tests//EEEVI.txt"));
            String data, line;
            ArrayList<ArrayList<Character>> studentResponse = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                ArrayList<Character> list = convertStringToArrayList(line);

                studentResponse.add(list);
            }
            return studentResponse;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }



    @FXML
    void closeEvent(MouseEvent event) {
        DialogPopUp.closeAlert(stackPane);
    }

    @FXML
    void handelEvent(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        Parent new_win;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (event.getSource() == btn_new) {
            loader.setLocation(getClass().getResource("scenes/createNewTest.fxml"));
            new_win = loader.load();
            stage.setScene(new Scene(new_win));
        }
        if (event.getSource() == btn_open) {
            if (!ReaderHelper.testPresent()) {
                JFXSnackbar snackbar = new JFXSnackbar();
                snackbar.show("Nothing to open", Messages.TOAST_DURATION);
            } else {

                loader.setLocation(getClass().getResource("scenes/openTest.fxml"));
                new_win = loader.load();
                stage.setScene(new Scene(new_win));
            }
        }



    }
}
