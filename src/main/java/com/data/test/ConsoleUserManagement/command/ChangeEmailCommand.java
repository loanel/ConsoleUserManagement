package com.data.test.ConsoleUserManagement.command;

import com.data.test.ConsoleUserManagement.controller.UserController;
import com.data.test.ConsoleUserManagement.parser.ConsoleInputParser;
import org.springframework.stereotype.Component;

@Component
public class ChangeEmailCommand implements LoggedInCommand {

    private final ConsoleInputParser consoleInputParser;
    private final UserController userController;

    public ChangeEmailCommand(ConsoleInputParser consoleInputParser, UserController userController) {
        this.consoleInputParser = consoleInputParser;
        this.userController = userController;
    }

    @Override
    public void execute(String username) {
        String email = consoleInputParser.getNewUserEmail();
        userController.updateEmail(username, email);
    }
}
