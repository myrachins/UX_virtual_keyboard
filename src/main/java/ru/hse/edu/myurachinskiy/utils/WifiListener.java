package ru.hse.edu.myurachinskiy.utils;

import java.util.function.Consumer;

public class WifiListener {
    public WifiListener(Consumer<AccelerometerData> consumer) {
        this.consumer = consumer;
        // TODO: Connect to wifi and listen for commands, when call
    }

    Consumer<AccelerometerData> consumer;
}
