package ru.hse.edu.myurachinskiy.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;
import ru.hse.edu.myurachinskiy.models.Point;
import ru.hse.edu.myurachinskiy.models.keyboards.QwertyRussianKeyboard;
import ru.hse.edu.myurachinskiy.models.keys.Key;
import ru.hse.edu.myurachinskiy.utils.AppSettings;
import ru.hse.edu.myurachinskiy.utils.CommandsProvider;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class KeyboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //this.cursor = new Point(canvas.getWidth() / 2, canvas.getHeight() / 2);
        this.keyboard = new QwertyRussianKeyboard();
        
        drawKeyboard();
        printText();

        this.commandsProvider = new CommandsProvider(this);
    }

    public void moveCursor(double xDelta, double yDelta) {
        cursor.setX(cursor.getX() + xDelta);
        cursor.setY(cursor.getY() + yDelta);
    }

    /*public void press() {
        int row = Math.min((int) (keyboard.getRowsNumber() * cursor.getY() / canvas.getHeight()),
                keyboard.getRowsNumber() - 1);
        int col = getPressedColumn(row);
        keyboard.pressButton(row, col);
        printText();
        // TODO: highlight pressed key
    }*/

    private int getPressedColumn(int row) {
        double rowsWidth = keyboard.sumRowWidth(row);
        double scale = anchorPane.getWidth() / rowsWidth;
        double prevKeyRightBord = 0;

        for (int col = 0; col < keyboard.getColumnsNumber(row); ++col) {
            Key currentKey = keyboard.getKey(row, col);
            double keyWidth = currentKey.getWidth() * scale;
            if (cursor.getX() >= prevKeyRightBord && cursor.getX() <= prevKeyRightBord + keyWidth) {
                return col;
            }
            prevKeyRightBord += keyWidth;
        }
        throw new IllegalArgumentException("x value is invalid");
    }

    private void printText() {
        text.setText(keyboard.getText());
    }

    /*private void drawPoint() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillOval(cursor.getX() - AppSettings.POINT_RADIUS,
                cursor.getY() - AppSettings.POINT_RADIUS,
                AppSettings.POINT_RADIUS * 2, AppSettings.POINT_RADIUS * 2);
    }*/

    private void drawKeyboard() {
        double keyHeight = this.anchorPane.getMaxHeight() / keyboard.getRowsNumber();
        List<Button> keyBtnList = new ArrayList<Button>();
        for (int row = 0; row < keyboard.getRowsNumber(); ++row) {
            double rowsWidth = keyboard.sumRowWidth(row);
            double scale = this.anchorPane.getMaxWidth() / rowsWidth;
            double prevKeyRightBord = 0;
            for (int col = 0; col < keyboard.getColumnsNumber(row); ++col) {
                Key currentKey = keyboard.getKey(row, col);
                double keyWidth = currentKey.getWidth() * scale;
                Button currentKeyBtn = new Button(currentKey.getKey());
                currentKeyBtn.setMinHeight(keyHeight);
                AnchorPane.setBottomAnchor(currentKeyBtn, (keyboard.getRowsNumber() - row - 1) * keyHeight);
                AnchorPane.setLeftAnchor(currentKeyBtn, prevKeyRightBord);
                AnchorPane.setRightAnchor(currentKeyBtn, anchorPane.getMaxWidth() - keyWidth - prevKeyRightBord);
                currentKeyBtn.setOnMousePressed(event -> {
                    currentKey.pressKey(keyboard);
                    text.setText(keyboard.getText());
                });
                //drawBorder(prevKeyRightBord, row * keyHeight, keyWidth, keyHeight);
                //drawKey(currentKey, prevKeyRightBord, row * keyHeight, keyWidth, keyHeight);
                keyBtnList.add(currentKeyBtn);
                prevKeyRightBord += keyWidth;
            }
        }
        this.anchorPane.getChildren().addAll(keyBtnList);
        
    }

    /*private void drawBorder(double x, double y, double w, double h) {
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
    }*/

    @FXML
    private TextArea text;
    @FXML
    private AnchorPane anchorPane;

    private Point cursor;
    private Keyboard keyboard;
    private CommandsProvider commandsProvider;
}
