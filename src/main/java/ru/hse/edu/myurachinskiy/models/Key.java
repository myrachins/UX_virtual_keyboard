package ru.hse.edu.myurachinskiy.models;

public class Key {
    public Key(String key) {
        this.key = key;
        this.keyWithShift = key;
        this.width = 1;
        this.isUsable = true;
    }

    public Key(String key, String keyWithShift) {
        this(key);
        this.keyWithShift = keyWithShift;
    }

    public Key(String key, String keyWithShift, int width, boolean isUsable) {
        this.key = key;
        this.keyWithShift = keyWithShift;
        this.width = width;
        this.isUsable = isUsable;
    }

    public Key(int width, boolean isUsable) {
        this.width = width;
        this.isUsable = isUsable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public boolean isUsable() {
        return isUsable;
    }

    public void setUsable(boolean usable) {
        isUsable = usable;
    }

    private String key;
    private String keyWithShift;
    private double width;
    private boolean isUsable;
}
