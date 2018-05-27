package com.data.test.ConsoleUserManagement.command;

import com.data.test.ConsoleUserManagement.controller.ConsoleController;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.parser.ConsoleInputParser;

public class RegisterCommand implements Command {

    private final ConsoleInputParser consoleInputParser;

    private final ConsoleController consoleController;

    public RegisterCommand(ConsoleInputParser consoleInputParser, ConsoleController consoleController) {
        this.consoleInputParser = consoleInputParser;
        this.consoleController = consoleController;
    }

    @Override
    public void execute() {
        UserDto userDto = consoleInputParser.getNewUserInformation();
        consoleController.createNewUser(userDto);
    }
}
