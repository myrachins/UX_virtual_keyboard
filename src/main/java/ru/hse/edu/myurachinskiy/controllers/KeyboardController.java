package ru.hse.edu.myurachinskiy.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import ru.hse.edu.myurachinskiy.models.DvorakKeyboard;
import ru.hse.edu.myurachinskiy.models.Point;
import ru.hse.edu.myurachinskiy.utils.WifiCommandsProvider;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO: draw keyboard
        // TODO: draw current point
        this.commandsProvider = new WifiCommandsProvider(this);
    }

    public void moveCursor(double xDelta, double yDelta) {
        cursor.setX(cursor.getX() + xDelta);
        cursor.setY(cursor.getY() + yDelta);
    }

    public void press() {
        // TODO: Implement method
    }

    @FXML
    private Canvas canvas;
    @FXML
    private TextArea text;

    private Point cursor;
    private DvorakKeyboard keyboard;
    private WifiCommandsProvider commandsProvider;
}
