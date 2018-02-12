package sample.DataClasses;

import java.util.ArrayList;

public class StudentDetails {
    private String name;
    private String rollNo;
    private ArrayList<ArrayList<Character>> response;
    private ArrayList<ArrayList<Integer>> evaluation;

    public StudentDetails(String name, String rollNo) {
        this.name = name;
        this.rollNo = rollNo;
        response = new ArrayList<>();
        evaluation = new ArrayList<>();
    }

    public StudentDetails() {
    }

    public String getName() {
        return name;
    }

    public String getRollNo() {
        return rollNo;
    }

    public ArrayList<ArrayList<Character>> getResponse() {
        return response;
    }

    public ArrayList<ArrayList<Integer>> getEvaluation() {
        return evaluation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public void setResponse(ArrayList<ArrayList<Character>> response) {
        this.response = response;
    }

    public void setEvaluation(ArrayList<ArrayList<Integer>> evaluation) {
        this.evaluation = evaluation;
    }
}


