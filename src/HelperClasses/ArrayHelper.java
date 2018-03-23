package HelperClasses;

import java.util.List;

public class ArrayHelper extends BaseHelper {


    public static int[][] guttTableArray() {

        updateTestDetails();

        int numOfStd = testDetails.getNumberOfStudent();
        List<List<Integer>> studentResponse = ListGenerationHelper.studentEvaluationInList();

        int colLength = studentResponse.get(0).size() + 2;

        int[][] guttArray = new int[numOfStd][colLength];
        List<Integer> totalMarks = ListGenerationHelper.studentsTotalMarks();


        for (int i = 0; i < numOfStd; i++) {
            int colIndex = 0;
            //Adding Student index for obtianing corrosponding roll number
            guttArray[i][colIndex] = i;
            colIndex++;
            List<Integer> response = studentResponse.get(i);

            for (int j = 0; j < response.size(); j++) {
                guttArray[i][colIndex] = response.get(j);
                colIndex++;
            }

            guttArray[i][colIndex] = totalMarks.get(i);

        }
        System.out.println("Before sorting");
        for (int i = 0; i < guttArray.length; i++) {
            for (int j = 0; j < guttArray[0].length; j++) {
                System.out.print(guttArray[i][j] + " ");
            }
            System.out.println();
        }

        return guttArray;
    }


}
