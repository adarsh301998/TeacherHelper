package sample.DataClasses;

import java.io.Serializable;

public class MainDataClass implements Serializable {
    private static TestDetails instance;

    public MainDataClass() {
    }

    public static TestDetails getInstance() {
        return instance;
    }

    public static void setInstance(TestDetails instance1) {
        instance = instance1;
    }
}
