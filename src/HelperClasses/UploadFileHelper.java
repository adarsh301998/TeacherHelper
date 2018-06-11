package HelperClasses;

import javafx.stage.FileChooser;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import sample.DataClasses.StudentDetails;
import sample.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


public class UploadFileHelper {

    //static char options[] = {'A','B','C','D','E'};

    static char options[] = {'A', 'B', 'C', 'D'};

    public static ArrayList<String> openFileChooserAndReadFile() {

        FileChooser fileChooser = new FileChooser();
        ArrayList<String> dataList = new ArrayList<>();
        File file = fileChooser.showOpenDialog(Main.stage);

        int index = file.getName().indexOf(".");
        index++;
        String extension = file.getName().substring(index);

        System.out.println(extension);

        try {
            if (extension.equals("txt")) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    dataList.add(line);
                }
            } else if (extension.equals("xlsx") || extension.equals("xls")) {
                Workbook workbook = WorkbookFactory.create(file);
                //Getting sheet 0
                Sheet sheet = workbook.getSheetAt(0);

                //Data formatter to format cell
                DataFormatter dataFormatter = new DataFormatter();

                System.out.println("\nIterating over row and column using iterator\n");

                Iterator<Row> rowIterator = sheet.rowIterator();

                while (rowIterator.hasNext()) {

                    Row row = rowIterator.next();

                    //Now lets us iterate over column of current row
                    Iterator<Cell> cellIterator = row.cellIterator();
                    String data = "";
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        String cellValue = dataFormatter.formatCellValue(cell);
                        if (cellValue.equals(""))
                            cellValue = " ";
                        data += cellValue + "\t";
                    }
                    dataList.add(data);
                }
                System.out.println(dataList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        return dataList;
    }


    public static ArrayList<ArrayList<Character>> getKeyDetailsFromDataList(ArrayList<String> dataList) {

        String split = getSplitString(dataList.get(0));
        ArrayList<ArrayList<Character>> characters = new ArrayList<>();
        if (split.equals("")) {
            //Assuming Key character is not separated
            for (String data : dataList) {
                ArrayList<Character> rowOfKey = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    char c = convertCharToOption(data.charAt(i));
                    rowOfKey.add(c);
                }
                characters.add(rowOfKey);
            }
        } else if (split.equals(" ") || split.equals("\t")) {
            //Assuming Key character is separated by space or a tab

            for (String data : dataList) {
                ArrayList<Character> rowOfKey = new ArrayList<>();
                String splitArray[] = data.split(split);
                for (int i = 0; i < splitArray.length; i++) {
                    rowOfKey.add(convertCharToOption(splitArray[i].charAt(0)));
                }
                characters.add(rowOfKey);
            }
        }
        return characters;
    }


    /*
     * Ignoring 1st row expected to be question numbers
     * */

    public static ArrayList<StudentDetails> getStudentDetailsFromDataList(ArrayList<String> dataList) {

        ArrayList<StudentDetails> studentDetails = new ArrayList<>();
        ArrayList<Character> keyList = ListGenerationHelper.getKeyList();
        int rollNumberIndex = 0;
        int nameIndex = 1;
        int responseIndex = 2;
        int blankResponseIndex = 4;
        String split = "\t";
        for (int x = 1; x < dataList.size(); x++) {
            String data = dataList.get(x);
            StudentDetails studentInfo = new StudentDetails();
            ArrayList<ArrayList<Character>> response = new ArrayList<>();
            ArrayList<ArrayList<Integer>> evaluation = new ArrayList<>();
            if (split.equals("\t") || split.equals(" ")) {

                String studentArray[] = data.split(split);
                studentInfo.setRollNo(studentArray[rollNumberIndex]);
                studentInfo.setName(studentArray[nameIndex]);

                ArrayList<Character> responseList = new ArrayList<>();
                ArrayList<Integer> evaluationList = new ArrayList<>();

                int index = 0;
                for (int i = responseIndex; i < studentArray.length; i++) {
                    char res = ' ';
                    if (studentArray[i].equals("") || studentArray[i].equals(" ")) {

                        /*
                         *
                         * Sending null instead to E
                         *
                         * */

                        //res = options[blankResponseIndex];
                        //res = null;
                        responseList.add(null);
                    } else {
                        res = convertCharToOption(studentArray[i].charAt(0));
                        responseList.add(res);
                    }

                    if (keyList.get(index) != null) {
                        if (keyList.get(index) == res) {
                            evaluationList.add(1);
                        } else {
                            evaluationList.add(0);
                        }
                    } else {
                        evaluationList.add(0);
                    }
                    index++;
                }

                response.add(responseList);
                evaluation.add(evaluationList);
            }

            studentInfo.setResponse(response);
            studentInfo.setEvaluation(evaluation);

            studentDetails.add(studentInfo);

        }

        return studentDetails;

    }


    /*
     * Given character can be 1,a,A
     * */
    private static char convertCharToOption(char c) {

        /*If any other character come it is considered as nothing*/
        char acceptedChar = ' ';

        if (c == '1' || c == '2' || c == '3' || c == '4') {
            String s = String.valueOf(c);
            int index = Integer.valueOf(s);
            index--;
            acceptedChar = options[index];
        } else if (c == 'a' || c == 'b' || c == 'c' || c == 'd') {
            acceptedChar = Character.toUpperCase(c);
        }

        return acceptedChar;
    }

    private static String getSplitString(String s) {

        String split = "";
        char c = s.charAt(s.length() - 2);
        if (c == ' ')
            split = " ";
        if (c == '\t')
            split = "\t";

        return split;

    }


}
