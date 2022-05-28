package application.popups;

import application.guihelpers.ListenerAdder;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Generic user entry window class which can be given a title and message
 * It will create a popup window with that title and message that must be addressed
 * before the user can resume interaction with the rest of the program
 */
public class UserEntryBox {

    private static String returnString;

    /**
     * Method to create the popup
     * @param title title of the window
     * @param message Message in the window
     */
    public static String newUserEntryBox(String title, String message){

        //Create a stage
        Stage window = new Stage();

        //Set title and make modal so events cannot be delivered to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        //Setup Label
        Label label = new Label();
        label.setWrapText(true);
        label.setAlignment(Pos.CENTER);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setText(message);

        //Setup TextField
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);
        textField.setMaxWidth(450);

        //Setup buttons
        Button okButton = new Button("Ok");
        okButton.setFocusTraversable(false);
        okButton.setPrefHeight(30);
        okButton.setPrefWidth(60);

        //Add button methods
        okButton.setOnAction(e -> {
            if (textField.getText().isEmpty()) return;
            returnString = textField.getText();
            window.close();
        });

        //Setup layout
        VBox layout = new VBox(10);
        layout.setMinWidth(480);
        layout.setMinHeight(135);
        layout.getChildren().addAll(label, textField, okButton);
        layout.setAlignment(Pos.CENTER);

        //Add listeners to TextField
        ListenerAdder.setUpBasicTextFieldListeners(textField, okButton);

        //Make the popup return null if its closed
        window.setOnCloseRequest(windowEvent -> returnString = null);

        //Create scene and add to window
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(Objects.requireNonNull(UserEntryBox.class.getResource("/resources/css/PopUpBox.css")).toExternalForm());
        window.setScene(scene);
        window.showAndWait();

        return returnString;
    }
}