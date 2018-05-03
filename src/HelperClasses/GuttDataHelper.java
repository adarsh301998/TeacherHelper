package HelperClasses;

import calculations.BasicStudentReportCalculation;

import java.util.ArrayList;

/*
 *  Student Index starts from row 1
 * */

public class GuttDataHelper {

    private static final int STUDENT_COLUMN_INDEX = 0;
    private static final int STUDENT_ROW_INDEX = 1;
    private static int array[][];
    private static int size;

    public GuttDataHelper(int guttArray[][]) {
        array = guttArray;
        //This is for removing first and last row
        size = guttArray.length - 2;
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


    public ArrayList<Double> getStudentPercentFromGutt() {
        ArrayList<Double> studentPercent = BasicStudentReportCalculation.getStudentPercent();

        ArrayList<Double> selectedPercent = new ArrayList<>();

        //Student index start form row 1
        for (int i = STUDENT_ROW_INDEX; i < size; i++) {
            selectedPercent.add(studentPercent.get(array[i][STUDENT_COLUMN_INDEX]));
        }

        return studentPercent;
    }


}
