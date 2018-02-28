package sample.DataClasses;

import java.io.Serializable;

public class Bus implements Serializable {
    private static TestDetails instance;

    private Bus() {
        instance = new TestDetails();
    }

    public static TestDetails getInstance() {
        return instance;
    }

    public static void setInstance(TestDetails instance1) {
        instance = instance1;
    }
}
