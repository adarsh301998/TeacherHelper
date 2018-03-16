package HelperClasses;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReaderHelper {

    public static boolean testPresent() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("src//Tests//testName.txt"));
            String data = "";
            if ((data = reader.readLine()) == null) {
                return false;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


}
