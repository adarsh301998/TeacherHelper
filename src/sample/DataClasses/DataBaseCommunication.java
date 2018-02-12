package sample.DataClasses;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.*;

public class DataBaseCommunication {

    static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static void convertJavaToJSON(Object object) {
        try {
            String result = mapper.writeValueAsString(object);
            System.out.println(result);
            BufferedWriter writer = new BufferedWriter(new FileWriter("data.json"));
            writer.write(result);
            writer.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static <T> T convertJSONToJava(Class<T> cls) {
        T result = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.json"));
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
}
