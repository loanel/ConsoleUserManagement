package com.data.test.ConsoleUserManagement.command;

import com.data.test.ConsoleUserManagement.controller.UserController;
import com.data.test.ConsoleUserManagement.parser.ConsoleInputParser;
import org.springframework.stereotype.Component;

@Component
public class ChangeTelephoneNumberCommand implements LoggedInCommand {
    private final ConsoleInputParser consoleInputParser;

    private final UserController userController;

    public ChangeTelephoneNumberCommand(ConsoleInputParser consoleInputParser, UserController userController) {
        this.consoleInputParser = consoleInputParser;
        this.userController = userController;
    }

    @Override
    public void execute(String username) {
        String telephoneNumber = consoleInputParser.getNewTelephoneNumber();
        userController.updateTelephoneNumber(username, telephoneNumber);
    }
}
