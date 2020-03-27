package ru.hse.edu.myurachinskiy.models;

public class QwertyRussianKeyboard {
    public void pressButton(int row, int col) {
        Key key = getKey(row, col);
        // TODO: Implement method
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

    public String GetText() {
        return text;
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

    private String text;
    private boolean isShiftPressed;

    private Key[][] keyboard =
            {
                    {new Key(1, false), new Key("1", "!"),
                     new Key("2", "@"), new Key("3", "#"),
                     new Key("3", "#"), new Key("4", "$"),
                     new Key("5", "%"), new Key("6", "^")
                    }
            }; // TODO: replace with russian keyboard
}
