package HelperClasses;

import sample.DataClasses.Bus;
import sample.DataClasses.StudentDetails;
import sample.DataClasses.TestDetails;

import java.util.ArrayList;
import java.util.List;

public class ListGenerationHelper {

    public static TestDetails testDetails = Bus.getInstance();


    private static void updateTestDetails() {
        testDetails = Bus.getInstance();
    }
    // 1-1 1-2 1-3
    //working
    public static ArrayList<String> questionList() {

        updateTestDetails();
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

    //Small question list
    // ex Q1 Q2 Q3
    public static ArrayList<String> smallSizeQuestionList() {
        ArrayList<String> ques = questionList();
        ArrayList<String> sortQues = new ArrayList<>();
        for (int i = 0; i < ques.size(); i++) {
            int t = i + 1;
            sortQues.add("Q" + t);
        }
        return sortQues;
    }


    //student response(ABCD) in single list
    // tested
    public static List<List<Character>> studentResponseInList() {
        updateTestDetails();
        List<List<Character>> studentResponse = new ArrayList<>();
        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {

            StudentDetails student = testDetails.getStudentDetails().get(i);
            //Adding all response in one list
            ArrayList<Character> eachStudentResponse = new ArrayList<>();
            if (student.getResponse() != null) {
                for (int j = 0; j < testDetails.getNumberOfQuestion(); j++) {
                    if (testDetails.getSubQuestionList().get(j) != null) {
                        for (int x = 0; x < testDetails.getSubQuestionList().get(j); x++) {
                            Character r = student.getResponse().get(j).get(x);
                            if (r == null) {
                                eachStudentResponse.add(' ');
                            } else {
                                eachStudentResponse.add(r);
                            }
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
        updateTestDetails();
        List<List<Integer>> studentResponse = new ArrayList<>();
        for (int i = 0; i < testDetails.getNumberOfStudent(); i++) {

            StudentDetails student = testDetails.getStudentDetails().get(i);
            //Addding all evaluation in one list
            ArrayList<Integer> eachStudentResponse = new ArrayList<>();
            if (student.getResponse() != null) {
                for (int j = 0; j < testDetails.getNumberOfQuestion(); j++) {
                    if (testDetails.getSubQuestionList().get(j) != null) {
                        for (int x = 0; x < testDetails.getSubQuestionList().get(j); x++) {
                            if (student.getEvaluation().get(j).get(x) == null) {
                                eachStudentResponse.add(0);
                            } else {
                                eachStudentResponse.add(student.getEvaluation().get(j).get(x));
                            }
                        }
                    }

                }
                studentResponse.add(eachStudentResponse);

            }


        }
        System.out.println(studentResponse.toString());
        return studentResponse;
    }


    //student total score
    public static List<Integer> studentsTotalMarks() {
        updateTestDetails();
        List<List<Integer>> studentEvalutionList = studentEvaluationInList();
        List<Integer> sum = new ArrayList<>();
        for (int i = 0; i < studentEvalutionList.size(); i++) {
            List<Integer> temp = studentEvalutionList.get(i);
            int s = 0;
            // Calculating sum for each student
            for (int j = 0; j < temp.size(); j++) {
                s += temp.get(j);
            }
            sum.add(s);

        }

        return sum;
    }

    //generate student roll number list
    // tested
    public static ArrayList<String> studentRollNumberList() {
        updateTestDetails();
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
        updateTestDetails();
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
        updateTestDetails();
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
        updateTestDetails();
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

    public static ArrayList<Character> getKeyList() {
        updateTestDetails();
        ArrayList<Character> key = new ArrayList<>();
        ArrayList<ArrayList<Character>> keyList = testDetails.getKey();

        for (int i = 0; i < keyList.size(); i++) {

            key.addAll(keyList.get(i));
        }

        return key;
    }


    public static List<List<String>> guttArrayToList(int[][] guttArray, int[] quesSum) {

        List<List<String>> guttList = new ArrayList<>();
        List<String> roollnumbers = studentRollNumberList();

        for (int i = 0; i < guttArray.length; i++) {

            List<String> subList = new ArrayList<>();
            subList.add(roollnumbers.get(guttArray[i][0]));
            for (int j = 1; j < guttArray[0].length; j++) {
                subList.add(String.valueOf(guttArray[i][j]));
            }

            guttList.add(subList);
        }
        List<String> subList = new ArrayList<>();
        subList.add("Total");

        for (int i = quesSum.length - 1; i >= 0; i--) {
            subList.add(String.valueOf(quesSum[i]));
        }
        subList.add("");
        guttList.add(subList);


        return guttList;

    }

    //Sorting according to maximum number 1 in the give question
    public static List<String> sortedQuestion(int[] quesIndex) {
        List<String> sortedList = new ArrayList<>();
        List<String> queslist = questionList();

        for (int i = 0; i < quesIndex.length; i++) {
            sortedList.add(queslist.get(quesIndex[i]));
        }
        return sortedList;
    }


}
