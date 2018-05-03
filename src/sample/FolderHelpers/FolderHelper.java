package sample.FolderHelpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class FolderHelper {

    public static void createFolder() {
        new File(StorageLocation.STORAGE_PATH.toUri()).mkdirs();
    }

    public static ArrayList<String> getTestFolderContent() {

        ArrayList<String> arrayList = new ArrayList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(StorageLocation.STORAGE_PATH)) {

            for (Path path : directoryStream) {
                //removing extension
                String s = path.getFileName().toString();
                int n = s.indexOf('.');
                s = s.substring(0, n);

                arrayList.add(s);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
