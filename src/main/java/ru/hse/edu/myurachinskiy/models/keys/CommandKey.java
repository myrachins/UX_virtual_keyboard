package ru.hse.edu.myurachinskiy.models.keys;

import ru.hse.edu.myurachinskiy.models.keyboards.Keyboard;

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
        // TODO: Implement method
        switch (command) {
            case DELETE:
            case TAB:
            case CTRL:
            case SHIFT:
            case ENTER:
        }
    }
    private COMMAND command;

    enum COMMAND {
        DELETE("DELETE"), TAB("TAB"), CTRL("CTRL"), SHIFT("SHIFT"), ENTER("ENTER");

        COMMAND(String command) {
            this.command = command;
        }

        String command;
    }
}
