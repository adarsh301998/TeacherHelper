package HelperClasses;

import sample.DataClasses.StudentDetails;

import java.util.ArrayList;

public class ListHelper {

    /*public static ArrayList<StudentDetails> keyToStudents(ArrayList<Character> key, ArrayList<StudentDetails> studentDetails, int index) {
        if (studentDetails != null) {
            int size = 0;
            for (StudentDetails sd : studentDetails) {
                if (sd != null) {
                    if (sd.getResponse() != null)
                        size = sd.getResponse().get(index).size();
                }
            }
            if (size > 0) {
                for (int i = 0; i < studentDetails.size(); i++) {
                    if (studentDetails.get(i) != null && studentDetails.get(0).getResponse() != null) {

                        int difference = key.size() - size;
                        ArrayList<Character> response = studentDetails.get(i).getResponse().get(index);
                        ArrayList<Integer> evaluation = studentDetails.get(i).getEvaluation().get(index);
                        if (difference > 0) {
                            // Adding null if key increased
                            for (int j = 0; j < difference; j++) {
                                response.add(null);
                                evaluation.add(null);
                            }
                        } else if (difference < 0) {
                            //creating temp list
                            ArrayList<Character> temp = new ArrayList<>();
                            ArrayList<Integer> temp2 = new ArrayList<>();
                            // Cutting elements if key reduced
                            for (int j = 0; j < key.size(); j++) {
                                temp.add(studentDetails.get(i).getResponse().get(index).get(j));
                                temp2.add(studentDetails.get(i).getEvaluation().get(index).get(j));
                            }
                            studentDetails.get(i).getResponse().set(index, temp);
                            studentDetails.get(i).getEvaluation().set(index, temp2);
                        }

                        studentDetails.get(i).getEvaluation().set(index, reevaluate(key, studentDetails.get(i).getResponse().get(index)));

                    } else {
                        return studentDetails;
                    }

                }
            }
        } else {
            return studentDetails;
        }


        return studentDetails;
    }*/

    public static ArrayList<StudentDetails> keyToStudents(ArrayList<Character> key, ArrayList<StudentDetails> studentDetails, int index, int size) {
        if (studentDetails != null) {

            if (size > 0) {
                for (int i = 0; i < studentDetails.size(); i++) {
                    if (studentDetails.get(i) != null && studentDetails.get(0).getResponse() != null) {

                        /*int difference = key.size() - size;
                        ArrayList<Character> response = studentDetails.get(i).getResponse().get(index);
                        ArrayList<Integer> evaluation = studentDetails.get(i).getEvaluation().get(index);
                        if (difference > 0) {
                            // Adding null if key increased
                            for (int j = 0; j < difference; j++) {
                                response.add(null);
                                evaluation.add(null);
                            }
                        } else if (difference < 0) {
                            //creating temp list
                            ArrayList<Character> temp = new ArrayList<>();
                            ArrayList<Integer> temp2 = new ArrayList<>();
                            // Cutting elements if key reduced
                            for (int j = 0; j < key.size(); j++) {
                                temp.add(studentDetails.get(i).getResponse().get(index).get(j));
                                temp2.add(studentDetails.get(i).getEvaluation().get(index).get(j));
                            }
                            studentDetails.get(i).getResponse().set(index, temp);
                            studentDetails.get(i).getEvaluation().set(index, temp2);
                        }*/


                        int keydiff = size - key.size();
                        if (keydiff < 0) {
                            key.subList(size, key.size()).clear();
                        } else if (keydiff > 0) {
                            for (int j = 0; j < keydiff; j++) {
                                key.add(null);
                            }
                        }

                        int responsesize = studentDetails.get(i).getResponse().get(index).size();
                        int responsediff = size - responsesize;

                        if (responsediff < 0) {
                            studentDetails.get(i).getResponse().get(index).subList(size, responsesize).clear();
                        } else if (responsediff > 0) {
                            for (int j = 0; j < responsediff; j++) {
                                studentDetails.get(i).getResponse().get(index).add(null);
                            }
                        }

                        studentDetails.get(i).getEvaluation().set(index, reevaluate(key, studentDetails.get(i).getResponse().get(index)));

                    } else {
                        return studentDetails;
                    }

                }
            }
        } else {
            return studentDetails;
        }


        return studentDetails;
    }


    // Evaluation is done on the basis of change in subQuestion in key scene
    public static ArrayList<Integer> reevaluate(ArrayList<Character> key, ArrayList<Character> response) {
        ArrayList<Integer> evaluation = new ArrayList<>();
        for (int i = 0; i < key.size(); i++) {

            if (key.get(i) != null) {
                if (response.get(i) == key.get(i)) {
                    evaluation.add(1);
                } else {
                    evaluation.add(0);
                }
            } else {
                evaluation.add(null);
            }
        }
        return evaluation;
    }


    // Evaluation is done on the basis of change in subQuestion in student details scene
    /*public static ArrayList<Integer> revaluationByStudentDetails(ArrayList<Character> key, ArrayList<Character> response) {
        ArrayList<Integer> evaluation = new ArrayList<>();

        for (int i=0;i<response.size();i++) {
            if (response.get(i) != null) {
                if (response.get(i) == key.get(i)){
                    evaluation.add(1);
                }else {
                    evaluation.add(0);
                }
            }else {
                evaluation.add(0);
            }
        }
        return evaluation;
    }
*/


}

