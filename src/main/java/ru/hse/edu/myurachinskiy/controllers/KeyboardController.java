package ru.hse.edu.myurachinskiy.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;
import ru.hse.edu.myurachinskiy.models.Point;
import ru.hse.edu.myurachinskiy.models.keyboards.QwertyRussianKeyboard;
import ru.hse.edu.myurachinskiy.models.keys.Key;
import ru.hse.edu.myurachinskiy.utils.WifiCommandsProvider;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.cursor = new Point(canvas.getWidth() / 2, canvas.getHeight() / 2);
        this.keyboard = new QwertyRussianKeyboard();

        drawKeyboard();
        drawPoint();

        this.commandsProvider = new WifiCommandsProvider(this);
    }

    public void moveCursor(double xDelta, double yDelta) {
        cursor.setX(cursor.getX() + xDelta);
        cursor.setY(cursor.getY() + yDelta);
        // TODO: Move cursor on canvas
    }

    public void press() {
        // TODO: Implement method
    }

    private void drawPoint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillOval(cursor.getX(), cursor.getY(), 1, 1);
    }

    private void drawKeyboard() {
        double keyHeight = canvas.getHeight() / keyboard.getRowsNumber();

        for (int row = 0; row < keyboard.getRowsNumber(); ++row) {
            double rowsWidth = keyboard.sumRowWidth(row);
            double scale = canvas.getWidth() / rowsWidth;
            double prevKeyRightBord = 0;
            for (int col = 0; col < keyboard.getColumnsNumber(row); ++col) {
                Key currentKey = keyboard.getKey(row, col);
                double keyWidth = currentKey.getWidth() * scale;
                drawBorder(prevKeyRightBord, row * keyHeight, keyWidth, keyHeight);
                drawKey(currentKey, prevKeyRightBord, row * keyHeight, keyWidth, keyHeight);
                prevKeyRightBord = prevKeyRightBord + keyWidth;
            }
        }
    }

    private void drawBorder(double x, double y, double w, double h) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.GREY);
        gc.setLineWidth(4);
        gc.strokeRoundRect(x, y, w, h, 0, 0);
    }

    private void drawKey(Key key, double xKey, double yKey, double keyWidth, double keyHeight) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeText(key.getKey(), xKey, yKey, keyWidth); // TODO: Print shifted text also
    }

    @FXML
    private Canvas canvas;
    @FXML
    private TextArea text;

    private Point cursor;
    private Keyboard keyboard;
    private WifiCommandsProvider commandsProvider;
}
