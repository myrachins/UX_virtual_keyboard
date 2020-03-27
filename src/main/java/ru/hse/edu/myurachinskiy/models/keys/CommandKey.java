package ru.hse.edu.myurachinskiy.models.keys;

import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

import java.util.Optional;

class CommandKey extends Key {
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
            	keyboard.setText(Optional.ofNullable(keyboard.getText())
					.filter(sStr -> sStr.length() != 0)
					.map(sStr -> sStr.substring(0, sStr.length() - 1))
					.orElse(keyboard.getText()));
            case TAB:
            	keyboard.setText(keyboard.getText() + "\t");
            case CTRL:
            case SHIFT:
            	keyboard.setShiftPressed(!keyboard.isShiftPressed());
            case ENTER:
        }
    }
    private COMMAND command;

    enum COMMAND {
        BACKSPACE("BACKSPACE"), TAB("TAB"), CTRL("CTRL"), SHIFT("SHIFT"), ENTER("ENTER");

        COMMAND(String command) {
            this.command = command;
        }

        String command;
    }
}
