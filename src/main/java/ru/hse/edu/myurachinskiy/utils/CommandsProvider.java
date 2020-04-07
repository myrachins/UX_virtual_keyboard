package ru.hse.edu.myurachinskiy.utils;

import ru.hse.edu.myurachinskiy.controllers.KeyboardController;

import java.io.IOException;

public class CommandsProvider {
    public CommandsProvider(KeyboardController keyboardController) throws IOException {
        this.keyboardController = keyboardController;
        this.wifiListener = new WifiListener(accelerometerData -> {
            if (accelerometerData.getZ() < AppSettings.Z_EPSILON) {
                keyboardController.moveCursor(accelerometerData.getX() * AppSettings.COORDINATE_SCALE,
                                        accelerometerData.getY() * AppSettings.COORDINATE_SCALE);
            } else {
                keyboardController.pressMouse();
            }
        });
    }

    private KeyboardController keyboardController;
    private WifiListener wifiListener;
}
