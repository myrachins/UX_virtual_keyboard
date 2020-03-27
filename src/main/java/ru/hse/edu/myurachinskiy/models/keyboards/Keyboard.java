package ru.hse.edu.myurachinskiy.models.keyboards;

import ru.hse.edu.myurachinskiy.models.keys.Key;

public abstract class Keyboard {
    public void pressButton(int row, int col) {
        Key key = getKey(row, col);
        key.pressKey(this);
    }

    public Key getKey(int row, int col) {
        if (row < 0 || row >= keyboard.length) {
            throw new IllegalArgumentException("Invalid row");
        }
        if (col < 0 || col >= keyboard[row].length) {
            throw new IllegalArgumentException("Invalid column");
        }
        return keyboard[row][col];
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShiftPressed() {
        return isShiftPressed;
    }

    public void pressShift() {
        isShiftPressed = !isShiftPressed;
    }

    public int getRowsNumber() {
        return keyboard.length;
    }

    public int getColumnsNumber(int row) {
        if (row < 0 || row >= keyboard.length) {
            throw new IllegalArgumentException("Invalid row");
        }
        return keyboard[row].length;
    }

    public double sumRowWidth(int row) {
        if (row < 0 || row >= keyboard.length) {
            throw new IllegalArgumentException("Invalid row");
        }
        double sum = 0;
        for (Key key : keyboard[row]) {
            sum += key.getWidth();
        }
        return sum;
    }

    protected String text;
    protected boolean isShiftPressed;

    protected Key[][] keyboard;
    
    public Key[][] getKeyboard() {
        return keyboard;
    }
    
    public void setKeyboard(Key[][] keyboard) {
        this.keyboard = keyboard;
    }
	
	public void setShiftPressed(boolean shiftPressed) {
		isShiftPressed = shiftPressed;
	}
}
