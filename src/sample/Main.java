package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.util.ArrayList;

public class Main extends Application {

    TestDetails testDetails;

    public static Stage stage;

    public Screen screen = Screen.getPrimary();

    Rectangle2D bounds = screen.getVisualBounds();

    public double screenXmin = bounds.getMinX();

    public double screenYmin = bounds.getMinY();

    public double screenMaxWidth = bounds.getWidth();

    public double screenMaxHeight = bounds.getHeight();

    public double screenMinimunWidth = 1234.0;

    public double ScreenMinimunHeight = 600.0;



    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("Controllers/scenes/mainWindow.fxml"));

        /*testDetails = new TestDetails();
        testDetails.setTeacherName("xyz");
        testDetails.setClassLabel("bca");
        testDetails.setTestName("English");
        testDetails.setInstitute("Birla Institute of technology");
        testDetails.setDateTime(LocalDate.now());
        testDetails.setNumberOfStudent(2);
        testDetails.setNumberOfQuestion(3);
        initialize_arrayList(3, 2);

        Bus.setInstance(testDetails);
*/

        stage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Controllers/scenes/mainWindow.fxml"));
        Parent root = loader.load();
        //KeyMapController controller = loader.getController();
        // StudentDetailsController controller = loader.getController();
        //controller.init_create(testDetails);
        //controller.init(testDetails);


        // primaryStage.initStyle(StageStyle.TRANSPARENT);

        screen = Screen.getPrimary();

        bounds = screen.getVisualBounds();

        screenXmin = bounds.getMinX();

        screenYmin = bounds.getMinY();

        screenMaxWidth = bounds.getWidth();

        screenMaxHeight = bounds.getHeight();

        stage.maximizedProperty().addListener((ov, minimumSize, maximumSize) -> {

            if (maximumSize) {
                stage.setX(screenXmin);
                stage.setX(screenYmin);
                stage.setWidth(screenMaxWidth);
                stage.setHeight(screenMaxHeight);

            }
            if (minimumSize) {
                stage.setX(60);
                stage.setY(60);
                stage.setWidth(screenMinimunWidth);
                stage.setHeight(ScreenMinimunHeight);
            }
        });


        stage.setScene(new Scene(root));
        stage.show();
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
            testDetails.getQuestion().add(String.valueOf(i + 1));
            testDetails.getSubQuestionList().add(3);

            ArrayList<Character> key = new ArrayList<>();
            key.add('A');
            key.add('A');
            key.add('A');
            testDetails.getKey().add(key);
        }

        for (int i = 0; i < num_students; i++) {
            StudentDetails studentDetails = new StudentDetails();
            /*studentDetails.setName("Adarsh");
            studentDetails.setRollNo("Bca");*/

            ArrayList<ArrayList<Character>> mr = new ArrayList<>();
            ArrayList<ArrayList<Integer>> me = new ArrayList<>();
            for (int j = 0; j < 3; j++) {
                ArrayList<Character> response = new ArrayList<>();
                ArrayList<Integer> eva = new ArrayList<>();
                response.add(null);
                response.add(null);
                response.add(null);
                eva.add(null);
                eva.add(null);
                eva.add(null);
                mr.add(response);
                me.add(eva);
            }
            studentDetails.setResponse(mr);
            studentDetails.setEvaluation(me);
            testDetails.getStudentDetails().add(studentDetails);
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.out.println("Hello");

    }
}
