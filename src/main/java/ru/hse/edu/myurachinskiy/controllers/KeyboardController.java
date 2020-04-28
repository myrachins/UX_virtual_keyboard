package ru.hse.edu.myurachinskiy.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;
import ru.hse.edu.myurachinskiy.models.keyboards.QwertyRussianKeyboard;
import ru.hse.edu.myurachinskiy.models.keys.CommandKey;
import ru.hse.edu.myurachinskiy.models.keys.Key;
import ru.hse.edu.myurachinskiy.models.keys.OrdinaryKey;
import ru.hse.edu.myurachinskiy.models.keys.TipKey;
import ru.hse.edu.myurachinskiy.predicativeSystem.DictionaryPredictiveSystem;
import ru.hse.edu.myurachinskiy.predicativeSystem.OurDictionaryPersister;
import ru.hse.edu.myurachinskiy.utils.CommandsProvider;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
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
            
            bufferedReaderSentences = new BufferedReader(new FileReader(new File("M:\\java HW\\ux-keyboard\\src\\main" +
                "\\resources\\OUTPUT.txt")));
            updateCurrentSentenceLabel();
            
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
                    if (!sessionInProcess && !sessionEnded && (currentKey instanceof TipKey || currentKey instanceof OrdinaryKey)) {
                        startTimeSentence = startTime = System.currentTimeMillis();
                        sessionInProcess = true;
                    } else if (currentKey instanceof CommandKey
                        && ((CommandKey) currentKey).getCommand() == CommandKey.COMMAND.ENTER
                        && sessionInProcess && !sessionEnded) {
                        updateSpeedLabel();
                        updateCurrentSentenceLabel();
                        startTimeSentence = System.currentTimeMillis();
                    }
                });
                
                keyBtnList.add(currentKeyBtn);
                prevKeyRightBord += keyWidth;
            }
        }
        this.anchorPane.getChildren().addAll(keyBtnList);
    }
    
    private void updateSpeedLabel() {
        float currentSentenceTimeMinDelta = (System.currentTimeMillis() - startTimeSentence)/60000F;
        float sessionTimeMinDelta = (System.currentTimeMillis() - startTime)/60000F;
        int wordCount = currentSentence.length() - currentSentence.replaceAll(" ", "").length() + 1;
        wordsTotal += wordCount;
        speedLabel.setText("Words per minute in last sentence: " + wordCount / currentSentenceTimeMinDelta
            + "   Word per minute average: " + wordsTotal / sessionTimeMinDelta);
    }
    
    private void updateCurrentSentenceLabel() {
        try {
            if ((currentSentence = bufferedReaderSentences.readLine()) != null) {
                nextSentenceLabel.setText("Current sentence: " + currentSentence);
            } else {
                currentSentence = "";
                nextSentenceLabel.setText("That's the end of the session");
                sessionEnded = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            sessionEnded = true;
        }
    }

    @FXML
    private TextArea text;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label speedLabel;
    @FXML
    private Label nextSentenceLabel;

    private Robot robot;
    
    private DictionaryPredictiveSystem predictiveTextSystem;
    
    private boolean sessionInProcess = false;
    private boolean sessionEnded = false;
    private BufferedReader bufferedReaderSentences;
    private int wordsTotal = 0;
    private long startTime;
    private long startTimeSentence;
    private String currentSentence;
    
    private Point cursor;
    private Keyboard keyboard;
    private CommandsProvider commandsProvider;
}
