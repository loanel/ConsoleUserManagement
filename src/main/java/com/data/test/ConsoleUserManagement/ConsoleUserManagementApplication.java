package com.data.test.ConsoleUserManagement;

import com.data.test.ConsoleUserManagement.command.Command;
import com.data.test.ConsoleUserManagement.command.LoginCommand;
import com.data.test.ConsoleUserManagement.command.RegisterCommand;
import com.data.test.ConsoleUserManagement.controller.UserController;
import com.data.test.ConsoleUserManagement.parser.ConsoleInputParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
@Profile("!test")
public class ConsoleUserManagementApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUserManagementApplication.class.getName());

    private static final String STOP_APP_SIGN = "3";
    private Map<String, Command> commands;
    private final ConsoleInputParser consoleInputParser;
    private final UserController userController;

    @Autowired
    public ConsoleUserManagementApplication(ConsoleInputParser consoleInputParser, UserController userController) {
        this.consoleInputParser = consoleInputParser;
        this.userController = userController;
    }

    public static void main(String[] args) {

        SpringApplication.run(ConsoleUserManagementApplication.class, args);

    }

    @Override
    public void run(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        createCommandMap();
        logger.info("Choose course of action");
        while (true) {
            logger.info("Available options: 1) register new user, 2) login to existing user, 3) quit");

            String input = scanner.next();
            if (input.equals(STOP_APP_SIGN)) {
                break;
            }

            Command command = commands.get(input);
            if (command != null) {
                command.execute();
            } else {
                logger.info("Wrong command, please try again");
            }
        }
    }

    private void createCommandMap() {
        this.commands = new HashMap<>();
        commands.put("1", new RegisterCommand(consoleInputParser, userController));
        commands.put("2", new LoginCommand(consoleInputParser, userController));
    }
}
