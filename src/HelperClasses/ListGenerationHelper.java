package HelperClasses;

import sample.DataClasses.Bus;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.util.ArrayList;
import java.util.List;

public class ListGenerationHelper {

    public static TestDetails testDetails = Bus.getInstance();

    // 1-1 1-2 1-3
    //working
    public static ArrayList<String> questionList() {

        ArrayList<String> questionList = new ArrayList<>();
        for (int i = 0; i < testDetails.getQuestion().size(); i++) {
            String q = testDetails.getQuestion().get(i);
            if (testDetails.getSubQuestionList().get(i) != null) {
                for (int j = 0; j < testDetails.getSubQuestionList().get(i); j++) {
                    String quesLabel = q + "-" + (j + 1);
                    questionList.add(quesLabel);
                }
            }
        }

        return questionList;

    }


    //student response(ABCD) in single list
    // tested
    public static List<List<Character>> studentResponseInList() {
        List<List<Character>> studentResponse = new ArrayList<>();
        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {

            StudentDetails student = testDetails.getStudentDetails().get(i);
            //Addding all response in one list
            ArrayList<Character> eachStudentResponse = new ArrayList<>();
            if (student.getResponse() != null) {
                for (int j = 0; j < testDetails.getNumberOfQuestion(); j++) {
                    for (int x = 0; x < testDetails.getSubQuestionList().get(j); x++) {
                        Character r = student.getResponse().get(j).get(x);
                        if (r == null) {
                            eachStudentResponse.add(' ');
                        } else {
                            eachStudentResponse.add(r);
                        }
                    }

                }
                studentResponse.add(eachStudentResponse);

            }


        }
        return studentResponse;
    }

    //student Evaluation(1 and 0) in single list
    // tested
    public static List<List<Integer>> studentEvaluationInList() {
        List<List<Integer>> studentResponse = new ArrayList<>();
        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {

            StudentDetails student = testDetails.getStudentDetails().get(i);
            //Addding all evaluation in one list
            ArrayList<Integer> eachStudentResponse = new ArrayList<>();
            if (student.getResponse() != null) {
                for (int j = 0; j < testDetails.getNumberOfQuestion(); j++) {
                    for (int x = 0; x < testDetails.getSubQuestionList().get(j); x++) {
                        if (student.getEvaluation().get(j).get(x) == null) {
                            eachStudentResponse.add(0);
                        } else {
                            eachStudentResponse.add(student.getEvaluation().get(j).get(x));
                        }
                    }

                }
                studentResponse.add(eachStudentResponse);

            }


        }
        System.out.println(studentResponse.toString());
        return studentResponse;
    }

    //generate student roll number list
    // tested
    public static ArrayList<String> studentRollNumberList() {

        ArrayList<String> rollNumbers = new ArrayList<>();

        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {
            String r = testDetails.getStudentDetails().get(i).getRollNo();
            if (r == null) {
                rollNumbers.add("");
            } else {
                rollNumbers.add(r);
            }
        }

        System.out.println(rollNumbers.toString());

        return rollNumbers;
    }

    //add student response with roll number
    // data form [[A B C D][A B C D]]
    public static List<List<String>> rollNumberAndResponseList() {
        List<List<Character>> studentResponse = studentResponseInList();
        ArrayList<String> rollNumbers = studentRollNumberList();
        List<List<String>> combineList = new ArrayList<>();
        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {

            ArrayList<String> rows = new ArrayList<>();

            rows.add(rollNumbers.get(i));
            for (int j = 0; j < studentResponse.get(i).size(); j++) {
                rows.add(String.valueOf(studentResponse.get(i).get(j)));
            }

            combineList.add(rows);
        }

        System.out.println(combineList.toString());

        return combineList;

    }

    //Evaluation and roll number Combine
    //Data form  [[1 0 1 0][1 1 1]] one row represent each student
    public static List<List<String>> rollNumberAndEvaluationList() {
        List<List<Integer>> studentEvaluation = studentEvaluationInList();
        ArrayList<String> rollNumbers = studentRollNumberList();
        List<List<String>> combineList = new ArrayList<>();
        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {

            ArrayList<String> rows = new ArrayList<>();
            // Adding roll number
            rows.add(rollNumbers.get(i));
            //Adding evaluation details for each student
            for (int j = 0; j < studentEvaluation.get(i).size(); j++) {
                rows.add(String.valueOf(studentEvaluation.get(i).get(j)));
            }

            combineList.add(rows);
        }

        System.out.println(combineList.toString());

        return combineList;

    }

    //get number reponse in of eachQuestion for each option
    //tested
    public static ArrayList<ArrayList<Integer>> allStudentResponseForEachOptionList() {

        ArrayList<ArrayList<Integer>> count = new ArrayList<>();
        List<List<Character>> studentResponse = studentResponseInList();


        for (int i = 0; i < studentResponse.get(0).size(); i++) {
            int optionA = 0;
            int optionB = 0;
            int optionC = 0;
            int optionD = 0;

            ArrayList<Integer> rows = new ArrayList<>();

            for (int j = 0; j < studentResponse.size(); j++) {
                Character r = studentResponse.get(j).get(i);
                if (r == 'A') optionA++;
                else if (r == 'B') optionB++;
                else if (r == 'C') optionC++;
                else if (r == 'D') optionD++;

            }
            rows.add(optionA);
            rows.add(optionB);
            rows.add(optionC);
            rows.add(optionD);
            count.add(rows);
        }
        System.out.println(count.toString());

        return count;

    }


}
