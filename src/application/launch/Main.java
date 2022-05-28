package application.launch;

import application.guihelpers.ListenerAdder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Objects;

/**
 * Maori Spelling Quiz JavaFX Program
 *
 * by Max, Alan and Ryan
 *
 * This application is a spelling quiz game that helps the user practise
 * maori words from selected categories.
 */
public class Main extends Application {

	public static Stage window;

	@Override
	public void start(Stage stage) {

		Main.window = stage;
		ListenerAdder.setCloseRequestBeforeQuiz();

		try {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/fxml/Welcome.fxml")));
			Scene scene = new Scene(root, 1120, 768);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
