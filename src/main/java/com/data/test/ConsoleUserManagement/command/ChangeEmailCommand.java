package com.data.test.ConsoleUserManagement.command;

import com.data.test.ConsoleUserManagement.controller.ConsoleController;
import com.data.test.ConsoleUserManagement.parser.ConsoleInputParser;
import org.springframework.stereotype.Component;

@Component
public class ChangeEmailCommand implements LoggedInCommand {

    private final ConsoleInputParser consoleInputParser;
    private final ConsoleController consoleController;

    public ChangeEmailCommand(ConsoleInputParser consoleInputParser, ConsoleController consoleController) {
        this.consoleInputParser = consoleInputParser;
        this.consoleController = consoleController;
    }

    @Override
    public void execute(String username) {
        String email = consoleInputParser.getNewUserEmail();
        consoleController.updateEmail(username, email);
    }
}
