package HelperClasses;

import sample.DataClasses.Bus;
import sample.DataClasses.TestDetails;

public class BaseHelper {

    public static TestDetails testDetails = Bus.getInstance();


    static void updateTestDetails() {
        testDetails = Bus.getInstance();
    }


}
