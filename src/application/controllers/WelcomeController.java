package application.controllers;

import javafx.event.ActionEvent;

/**
 * Controller for the welcome screen
 */
public class WelcomeController extends Controller {

	/**
	 * Switches to categories scene
	 * @param event Button event
	 * @throws Exception If loading scene fails
	 */
	public void startNewGame(ActionEvent event) throws Exception {
		loadScene(event, "/resources/fxml/Categories.fxml");
	}

}
