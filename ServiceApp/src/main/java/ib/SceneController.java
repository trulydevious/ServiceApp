package ib;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SceneController {

    /**
     * This class is used to transfer data from JavaFX stage to another stage.
     */

    @FXML
    private TextField display;

    /**
     * This method transfer message.
     * @param message
     */

    public void transferMessage(String message) {
        display.setText(message);
    }
}
