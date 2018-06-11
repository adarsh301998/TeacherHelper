package sample.DataClasses;

import org.codehaus.jackson.map.ObjectMapper;
import sample.FolderHelpers.StorageLocation;

import java.io.*;
import java.util.ArrayList;

public class DataBaseCommunication {

    static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static void convertJavaToJSON(TestDetails object) {
        try {
            System.out.println(object.getTeacherName());

            String result = mapper.writeValueAsString(object);

            String filename = object.getTestName() + object.getDateTime();

            System.out.println("Result" + result);
            BufferedWriter writer = new BufferedWriter(new FileWriter(StorageLocation.TEST_STORAGE_PATH + filename + ".json"));
            writer.write(result);
            writer.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static <T> T convertJSONToJava(Class<T> cls, String testname) {
        T result = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(StorageLocation.TEST_STORAGE_PATH + testname + ".json"));
            String line, data = "";
            while ((line = reader.readLine()) != null) {
                data += line;
            }
            System.out.println(data);
            result = mapper.readValue(data, cls);
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return result;
    }

    public static ArrayList<String> readStringLineFromFile(String filename) {
        BufferedReader reader = null;
        ArrayList<String> dataList = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(StorageLocation.TEST_STORAGE_PATH + filename + ".txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                dataList.add(line);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dataList;
    }

}
