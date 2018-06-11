package Analysis;

import HelperClasses.ListGenerationHelper;

import java.util.ArrayList;
import java.util.List;

public class Distractors {

    static List<Integer> studentTotalMarks;

    static List<List<Character>> studentResponse;

    static List<List<Integer>> studentEvaluationList;
    //Calculating distractors
    // d = (M(a) - M(nonA) * (p*q)^1/2)/ sd

    /* M(a) - mean of total marks obtain by student who opted for option A / no. of student opted
     * M(nona) - mean of total marks obtain by student who not opted for option A / no. of student not opted
     * Pa - probablity of option A to be choosen
     * Qa - probablity of option A  not to be choosen
     * sd = SUM((student score - Mean of total score of student)^2)
     * */
    public static ArrayList<ArrayList<Double>> getDistactors() {

        studentResponse = ListGenerationHelper.studentResponseInList();
        studentTotalMarks = ListGenerationHelper.studentsTotalMarks();

        List<Character> options = new ArrayList<>();
        options.add('A');
        options.add('B');
        options.add('C');
        options.add('D');

        ArrayList<ArrayList<Double>> distractorsList = new ArrayList<>();
        //Loop is working according to number of question

        double standardDeviation = standardDeviation();

        if (standardDeviation > 0) {

            for (int i = 0; i < studentResponse.get(0).size(); i++) {
                //System.out.print((i+1)+".\t");
                ArrayList<Double> disSubList = new ArrayList<>();
                //Loop is working according to options
                for (int x = 0; x < options.size(); x++) {
                    double distractors = (meanDifference(i, options.get(x)) * probabilitiesRoot(i, options.get(x))) / standardDeviation();
                    //System.out.print(" "+distractors);
                    distractors = Math.round(distractors * 100.0) / 100.0;
                    disSubList.add(distractors);

                }
                //System.out.println();
                //System.out.println(disSubList);
                distractorsList.add(disSubList);

            }
        } else {
            return null;
        }


        return distractorsList;

    }


    private static double meanDifference(int quesIndex, char option) {

        int optionFoundSum = 0, optionNotFoundSum = 0, countFound = 0, countNotFound = 0;
        for (int i = 0; i < studentResponse.size(); i++) {
            // ex If option A found
            if (studentResponse.get(i).get(quesIndex) == option) {
                // Adding student marks if option found (Ma)
                optionFoundSum += studentTotalMarks.get(i);
                countFound++;
            } else {
                //Adding student marks if option not found (Mnon A)
                optionNotFoundSum += studentTotalMarks.get(i);
                countNotFound++;
            }

        }
        double meanOptionFound = 0.0;
        double meanOptionNotFound = 0.0;
        System.out.print(option + " = " + countFound + "\t");
        if (countFound > 0) {
            meanOptionFound = (double) optionFoundSum / countFound;
        }
        if (countNotFound > 0) {
            meanOptionNotFound = (double) optionNotFoundSum / countNotFound;
        }

        //System.out.println("Mean of " + option +" = " + meanOptionFound);
        //System.out.println("Mean of not " + option + " = " + meanOptionNotFound);

        double diff = meanOptionFound - meanOptionNotFound;


        return diff;
    }


    // Tested working properly
    private static double probabilitiesRoot(int quesIndex, char option) {


        int count = 0;
        // finding total number of occorence of the given letter
        for (int i = 0; i < studentResponse.size(); i++) {
            if (studentResponse.get(i).get(quesIndex) == option)
                count++;
        }

        double p = (double) count / studentResponse.size();
        double q = 1.0 - p;
        double root = Math.sqrt(p * q);

        return root;

    }

    public static double standardDeviation() {

        studentTotalMarks = ListGenerationHelper.studentsTotalMarks();
        int sum = 0;
        int n = studentTotalMarks.size();
        for (int i = 0; i < n; i++) {
            sum += studentTotalMarks.get(i);
        }
        double mean = (double) sum / n;

        double sdSum = 0.0;
        for (int i = 0; i < n; i++) {

            double diff = (double) studentTotalMarks.get(i) - mean;
            double v = Math.pow(diff, 2.0);
            sdSum += v;

        }


        double sd = sdSum / n;
        sd = Math.sqrt(sd);


        return sd;

    }


    //Calculating binary distractor

    public static ArrayList<Double> getBinaryDistractors() {

        studentEvaluationList = ListGenerationHelper.studentEvaluationInList();
        studentTotalMarks = ListGenerationHelper.studentsTotalMarks();

        ArrayList<Double> distractorsList = new ArrayList<>();

        double standardDeviation = standardDeviation();


        /*System.out.println("Calculate distractor by hand");
        System.out.println("Standard deviation = "+standardDeviation);
        System.out.println("Mean diff = " + binaryDistractorsMeanDifference(0));
        System.out.println("Probalites = " + binaryProbabilitiesRoot(0));*/

        if (standardDeviation > 0) {

            for (int i = 0; i < studentEvaluationList.get(0).size(); i++) {
                //System.out.print((i+1)+".\t");
                double distractors = (binaryDistractorsMeanDifference(i) * binaryProbabilitiesRoot(i)) / standardDeviation();
                //System.out.print(" "+distractors);
                distractors = Math.round(distractors * 100.0) / 100.0;
                distractorsList.add(distractors);

            }
        } else {
            return null;
        }


        return distractorsList;

    }

    public static double binaryDistractorsMeanDifference(int quesIndex) {
        int binaryNum = 1;

        int binaryFoundSum = 0, binaryNotFoundSum = 0, countFound = 0, countNotFound = 0;
        for (int i = 0; i < studentEvaluationList.size(); i++) {
            // ex If option 1 found
            if (studentEvaluationList.get(i).get(quesIndex) == binaryNum) {
                // Adding student marks if option found (M1)
                binaryFoundSum += studentTotalMarks.get(i);
                countFound++;
            } else {
                //Adding student marks if option not found (Mnon A)
                binaryNotFoundSum += studentTotalMarks.get(i);
                countNotFound++;
            }

        }
        double meanOptionFound = 0.0;
        double meanOptionNotFound = 0.0;
        //System.out.print(binaryNum + " = " + countFound+"\t");
        if (countFound > 0) {
            meanOptionFound = (double) binaryFoundSum / countFound;
        }
        if (countNotFound > 0) {
            meanOptionNotFound = (double) binaryNotFoundSum / countNotFound;
        }

        //System.out.println("Mean of " + option +" = " + meanOptionFound);
        //System.out.println("Mean of not " + option + " = " + meanOptionNotFound);

        double diff = meanOptionFound - meanOptionNotFound;


        return diff;

    }

    private static double binaryProbabilitiesRoot(int quesIndex) {

        int binaryNum = 1;
        int count = 0;
        // finding total number of occorence of the given binary num
        for (int i = 0; i < studentEvaluationList.size(); i++) {
            if (studentEvaluationList.get(i).get(quesIndex) == binaryNum)
                count++;
        }

        double p = (double) count / studentEvaluationList.size();
        double q = 1.0 - p;
        double root = Math.sqrt(p * q);

        return root;

    }


}
