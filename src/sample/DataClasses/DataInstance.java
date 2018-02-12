package sample.DataClasses;

public class DataInstance {
    private static MainDataClass instance;

    private DataInstance() {
    }

    public static MainDataClass getInstance() {
        return instance;
    }


    public static void setInstance(MainDataClass instance1) {
        instance = instance1;
    }
}
