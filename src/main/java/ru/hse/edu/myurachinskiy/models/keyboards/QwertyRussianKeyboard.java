package ru.hse.edu.myurachinskiy.models.keyboards;

import ru.hse.edu.myurachinskiy.models.keys.EmptyKey;
import ru.hse.edu.myurachinskiy.models.keys.Key;

public class QwertyRussianKeyboard extends Keyboard {
    public QwertyRussianKeyboard() {
        // TODO: replace with russian keyboard
        this.keyboard = new Key[][]
                {
                    {
                        new EmptyKey(), new EmptyKey(),
                        new EmptyKey(), new EmptyKey()
                    },
                    {
                            new EmptyKey(), new EmptyKey(),
                            new EmptyKey(), new EmptyKey()
                    }
                };
        }
}
