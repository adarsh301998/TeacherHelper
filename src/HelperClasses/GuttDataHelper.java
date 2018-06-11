package HelperClasses;

import calculations.BasicStudentReportCalculation;

import java.util.ArrayList;
import java.util.List;

/*
 *  Student Index starts from row 1
 * */

public class GuttDataHelper {

    private static final int STUDENT_COLUMN_INDEX = 0;
    private static final int STUDENT_ROW_INDEX = 1;
    private static final int QUESTION_START_COLUMN_INDEX = 1;
    private static int QUESTION_END_COLUMN_INDEX;
    private static final int QUESTION_ROW_INDEX = 0;

    private static int array[][];
    private static int size;

    public GuttDataHelper(int guttArray[][]) {
        array = guttArray;
        //This is for removing first and last row
        size = guttArray.length - 1;
        //-1 for removing student raw score , -1 for getting index value
        QUESTION_END_COLUMN_INDEX = array[0].length - 1 - 1;
    }

    public ArrayList<String> getRollNumberFromGutt() {
        //Column 0 contains student  index
        ArrayList<String> allRollNumber = ListGenerationHelper.studentRollNumberList();

        ArrayList<String> selectedRollNumber = new ArrayList<>();

        //Student index start from row 1
        for (int i = STUDENT_ROW_INDEX; i < size; i++) {
            selectedRollNumber.add(allRollNumber.get(array[i][STUDENT_COLUMN_INDEX]));
        }

        return selectedRollNumber;
    }

    public ArrayList<String> getStudentnameFromGutt() {
        //Column 0 contains student index
        ArrayList<String> allStudentNames = ListGenerationHelper.getStudentsName();

        ArrayList<String> selectedStudent = new ArrayList<>();

        //Student index start from row 1
        for (int i = STUDENT_ROW_INDEX; i < size; i++) {
            selectedStudent.add(allStudentNames.get(array[i][STUDENT_COLUMN_INDEX]));
        }

        return selectedStudent;
    }

    public ArrayList<String> getQuestionNumberListFromGutt() {
        //Row 0 contain question index in gutt table
        ArrayList<String> allQuestionNumberList = ListGenerationHelper.questionList();

        ArrayList<String> selectedQuestion = new ArrayList<>();

        //Question index start from column 1
        for (int j = QUESTION_START_COLUMN_INDEX; j <= QUESTION_END_COLUMN_INDEX; j++) {
            selectedQuestion.add(allQuestionNumberList.get(array[QUESTION_ROW_INDEX][j]));
        }

        return selectedQuestion;
    }


    public ArrayList<Double> getStudentPercentFromGutt() {
        ArrayList<Double> studentPercent = BasicStudentReportCalculation.getStudentPercent();

        ArrayList<Double> selectedPercent = new ArrayList<>();

        //Student index start form row 1
        for (int i = STUDENT_ROW_INDEX; i < size; i++) {
            selectedPercent.add(studentPercent.get(array[i][STUDENT_COLUMN_INDEX]));
        }

        return selectedPercent;
    }

    public ArrayList<Double> getQuestionDifficultyLevelFromGutt() {

        List<Double> allQuestionDifficultyLevel = ListGenerationHelper.getQuestionDifficultyLevel();

        ArrayList<Double> selectedDifficultyLevel = new ArrayList<>();

        //Selecting question serial wise according to array
        for (int j = QUESTION_START_COLUMN_INDEX; j <= QUESTION_END_COLUMN_INDEX; j++) {
            selectedDifficultyLevel.add(allQuestionDifficultyLevel.get(array[QUESTION_ROW_INDEX][j]));
        }
        return selectedDifficultyLevel;
    }


}
