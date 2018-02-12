package HelperClasses;

import com.jfoenix.controls.JFXRadioButton;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;


public class RadioButtonHelper {

    public static ToggleGroup[] generateRadioButton(GridPane gridPane, int n, int width, ToggleGroup[] toggleGroups) throws InterruptedException {

        char[] options = {'A', 'B', 'C', 'D'};
        // Creating option lables

        for (int i = 0; i < 5; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints(width);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        for (int i = 0; i < options.length; i++) {
            Label label = new Label(String.valueOf(options[i]));
            label.setTextFill(Color.WHITE);
            gridPane.add(label, i + 1, 0);
        }
        // Creating radio button
        for (int i = 1; i <= n; i++) {
            Label label = new Label(i + ".");
            label.setTextFill(Color.WHITE);

            gridPane.add(label, 0, i);

            toggleGroups[i - 1] = new ToggleGroup();
            for (int j = 0; j < options.length; j++) {
                JFXRadioButton radioButton = new JFXRadioButton();
                radioButton.setUserData(options[j]);
                radioButton.setToggleGroup(toggleGroups[i - 1]);
                gridPane.add(radioButton, j + 1, i);
            }
        }

        return toggleGroups;
    }

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

    public static void deselectRadioButton(ToggleGroup[] toggleGroups) {


        for (int i = 0; i < toggleGroups.length; i++) {
            if (toggleGroups[i].getSelectedToggle() != null) {
                toggleGroups[i].getSelectedToggle().setSelected(false);
            }
        }

    }

}
