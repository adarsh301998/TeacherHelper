package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends Application {

    TestDetails testDetails;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("Controllers/scenes/mainWindow.fxml"));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Controllers/scenes/mainWindow.fxml"));
        Parent root = loader.load();
//        KeyMapController controller = loader.getController();

        testDetails = new TestDetails();
        testDetails.setTeacherName("xyz");
        testDetails.setClassLabel("bca");
        testDetails.setTestName("English");
        testDetails.setInstitute("Birla Institute of technology");
        testDetails.setDateTime(LocalDate.now());
        testDetails.setNumberOfStudent(2);
        testDetails.setNumberOfQuestion(3);
        initialize_arrayList(3, 2);
        //controller.init_create(testDetails);

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
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
            StudentDetails studentDetails = new StudentDetails();
            studentDetails.setName("Adarsh");
            studentDetails.setRollNo("Bca");

            ArrayList<ArrayList<Character>> mr = new ArrayList<>();
            ArrayList<ArrayList<Integer>> me = new ArrayList<>();
            for (int j = 0; j < 3; j++) {


                ArrayList<Character> response = new ArrayList<>();
                ArrayList<Integer> eva = new ArrayList<>();
                response.add(null);
                response.add('A');
                response.add('A');
                eva.add(1);
                eva.add(1);
                eva.add(1);
                mr.add(response);
                me.add(eva);
            }
            studentDetails.setResponse(mr);
            studentDetails.setEvaluation(me);
            testDetails.getStudentDetails().add(studentDetails);
        }
    }
}
