package com.data.test.ConsoleUserManagement.command;

import com.data.test.ConsoleUserManagement.ConsoleUserManagementApplication;
import com.data.test.ConsoleUserManagement.controller.UserController;
import com.data.test.ConsoleUserManagement.dto.LoginCredentials;
import com.data.test.ConsoleUserManagement.parser.ConsoleInputParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LoginCommand implements Command {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUserManagementApplication.class.getName());

    private static final String LOGOUT_SIGN = "3";
    private final ConsoleInputParser consoleInputParser;
    private final UserController userController;
    private Map<String, LoggedInCommand> commands;

    public LoginCommand(ConsoleInputParser consoleInputParser, UserController userController) {
        this.consoleInputParser = consoleInputParser;
        this.userController = userController;
    }

    @Override
    public void execute() {
        LoginCredentials loginCredentials = consoleInputParser.getLoginCredentials();
        if (userController.loginUser(loginCredentials)) {
            Scanner scanner = new Scanner(System.in);
            logger.info("Login completed, choose next option");
            createCommandMap();
            while (true) {
                logger.info("Available options: 1) change email, 2) change telephone number, 3) logout");

                String input = scanner.next();
                if (input.equals(LOGOUT_SIGN)) {
                    break;
                }

                LoggedInCommand command = commands.get(input);
                if (command != null) {
                    command.execute(loginCredentials.getUsername());
                } else {
                    logger.info("Wrong command, try again");
                }

            }
        }
    }

    private void createCommandMap() {
        this.commands = new HashMap<>();
        commands.put("1", new ChangeEmailCommand(consoleInputParser, userController));
        commands.put("2", new ChangeTelephoneNumberCommand(consoleInputParser, userController));
    }
}
