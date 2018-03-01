package sample.DataClasses;

import java.util.ArrayList;

public class TestDetails {
    private int index;
    private String teacherName;
    private String testName;
    private String institute;
    private String dateTime;
    private int numberOfQuestion;
    private int numberOfStudent;
    private ArrayList<StudentDetails> studentDetails;
    private String classLabel;
    private ArrayList<Integer> subQuestionList;
    private ArrayList<ArrayList<Character>> key;
    private ArrayList<String> question;

    /*    public TestDetails(String teacherName, String testName, String institute, LocalDate dateTime, int numberOfQuestion, int numberOfStudent, String classLabel) {
            this.teacherName = teacherName;
            this.testName = testName;
            this.institute = institute;
            this.dateTime = dateTime;
            this.numberOfQuestion = numberOfQuestion;
            this.numberOfStudent = numberOfStudent;
            this.classLabel = classLabel;

        }*/
    public TestDetails() {
    }

    public int getIndex() {
        return index;
    }

    public ArrayList<Integer> getSubQuestionList() {
        return subQuestionList;
    }

    public void setSubQuestionList(ArrayList<Integer> subQuestionList) {
        this.subQuestionList = subQuestionList;
    }

    public ArrayList<ArrayList<Character>> getKey() {
        return key;
    }

    public ArrayList<String> getQuestion() {
        return question;
    }

    public void setKey(ArrayList<ArrayList<Character>> key) {
        this.key = key;
    }

    public void setQuestion(ArrayList<String> question) {
        this.question = question;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }

    public void setNumberOfStudent(int numberOfStudent) {
        this.numberOfStudent = numberOfStudent;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setClassLabel(String classLabel) {
        this.classLabel = classLabel;
    }

    public void setStudentDetails(ArrayList<StudentDetails> studentDetails) {
        this.studentDetails = studentDetails;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public String getTestName() {
        return testName;
    }

    public String getInstitute() {
        return institute;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public int getNumberOfStudent() {
        return numberOfStudent;
    }

    public ArrayList<StudentDetails> getStudentDetails() {
        return studentDetails;
    }

    public String getClassLabel() {
        return classLabel;
    }


}
