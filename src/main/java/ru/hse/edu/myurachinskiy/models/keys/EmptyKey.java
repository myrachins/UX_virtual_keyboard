package ru.hse.edu.myurachinskiy.models.keys;

import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

public class EmptyKey extends Key {
    public EmptyKey() {
        super("");
    }
    @Override
    public void pressKey(Keyboard keyboard) { }
}
