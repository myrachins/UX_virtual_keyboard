package ru.hse.edu.myurachinskiy.models.keys;

import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

public class OrdinaryKey extends Key {
    public OrdinaryKey(String key) {
        super(key);
    }

    public OrdinaryKey(String key, String shiftedKey) {
        super(key, shiftedKey);
    }

    public OrdinaryKey(String key, String shiftedKey, double width) {
        super(key, shiftedKey, width);
    }

    @Override
    public void pressKey(Keyboard keyboard) {
        if (keyboard.isShiftPressed())
        	keyboard.setText(keyboard.getText() + this.shiftedKey);
        else
			keyboard.setText(keyboard.getText() + this.key);
    }
}