package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import sample.DataClasses.DataBaseCommunication;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;
import sample.FolderHelpers.FolderHelper;

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

    public double screenMinimunHeight = 600.0;

    String teacherName = "Adarsh";
    String classLabel = "Class";
    String instituteName = "Samarpan";
    String testDate = "05-05-2018";
    String testName = "Physics";
    int numberOfStudent = 27;
    int numberOfQuestion = 1;
    int numberOfSubQuestion = 30;

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Creating neccesary folder
        FolderHelper.createFolder();
        //Parent root = FXMLLoader.load(getClass().getResource("Controllers/scenes/mainWindow.fxml"));

        //Creating test from txt file
        /*testDetails = new TestDetails();
        testDetails.setTeacherName(teacherName);
        testDetails.setClassLabel(classLabel);
        testDetails.setTestName(testName);
        testDetails.setInstitute(instituteName);
        testDetails.setDateTime(testDate);
        testDetails.setNumberOfStudent(numberOfStudent);
        testDetails.setNumberOfQuestion(numberOfQuestion);
        initialize_arrayList(numberOfQuestion, numberOfStudent);

        Bus.setInstance(testDetails);
        DataBaseCommunication.convertJavaToJSON(testDetails);*/

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
                stage.setHeight(screenMinimunHeight);
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
        char options[] = {'A', 'B', 'C', 'D', 'E'};
        int blankIndex = 4;
        for (int i = 0; i < ques; i++) {
            //Enter subquestion
            testDetails.getQuestion().add(String.valueOf(i + 1));
            testDetails.getSubQuestionList().add(numberOfSubQuestion);

            ArrayList<String> keyDataList = DataBaseCommunication.readStringLineFromFile("smarpam_Key");
            ArrayList<Character> key = new ArrayList<>();

            //Splitting keyDataList by space

            for (String keyData : keyDataList) {
                String keyDataArray[] = keyData.split(" ");
                for (int x = 0; x < keyDataArray.length; x++) {
                    int index = Integer.valueOf(keyDataArray[x]);
                    index--;
                    key.add(options[index]);
                }
            }

            /*key.add('A');
            key.add('A');
            key.add('A');*/

            testDetails.getKey().add(key);
        }


        /*
         * Adding student studentDetails
         * */
        ArrayList<String> studentDataList = DataBaseCommunication.readStringLineFromFile("samarpam");

        // starting form 1 to ignore 1st row
        for (int i = 1; i < studentDataList.size(); i++) {
            //Splitting by tab
            // [RollNumber, Name, response]
            StudentDetails studentDetails = new StudentDetails();
            ArrayList<ArrayList<Character>> mr = new ArrayList<>();
            ArrayList<ArrayList<Integer>> me = new ArrayList<>();
            String studentData[] = studentDataList.get(i).split("\t");

            studentDetails.setRollNo(studentData[0]);
            studentDetails.setName(studentData[1]);

            //Number of question one
            ArrayList<Character> response = new ArrayList<>();
            ArrayList<Integer> evaluation = new ArrayList<>();
            ArrayList<Character> key = testDetails.getKey().get(0);
            //first 2 column ignored
            for (int x = 2; x < studentData.length; x++) {
                String data = studentData[x];
                int index;
                if (data.equals("") || data.equals("-"))
                    index = blankIndex;
                else {
                    index = Integer.valueOf(data);
                    index--;
                }

                response.add(options[index]);
            }

            //Evaluating response
            for (int z = 0; z < response.size(); z++) {
                if (response.get(z) == key.get(z)) {
                    evaluation.add(1);
                } else {
                    evaluation.add(0);
                }
            }
            mr.add(response);
            me.add(evaluation);


            /*for (int j = 0; j < 3; j++) {
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
            }*/
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
