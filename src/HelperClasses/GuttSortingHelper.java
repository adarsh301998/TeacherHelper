package HelperClasses;

import java.util.Arrays;

public class GuttSortingHelper {

    static int quesSortedIndex[];
    static int returnQuesSum[];

    public static int[] getQuesSortedIndex() {
        return quesSortedIndex;
    }


    //Sorting Question Score
    public static int[] getQuesSum() {
        Arrays.sort(returnQuesSum);
        return returnQuesSum;
    }


    /*
     * Sorting [student Index, Evaluation, student total marks]
     * */
    public static int[][] getSortedArray(int[][] array) {

        int r = array.length;
        int c = array[0].length;
        int marksIndex = array[0].length - 1;

        int rowSortedArray[][] = new int[r][c];

        for (int i = 0; i < r; i++) {

            int index = getMaxMarksIndex(array);
            for (int j = 0; j < c; j++) {
                rowSortedArray[i][j] = array[index][j];
            }
            //Setting it to -1 to get next max
            array[index][marksIndex] = -1;
        }

        int questionSum[] = new int[c - 2];
        int index = 0;
        for (int i = 1; i < c - 1; i++) {

            for (int j = 0; j < r; j++) {
                questionSum[index] += rowSortedArray[j][i];
            }
            index++;
        }

        returnQuesSum = Arrays.copyOf(questionSum, questionSum.length);


        int columnSorted[][] = new int[r][c];
        quesSortedIndex = new int[c - 2];
        index = 0;

        for (int i = 0; i < r; i++) {
            columnSorted[i][0] = rowSortedArray[i][0];
            columnSorted[i][marksIndex] = rowSortedArray[i][marksIndex];
        }

        for (int i = 1; i < c - 1; i++) {

            int maxIndex = getMaxQuesSum(questionSum);
            quesSortedIndex[index] = maxIndex;


            //columnSorted[index][0] = rowSortedArray[index][0];
            for (int j = 0; j < r; j++) {
                columnSorted[j][i] = rowSortedArray[j][maxIndex + 1];
            }
            //columnSorted[index][c-1] = array[index][c-1];
            questionSum[maxIndex] = -1;
            index++;
        }

        for (int i = 0; i < quesSortedIndex.length; i++) {
            System.out.println(quesSortedIndex[i]);
        }

        /*System.out.println("After sorting");
        for (int i = 0; i < columnSorted.length; i++) {
            for (int j = 0; j < columnSorted[0].length; j++) {
                System.out.print(columnSorted[i][j] + " ");
            }
            System.out.println();
        }
*/
        return columnSorted;
    }

    private static int getMaxQuesSum(int ar[]) {
        int max = ar[0];
        int maxIndex = 0;
        for (int i = 0; i < ar.length; i++) {
            if (max < ar[i]) {
                max = ar[i];
                maxIndex = i;
            }
        }
        return maxIndex;

    }

    private static int getMaxMarksIndex(int ar[][]) {

        int marksIndex = ar[0].length - 1;
        int max = ar[0][marksIndex];
        int maxIndex = 0;
        for (int i = 0; i < ar.length; i++) {

            if (max < ar[i][marksIndex]) {
                maxIndex = i;
                max = ar[i][marksIndex];
            }

        }
        return maxIndex;
    }


}

