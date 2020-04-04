package ru.hse.edu.myurachinskiy.utils;

import ru.hse.edu.myurachinskiy.controllers.KeyboardController;

import java.io.IOException;

public class CommandsProvider {
    public CommandsProvider(KeyboardController keyboardController) throws IOException {
        this.keyboardController = keyboardController;
        this.wifiListener = new WifiListener(accelerometerData -> {
            // TODO: Make moves smoother
            if (Math.abs(accelerometerData.getZ() - AppSettings.Z_EPSILON) < 0) {
                keyboardController.moveCursor(accelerometerData.getX(), accelerometerData.getY());
            } else {
                keyboardController.pressMouse();
            }
        });
    }

    private KeyboardController keyboardController;
    private WifiListener wifiListener;
}
