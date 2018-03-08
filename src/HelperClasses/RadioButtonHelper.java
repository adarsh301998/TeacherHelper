package HelperClasses;

import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class RadioButtonHelper {

    public static ToggleGroup[] generateRadioButton(GridPane gridPane, int n, ToggleGroup[] toggleGroups, ArrayList<Character> selection) throws InterruptedException {
        char[] options = {'A', 'B', 'C', 'D'};
        // Creating option lables



        for (int i = 0; i < options.length; i++) {
            Label label = new Label("      " + String.valueOf(options[i]));
            //label.setTextFill(Color.WHITE);
            gridPane.add(label, i + 1, 0);
        }
        // Creating radio button
        for (int i = 1; i <= n; i++) {
            Label label = new Label(i + ".");
            //label.setTextFill(Color.WHITE);

            gridPane.add(label, 0, i);

            toggleGroups[i - 1] = new ToggleGroup();
            for (int j = 0; j < options.length; j++) {
                JFXRadioButton radioButton = new JFXRadioButton();
                radioButton.setUserData(options[j]);
                // int s,ex;
           /*     if (!selection.isEmpty()) {
                    if(selection.get(i-1) != null) {
                        s = selection.get(i - 1);
                        if (j == (s % 65)) {
                            radioButton.setSelected(true);
                        }
                    }
                }*/

                radioButton.setToggleGroup(toggleGroups[i - 1]);
                gridPane.add(radioButton, j + 1, i);
            }
        }

        selectedKey(selection, gridPane);

        return toggleGroups;
    }

    public static void selectedKey(ArrayList<Character> keyprsent, GridPane key_map) {
        int count = 5;
        int index = 0;
        int s;
        for (int i = 0; i < keyprsent.size(); i++) {
            if (keyprsent.get(i) != null) {
                s = keyprsent.get(i);
                index = count + (s % 65);

                // System.out.println("index  = " + index);
                if (count < key_map.getChildren().size()) {
                    if (key_map.getChildren().get(index) instanceof JFXRadioButton) {
                        //  System.out.println("index  = " + index);
                        JFXRadioButton radioButton = (JFXRadioButton) key_map.getChildren().get(index);
                        radioButton.setSelected(true);

                        // System.out.println("Radio" + radioButton.getUserData());
                    }
                    count += 5;
                } else {
                    break;
                }


            }

        }

    }

    /*public static ArrayList<Integer> getSelectedToggles(ToggleGroup[] toggleGroup) {
        ArrayList<Integer> selectedToggles = new ArrayList<>();
        for (int i = 0; i < toggleGroup.length; i++) {
            try {
                if (toggleGroup[i].getSelectedToggle() != null) {
                    selectedToggles.add(i);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return selectedToggles;
    }*/

    /*public static ArrayList<Character> getSelectedRadio(GridPane key_map) {

        ArrayList<Character> key = new ArrayList<>();
            int j=0,f=0;
         for (int i=5;i<key_map.getChildren().size();i++) {

             if (key_map.getChildren().get(i) instanceof JFXRadioButton) {
                 JFXRadioButton radioButton = (JFXRadioButton) key_map.getChildren().get(i);
                 if (radioButton.isSelected()==true) {
                     f++;
                 }
                 j++;
                 if (j==4) {
                     if (f==0)
                         key.add(null);
                     else
                         key.add((char) radioButton.getUserData());

                     f=0;
                     j=0;
                     i++;
                 }
             }
         }

        return key;
    }
*/

    public static ArrayList<Integer> getSelectedToggles(ToggleGroup[] toggleGroup) {
        ArrayList<Integer> selectedToggles = new ArrayList<>();
        for (int i = 0; i < toggleGroup.length; i++) {
            try {
                if (toggleGroup[i].getSelectedToggle() != null) {
                    selectedToggles.add(i);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return selectedToggles;
    }

    public static ArrayList<Character> getSelectedRadio(ToggleGroup[] toggleGroups, ArrayList<Integer> selectedToggles) {

        ArrayList<Character> key = new ArrayList<>();
        int count = 0;
        for (int i = 0; i < toggleGroups.length; i++) {
            if (selectedToggles.size() > count && selectedToggles.get(count) == i) {
                key.add((char) toggleGroups[i].getSelectedToggle().getUserData());
                count++;
            } else {
                key.add(null);
            }
        }
        return key;
    }

    public static void deselectRadioButton(GridPane key_map) {


        /*for (int i = 0; i < toggleGroups.length; i++) {
            if (toggleGroups[i].getSelectedToggle() != null) {
                toggleGroups[i].getSelectedToggle().setSelected(false);
            }
        }*/

        int j = 0;
        for (int i = 5; i < key_map.getChildren().size(); i++) {
            //
            // System.out.println(i);
            if (key_map.getChildren().get(i) instanceof JFXRadioButton) {
                JFXRadioButton r1 = (JFXRadioButton) key_map.getChildren().get(i);
                r1.setSelected(false);
                j++;
                if (j == 4) {
                    i++;
                    j = 0;
                }
            }

        }


    }


    public static void gridConstraints(GridPane gridPane, int width) {
        for (int i = 0; i < 5; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(width);
            gridPane.getColumnConstraints().add(columnConstraints);
        }
    }

}
