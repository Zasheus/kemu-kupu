package application.guihelpers;

import javafx.scene.control.Button;

/**
 * Class to disable and enable a set of buttons given to in
 */
public class ButtonDisabler {

    private Button[] buttons;

    public ButtonDisabler(Button button1, Button button2, Button button3) {
        this.buttons = new Button[3];
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
    }

    public ButtonDisabler(Button button1, Button button2, Button button3, Button button4) {
        this.buttons = new Button[4];
        buttons[0] = button1;
        buttons[1] = button2;
        buttons[2] = button3;
        buttons[3] = button4;
    }

    /**
     * Method to disable or enable buttons
     * @param isDisabled true to disable buttons
     */
    public void changeButtonDisabled(boolean isDisabled) {

        for (Button button : buttons) {
            if (button.isVisible()) {
                button.setDisable(isDisabled);
            }
        }
    }
}
