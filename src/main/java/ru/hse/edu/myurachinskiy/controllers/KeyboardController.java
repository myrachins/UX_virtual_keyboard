package ru.hse.edu.myurachinskiy.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;
import ru.hse.edu.myurachinskiy.models.keyboards.QwertyRussianKeyboard;
import ru.hse.edu.myurachinskiy.models.keys.Key;
import ru.hse.edu.myurachinskiy.models.keys.TipKey;
import ru.hse.edu.myurachinskiy.predicativeSystem.DictionaryPredictiveSystem;
import ru.hse.edu.myurachinskiy.predicativeSystem.OurDictionaryPersister;
import ru.hse.edu.myurachinskiy.predicativeSystem.PredictiveTextSystem;
import ru.hse.edu.myurachinskiy.predicativeSystem.XMLDictionaryPersister;
import ru.hse.edu.myurachinskiy.utils.CommandsProvider;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class KeyboardController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.keyboard = new QwertyRussianKeyboard();
        try {
            predictiveTextSystem = DictionaryPredictiveSystem.newInstance();
            try {
                predictiveTextSystem.fill(OurDictionaryPersister
                    .newInstance(new FileInputStream(new File("M:\\java HW\\ux-keyboard\\src\\main" +
                        "\\resources\\unigrams.txt")), StandardCharsets.UTF_8));
    
            } catch (Exception e) {
                System.out.println("Problems with dict");
            }
            if (keyboard instanceof QwertyRussianKeyboard) {
                ((QwertyRussianKeyboard) keyboard).setPredictiveTextSystem(predictiveTextSystem);
            }
            robot = new Robot();
            drawKeyboard();
            printText();

            this.commandsProvider = new CommandsProvider(this);
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    public void moveCursor(double xDelta, double yDelta) {
        if (robot != null) {
            PointerInfo pointerInfo = MouseInfo.getPointerInfo();
            Point point = pointerInfo.getLocation();
            int x = (int) point.getX();
            int y = (int) point.getY();
            robot.mouseMove(x + (int)xDelta, y + (int)yDelta);
        } else {
            throw new NullPointerException("robot in NULL");
        }
    }
    
    public void pressMouse() {
        if (robot != null) {
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } else {
            throw new NullPointerException("robot is NULL");
        }
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
        List<Button> keyBtnList = new ArrayList<>();
        
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
                
                if (currentKey instanceof TipKey) {
                    ((TipKey) currentKey).setButtonKey(currentKeyBtn);
                }
                
                currentKeyBtn.setOnMousePressed(event -> {
                    currentKey.pressKey(keyboard);
                    currentKeyBtn.setText(currentKey.getKey());
                    printText();
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
    
    private DictionaryPredictiveSystem predictiveTextSystem;
    
    private Point cursor;
    private Keyboard keyboard;
    private CommandsProvider commandsProvider;
}
