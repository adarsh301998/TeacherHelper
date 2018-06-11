package HelperClasses;

import java.util.ArrayList;
import java.util.List;

public class ArrayHelper extends BaseHelper {


    /*
     * [StudentIndex, Evaluation, TotalMarks]
     *
     * */
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
        /*System.out.println("Before sorting");
        for (int i = 0; i < guttArray.length; i++) {
            for (int j = 0; j < guttArray[0].length; j++) {
                System.out.print(guttArray[i][j] + " ");
            }
            System.out.println();
        }
*/
        return guttArray;
    }

    /*
     *
     * 1. getting all elements to gutt table sorted
     * 2. Arranging them in 2d array
     * 3. removing rows (student score full)
     * 4. removing column (Question score is full) - > decreasing student score when column removed
     * 5. return new array
     *
     * */
    public static int[][] getGuttImprovised(boolean fullyImprovisedGuttTable) {

        int[][] guttArrayTable = GuttSortingHelper.getSortedArray(ArrayHelper.guttTableArray());
        int questionSortedIndex[] = GuttSortingHelper.getQuesSortedIndex();
        int questionSum[] = GuttSortingHelper.getQuesSum();

        ArrayList<ArrayList<Integer>> improvedGuttTable = new ArrayList<>();

        //Arranging question sum in decending order
        int temp[] = new int[questionSum.length];
        int index = 0;
        for (int i = questionSum.length - 1; i >= 0; i--) {
            temp[index] = questionSum[i];
            index++;
        }
        questionSum = temp;

        //Creating array by combing all three

        //Inserting questionSortedIndex
        ArrayList<Integer> addQues = new ArrayList<>();
        //Column of roll number
        addQues.add(-1);
        for (int i : questionSortedIndex) {
            addQues.add(i);
        }
        //Column of raw score
        addQues.add(-1);
        improvedGuttTable.add(addQues);

        //Inserting studentIndex evaluation and Raw score

        for (int i = 0; i < guttArrayTable.length; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j : guttArrayTable[i]) {

                row.add(j);

            }
            improvedGuttTable.add(row);
        }

        //Inserting question score
        ArrayList<Integer> quesSumList = new ArrayList<>();
        quesSumList.add(-1);
        for (int i : questionSum) {
            quesSumList.add(i);
        }
        quesSumList.add(-1);
        improvedGuttTable.add(quesSumList);


        int array[][] = convertArrayListToArray(improvedGuttTable);
        //Remove student who scored full score

        /*In Reliability test complete guttTable is required*/


        /*Returning semi improved gutttable*/
        if (!fullyImprovisedGuttTable) {

            return array;

        }


        /*REMOVING NECESSARY ROW AND COLUMN*/

        ArrayList<ArrayList<Integer>> studentScoreFiltered = removeFullScorerStudent(array);

        //Remove Item who scored full score
        ArrayList<ArrayList<Integer>> itemScoreFiltered = removeQuestionScoredFull(studentScoreFiltered);

        //Fully imporved gutt table
        int fullyImprovedGuttTable[][] = convertArrayListToArray(itemScoreFiltered);


        System.out.println("Printing Gutt Array");
        for (int i = 0; i < fullyImprovedGuttTable.length; i++) {
            for (int j = 0; j < fullyImprovedGuttTable[0].length; j++) {
                System.out.print(fullyImprovedGuttTable[i][j] + " ");
            }
            System.out.println();
        }


        //Re-Calculate question raw score
        int quesRawScoreIndex = fullyImprovedGuttTable.length - 1;
        int startRowInex = 1, startColumnIndex = 1;
        // -1 one for questioRawScore and studentRawScore , -1 again for index location
        int endRowIndex = fullyImprovedGuttTable.length - 2, endColumnIndex = fullyImprovedGuttTable[1].length - 2;

        for (int j = startColumnIndex; j <= endColumnIndex; j++) {
            int sum = 0;
            for (int i = startRowInex; i <= endRowIndex; i++) {
                sum += fullyImprovedGuttTable[i][j];
            }
            fullyImprovedGuttTable[quesRawScoreIndex][j] = sum;
        }

        return fullyImprovedGuttTable;
    }

    private static int[][] convertArrayListToArray(ArrayList<ArrayList<Integer>> improvedGuttTable) {

        int r = improvedGuttTable.size();
        int c = improvedGuttTable.get(0).size();
        int array[][] = new int[r][c];

        for (int i = 0; i < r; i++) {
            ArrayList<Integer> arrayList = improvedGuttTable.get(i);
            for (int j = 0; j < arrayList.size(); j++) {
                array[i][j] = arrayList.get(j);
            }
        }
        return array;
    }

    public static ArrayList<ArrayList<Integer>> removeFullScorerStudent(int ar[][]) {

        int r = ar.length;
        int c = ar[0].length;
        ArrayList<ArrayList<Integer>> fullRawScoreFiltered = new ArrayList<>();
        int max = getMaxRawScoreStaticMethod();
        int maxRawScoreIndex = c - 1;
        for (int i = 0; i < r; i++) {
            if (ar[i][maxRawScoreIndex] == max) {
                continue;
            }
            ArrayList<Integer> sublist = new ArrayList<>();
            for (int j = 0; j < c; j++) {
                sublist.add(ar[i][j]);
            }
            fullRawScoreFiltered.add(sublist);
        }

        return fullRawScoreFiltered;


    }

    public static ArrayList<ArrayList<Integer>> removeQuestionScoredFull(ArrayList<ArrayList<Integer>> arrayLists) {

        ArrayList<ArrayList<Integer>> filteredList = new ArrayList<>();
        int max = getItemMaxScore();
        ArrayList<Integer> quesSumList = arrayLists.get(arrayLists.size() - 1);
        //count is one for ignoring student index
        int count = 1;
        //Counting number of column have full score
        for (int i = 1; i < quesSumList.size() - 1; i++) {
            if (quesSumList.get(i) == max) {
                count++;
            }
        }

        //Ignoring column with full score
        for (int i = 0; i < arrayLists.size(); i++) {
            ArrayList<Integer> sublist = new ArrayList<>();
            ArrayList<Integer> list = arrayLists.get(i);
            for (int j = 0; j < arrayLists.get(0).size(); j++) {
                if (j < count) {
                    continue;
                }
                sublist.add(list.get(j));
            }
            filteredList.add(sublist);
        }

        //Adding student roll number back
        for (int i = 0; i < arrayLists.size(); i++) {
            filteredList.get(i).add(0, arrayLists.get(i).get(0));
        }

        //Decreasing student Raw score if any item have full score
        if (count > 1) {
            int decRawScore = count - 1;
            int rawScoreIndex = filteredList.get(1).size() - 1;
            /*Ignore 1st row question index
             * Decrease Raw score  */

            for (int i = 1; i < filteredList.size() - 1; i++) {
                int rawScore = filteredList.get(i).get(rawScoreIndex);
                //Remove raw score
                filteredList.get(i).remove(rawScoreIndex);

                //update raw score
                rawScore -= decRawScore;
                filteredList.get(i).add(rawScore);
            }

        }

        return filteredList;

    }

    public static int[][] transposeMatrix(int ar[][]) {

        int r = ar.length;
        int c = ar[0].length;

        int transposeArray[][] = new int[c][r];

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                transposeArray[j][i] = ar[i][j];
            }
        }
        return transposeArray;
    }


}
