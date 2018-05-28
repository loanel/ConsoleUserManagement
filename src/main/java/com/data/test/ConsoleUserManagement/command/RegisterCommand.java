package com.data.test.ConsoleUserManagement.command;

import com.data.test.ConsoleUserManagement.controller.UserController;
import com.data.test.ConsoleUserManagement.dto.UserDto;
import com.data.test.ConsoleUserManagement.parser.ConsoleInputParser;

public class RegisterCommand implements Command {
    private final ConsoleInputParser consoleInputParser;

    private final UserController userController;

    public RegisterCommand(ConsoleInputParser consoleInputParser, UserController userController) {
        this.consoleInputParser = consoleInputParser;
        this.userController = userController;
    }

    @Override
    public void execute() {
        UserDto userDto = consoleInputParser.getNewUserInformation();
        userController.createNewUser(userDto);
    }
}
