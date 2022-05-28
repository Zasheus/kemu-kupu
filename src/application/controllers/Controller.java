package application.controllers;

import java.io.IOException;
import java.util.Objects;

import application.launch.Main;
import application.popups.ConfirmBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class with methods shared by all controller classes
 */
public abstract class Controller {

	//Loading scene method

	/**
	 * Method to move to a new scene
	 * @param event Button event
	 * @param sceneName Scene to load
	 * @throws IOException If loading scene fails
	 */
	public void loadScene(ActionEvent event, String sceneName) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(sceneName)));
		Stage stage = Main.window;
		Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
		stage.setScene(scene);
		stage.show();
	}

	//Navbar Button Methods

	/**
	 * Method to quit the application with confirmation window
	 * @param event Button Event
	 */
	public void quitButton(ActionEvent event) {
		if(ConfirmBox.newConfirmBox("Confirm Quit", "Are you sure you want to quit")) {
			Main.window.close();
		}
	}

	/**
	 * Method to go back to the home screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	public void homeButton(ActionEvent event) throws IOException {
		loadScene(event, "/resources/fxml/Welcome.fxml");
	}

	/**
	 * Method to go back to the category screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	public void newGameButton(ActionEvent event) throws IOException {
		loadScene(event, "/resources/fxml/Categories.fxml");
	}

	/**
	 * Method to go to the add words screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	public void addWordsButton(ActionEvent event) throws IOException {
		loadScene(event, "/resources/fxml/AddWords.fxml");
	}

	/**
	 * Method to go to the remove words screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	public void removeWordsButton(ActionEvent event) throws IOException {
		loadScene(event, "/resources/fxml/RemoveWords.fxml");
	}

	/**
	 * Method to go to the view words screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	public void viewWordsButton(ActionEvent event) throws IOException {
		loadScene(event, "/resources/fxml/ViewWords.fxml");
	}

	/**
	 * Method to go to the leaderboard screen
	 * @param event Button Event
	 * @throws IOException Throws exception if scene loading fails
	 */
	public void leaderboardButton(ActionEvent event) throws IOException {
		loadScene(event, "/resources/fxml/Leaderboard.fxml");
	}

}
