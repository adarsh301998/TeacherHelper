package HelperClasses;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.DataClasses.TestDetails;

public class DialogPopUp {

    public static void basicInfoDialog(StackPane stackPane, TestDetails testDetails) {


        String displayInfo[] = {"Teacher Name\t: " + testDetails.getTeacherName(),
                "Test Name\t: " + testDetails.getTestName(),
                "Institute\t\t: " + testDetails.getInstitute(),
                "Date\t\t\t: " + testDetails.getDateTime()};

        Label label[] = new Label[displayInfo.length];

        for (int i = 0; i < displayInfo.length; i++) {
            label[i] = new Label(displayInfo[i]);
            label[i].setFont(new Font("Segoi UI", 15));
        }

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Text("Basic Info"));

        VBox vBox = new VBox(label);
        vBox.setSpacing(20);
        layout.setBody(vBox);

        JFXButton button = new JFXButton("Okay");
        button.setPrefWidth(100);
        button.getStyleClass().add("btn-dialog");

        layout.setActions(button);

        JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });


        dialog.show();
    }


}
