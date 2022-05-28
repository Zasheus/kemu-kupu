package application.popups;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Generic confirm window class which can be given a title and message
 * It will create a popup window with that title and message that must be addressed
 * before the user can resume interaction with the rest of the program
 */
public class ConfirmBox {

    private static boolean answer;

    /**
     * Method to create the popup
     * @param title title of the window
     * @param message Message in the window
     * @return true if yes was clicked
     */
    public static boolean newConfirmBox(String title, String message){

        answer = false;

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

        //Setup buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");
        yesButton.setPrefHeight(30);
        yesButton.setPrefWidth(60);
        noButton.setPrefHeight(30);
        noButton.setPrefWidth(60);

        //Add button methods
        yesButton.setOnAction(e -> {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e -> {
            answer = false;
            window.close();
        });

        //Add buttons to an HBox for the layout
        HBox hBox = new HBox(10);
        hBox.getChildren().addAll(yesButton, noButton);
        hBox.setAlignment(Pos.CENTER);

        //Setup layout
        VBox layout = new VBox(10);
        layout.setMinWidth(480);
        layout.setMinHeight(135);
        layout.getChildren().addAll(label, hBox);
        layout.setAlignment(Pos.CENTER);

        //Create scene and add to window
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(Objects.requireNonNull(ConfirmBox.class.getResource("/resources/css/PopUpBox.css")).toExternalForm());
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}