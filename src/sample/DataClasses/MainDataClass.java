package sample.DataClasses;

import java.io.Serializable;
import java.util.ArrayList;

public class MainDataClass implements Serializable {
    private ArrayList<TestDetails> testDetails;

    public MainDataClass() {
    }

    public ArrayList<TestDetails> getTestDetails() {
        return testDetails;
    }

    public void setTestDetails(ArrayList<TestDetails> testDetails) {
        this.testDetails = testDetails;
    }
    /*public void addItem(TestDetails details) {
        testDetails.add(details);
    }*/
}
