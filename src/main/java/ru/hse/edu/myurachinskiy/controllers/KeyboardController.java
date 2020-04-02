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
import ru.hse.edu.myurachinskiy.models.keyboards.QwertyRussianKeyboard;
import ru.hse.edu.myurachinskiy.models.keys.Key;
import ru.hse.edu.myurachinskiy.utils.AppSettings;
import ru.hse.edu.myurachinskiy.utils.CommandsProvider;

import java.awt.*;
import java.awt.event.InputEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class KeyboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.keyboard = new QwertyRussianKeyboard();
        try {
            robot = new Robot();
            drawKeyboard();
            printText();

            this.commandsProvider = new CommandsProvider(this);
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void moveCursor(double xDelta, double yDelta) {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();
        int x = (int) point.getX();
        int y = (int) point.getY();
        robot.mouseMove(x + (int)xDelta, y + (int)yDelta);
    }
    
    public void pressMouse() {
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
    }

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
                keyBtnList.add(currentKeyBtn);
                prevKeyRightBord += keyWidth;
            }
        }
        this.anchorPane.getChildren().addAll(keyBtnList);
        
    }

    @FXML
    private TextArea text;
    @FXML
    private AnchorPane anchorPane;

    private Robot robot;
    
    private Point cursor;
    private Keyboard keyboard;
    private CommandsProvider commandsProvider;
}
