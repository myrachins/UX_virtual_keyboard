package ru.hse.edu.myurachinskiy.utils;

import ru.hse.edu.myurachinskiy.controllers.KeyboardController;

public class CommandsProvider {
    public CommandsProvider(KeyboardController keyboardController) {
        this.keyboardController = keyboardController;
        this.wifiListener = new WifiListener(accelerometerData -> {
            // TODO: Call controller methods based on wifi data
        });
    }

    private KeyboardController keyboardController;
    private WifiListener wifiListener;
}
