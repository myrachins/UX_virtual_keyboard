package ru.hse.edu.myurachinskiy.models.keys;

import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

public abstract class Key {
    public Key(String key) {
        this.key = key;
        this.shiftedKey = key;
        this.width = 1;
    }

    public Key(String key, String shiftedKey) {
        this.key = key;
        this.shiftedKey = shiftedKey;
    }

    public Key(String key, String shiftedKey, double width) {
        this.key = key;
        this.shiftedKey = shiftedKey;
        this.width = width;
    }

    public String getKey() {
        return key;
    }
    public String getShiftedKey() {
        return shiftedKey;
    }
    public double getWidth() {
        return width;
    }

    public abstract void pressKey(Keyboard keyboard);

    protected String key;
    protected String shiftedKey;
    protected double width;
}
