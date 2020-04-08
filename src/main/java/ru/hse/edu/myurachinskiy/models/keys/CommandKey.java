package ru.hse.edu.myurachinskiy.models.keys;

import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

import java.util.Optional;

public class CommandKey extends Key {
    public CommandKey(COMMAND command) {
        super(command.command);
        this.command = command;
    }

    public CommandKey(COMMAND command, double width) {
        super(command.command, command.command, width);
        this.command = command;
    }

    @Override
    public void pressKey(Keyboard keyboard) {
        switch (command) {
            case BACKSPACE:
                if (keyboard.getText().length() > 0)
                    keyboard.setText(keyboard.getText().substring(0, keyboard.getText().length() - 1));
                break;
            case TAB:
            	keyboard.setText(keyboard.getText() + "\t");
            	break;
            case CTRL:
                break;
            case SHIFT:
            	keyboard.setShiftPressed(!keyboard.isShiftPressed());
            	break;
            case ENTER:
                keyboard.setText(keyboard.getText() + "\n");
                break;
            case SPACE:
                keyboard.setText(keyboard.getText() + " ");
                keyboard.setLastWord("");
        }
    }
    private COMMAND command;

    public enum COMMAND {
        BACKSPACE("BACKSPACE"), TAB("TAB"), CTRL("CTRL"), SHIFT("SHIFT"), ENTER("ENTER"), SPACE("SPACE");

        COMMAND(String command) {
            this.command = command;
        }

        String command;
    }
}
